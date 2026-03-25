#include "proc_reader.h"
#include "file_util.h"
#include <dirent.h>
#include <cstdlib>
#include <sstream>

namespace phonescope {

std::optional<std::string> ProcReader::readFile(const std::string& path) {
    return FileUtil::readFileToString(path);
}

std::vector<std::string> ProcReader::readLines(const std::string& path) {
    return FileUtil::readFileLines(path);
}

std::optional<long long> ProcReader::readLong(const std::string& path) {
    auto content = readFile(path);
    if (!content.has_value()) return std::nullopt;
    return FileUtil::parseLong(FileUtil::trim(content.value()));
}

std::optional<CpuInfo> ProcReader::parseCpuInfo() {
    auto lines = readLines("/proc/cpuinfo");
    if (lines.empty()) return std::nullopt;

    CpuInfo info;
    std::map<std::string, std::string> currentProcessor;
    bool inProcessorBlock = false;

    for (const auto& line : lines) {
        if (FileUtil::trim(line).empty()) {
            if (inProcessorBlock && !currentProcessor.empty()) {
                info.processors.push_back(currentProcessor);
                currentProcessor.clear();
            }
            continue;
        }

        auto kv = FileUtil::parseKeyValue(line);
        if (kv.first == "processor") {
            inProcessorBlock = true;
        }

        if (inProcessorBlock) {
            currentProcessor[kv.first] = kv.second;
        } else {
            info.global[kv.first] = kv.second;
        }
    }

    if (inProcessorBlock && !currentProcessor.empty()) {
        info.processors.push_back(currentProcessor);
    }

    return info;
}

std::map<std::string, long long> ProcReader::parseMemInfo() {
    std::map<std::string, long long> meminfo;
    auto lines = readLines("/proc/meminfo");
    for (const auto& line : lines) {
        auto kv = FileUtil::parseKeyValue(line);
        if (!kv.first.empty() && !kv.second.empty()) {
            std::stringstream ss(kv.second);
            long long val;
            if (ss >> val) {
                meminfo[kv.first] = val;
            }
        }
    }
    return meminfo;
}

std::vector<CpuStat> ProcReader::parseStat() {
    std::vector<CpuStat> stats;
    auto lines = readLines("/proc/stat");
    for (const auto& line : lines) {
        if (line.find("cpu") == 0) {
            std::stringstream ss(line);
            CpuStat stat;
            ss >> stat.name >> stat.user >> stat.nice >> stat.system
               >> stat.idle >> stat.iowait >> stat.irq >> stat.softirq;
            stats.push_back(stat);
        }
    }
    return stats;
}

std::optional<std::pair<double, double>> ProcReader::parseUptime() {
    auto content = readFile("/proc/uptime");
    if (!content.has_value()) return std::nullopt;
    std::stringstream ss(content.value());
    double uptime, idletime;
    if (ss >> uptime >> idletime) {
        return std::make_pair(uptime, idletime);
    }
    return std::nullopt;
}

std::vector<int> ProcReader::listPids() {
    std::vector<int> pids;
    DIR* dir = opendir("/proc");
    if (!dir) return pids;

    struct dirent* entry;
    while ((entry = readdir(dir)) != nullptr) {
        if (entry->d_type == DT_DIR) {
            char* end;
            long pid = strtol(entry->d_name, &end, 10);
            if (*end == '\0' && pid > 0) {
                pids.push_back(static_cast<int>(pid));
            }
        }
    }
    closedir(dir);
    return pids;
}

} // namespace phonescope
