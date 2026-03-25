#include "cpu_inspector.h"
#include <nlohmann/json.hpp>
#include "proc_reader.h"
#include "sys_reader.h"
#include "prop_reader.h"
#include <unistd.h>

using json = nlohmann::json;

namespace phonescope {

std::string CpuInspector::inspect() {
    json result;

    // 1. Parse /proc/cpuinfo (Fails on Android 10+ for apps)
    auto cpuInfoOpt = ProcReader::parseCpuInfo();
    json processors = json::array();

    if (cpuInfoOpt.has_value()) {
        const auto& cpuInfo = cpuInfoOpt.value();
        
        json global;
        for (const auto& kv : cpuInfo.global) {
            global[kv.first] = kv.second;
        }
        result["global"] = global;

        for (size_t i = 0; i < cpuInfo.processors.size(); ++i) {
            json procJson;
            for (const auto& kv : cpuInfo.processors[i]) {
                procJson[kv.first] = kv.second;
            }
            processors.push_back(procJson);
        }
    } else {
        // Fallback: Use sysconf to get core count
        int numCores = sysconf(_SC_NPROCESSORS_CONF);
        if (numCores <= 0) numCores = 8; // Safe default for modern phones
        
        result["global"]["Hardware"] = PropReader::getProp("ro.hardware");
        result["global"]["Processor"] = "AArch64 Processor";

        for (int i = 0; i < numCores; ++i) {
            json procJson;
            procJson["processor"] = std::to_string(i);
            processors.push_back(procJson);
        }
    }

    // Mix in sysfs data for all detected cores
    for (size_t i = 0; i < processors.size(); ++i) {
        int coreId = static_cast<int>(i);
        processors[i]["scaling_max_freq"] = SysReader::readCpuMaxFreq(coreId).value_or(0);
        processors[i]["scaling_min_freq"] = SysReader::readCpuMinFreq(coreId).value_or(0);
        processors[i]["scaling_governor"] = SysReader::readCpuGovernor(coreId).value_or("unknown");

        auto caches = SysReader::readCpuCaches(coreId);
        json cachesJson = json::array();
        for (const auto& c : caches) {
            json cJson;
            cJson["level"] = c.level;
            cJson["type"] = c.type;
            cJson["size"] = c.size;
            cJson["ways_of_associativity"] = c.ways_of_associativity;
            cachesJson.push_back(cJson);
        }
        processors[i]["caches"] = cachesJson;
    }
    result["processors"] = processors;

    // 2. Vulnerabilities
    auto vulns = SysReader::readCpuVulnerabilities();
    json vulnsJson = json::object();
    for (const auto& v : vulns) {
        vulnsJson[v.name] = v.status;
    }
    result["vulnerabilities"] = vulnsJson;

    // 3. SoC metadata
    result["soc_board"] = PropReader::getProp("ro.board.platform");
    result["soc_hardware"] = PropReader::getProp("ro.hardware");

    return result.dump();
}

std::string CpuInspector::sample() {
    json result;

    // Read CPU Stat for usage
    auto stats = ProcReader::parseStat();
    json statsJson = json::array();
    for (const auto& stat : stats) {
        json s;
        s["name"] = stat.name;
        s["user"] = stat.user;
        s["nice"] = stat.nice;
        s["system"] = stat.system;
        s["idle"] = stat.idle;
        s["iowait"] = stat.iowait;
        statsJson.push_back(s);
    }
    result["stat"] = statsJson;

    // Read live frequencies
    json freqs = json::array();
    if (auto cpuInfoOpt = ProcReader::parseCpuInfo()) {
        int numCores = static_cast<int>(cpuInfoOpt.value().processors.size());
        for (int i = 0; i < numCores; ++i) {
            json f;
            f["core"] = i;
            f["freq"] = SysReader::readCpuFreq(i).value_or(0);
            freqs.push_back(f);
        }
    }
    result["frequencies"] = freqs;

    return result.dump();
}

} // namespace phonescope
