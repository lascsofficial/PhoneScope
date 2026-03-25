#include "process_inspector.h"
#include <nlohmann/json.hpp>
#include "proc_reader.h"
#include "file_util.h"
#include <string>

using json = nlohmann::json;

namespace phonescope {

std::string ProcessInspector::inspect() {
    json result = json::array();

    auto pids = ProcReader::listPids();
    for (int pid : pids) {
        std::string pidPath = "/proc/" + std::to_string(pid);

        // 1. Name/cmdline
        auto cmdlineStr = FileUtil::readFileToString(pidPath + "/cmdline");
        std::string name = "";
        if (cmdlineStr.has_value() && !cmdlineStr.value().empty()) {
            name = std::string(cmdlineStr.value().c_str()); // cmdline items are null-separated, stop at first \0
        }
        if (name.empty()) {
            auto commStr = FileUtil::readFileToString(pidPath + "/comm");
            if (commStr.has_value()) name = FileUtil::trim(commStr.value());
        }
        if (name.empty()) name = "[" + std::to_string(pid) + "]";

        // 2. Status (Memory and Threads)
        auto statusLines = FileUtil::readFileLines(pidPath + "/status");
        long long vmRss = 0;
        long long vmSwap = 0;
        int threads = 0;
        for (const auto& line : statusLines) {
            auto kv = FileUtil::parseKeyValue(line);
            if (kv.first == "VmRSS") {
                vmRss = FileUtil::parseLong(kv.second).value_or(0);
            } else if (kv.first == "VmSwap") {
                vmSwap = FileUtil::parseLong(kv.second).value_or(0);
            } else if (kv.first == "Threads") {
                threads = static_cast<int>(FileUtil::parseLong(kv.second).value_or(0));
            }
        }

        // 3. Stat (State, Priority)
        std::string state = "S";
        int priority = 0;
        auto statContent = FileUtil::readFileToString(pidPath + "/stat");
        if (statContent.has_value()) {
            // Find the character after the last ')', to handle spaces in proc comm
            size_t rparen = statContent.value().find_last_of(')');
            if (rparen != std::string::npos && rparen + 2 < statContent.value().length()) {
                std::string remainder = statContent.value().substr(rparen + 2);
                char stateChar;
                int ppid, pgrp, session, tty_nr, tpgid;
                unsigned int flags;
                unsigned long minflt, cminflt, majflt, cmajflt, utime, stime;
                long cutime, cstime, pri, nice;
                
                if (sscanf(remainder.c_str(), "%c %d %d %d %d %d %u %lu %lu %lu %lu %lu %lu %ld %ld %ld %ld",
                           &stateChar, &ppid, &pgrp, &session, &tty_nr, &tpgid,
                           &flags, &minflt, &cminflt, &majflt, &cmajflt, &utime, &stime, &cutime, &cstime, &pri, &nice) >= 16) {
                    state = std::string(1, stateChar);
                    priority = static_cast<int>(pri);
                }
            }
        }

        json proc;
        proc["pid"] = pid;
        proc["name"] = name;
        proc["state"] = state;
        proc["vm_rss_kb"] = vmRss;
        proc["vm_swap_kb"] = vmSwap;
        proc["threads"] = threads;
        proc["priority"] = priority;
        result.push_back(proc);
    }

    return result.dump();
}

} // namespace phonescope
