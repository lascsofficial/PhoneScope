#include "memory_inspector.h"
#include <nlohmann/json.hpp>
#include "proc_reader.h"

using json = nlohmann::json;

namespace phonescope {

std::string MemoryInspector::inspect() {
    json result;

    // Parse all keys in /proc/meminfo
    auto meminfo = ProcReader::parseMemInfo();
    json memJson = json::object();
    for (const auto& kv : meminfo) {
        memJson[kv.first] = kv.second; // value is in kB
    }
    result["meminfo"] = memJson;

    // Basic bandwidth benchmark approximation could go here, 
    // but we'll defer heavy benchmarks to S5 / S6.
    
    return result.dump();
}

std::string MemoryInspector::sample() {
    json result;

    // Fast parse of essential fields
    auto meminfo = ProcReader::parseMemInfo();
    
    // Safety fallback to 0 if key not found
    auto getVal = [&meminfo](const std::string& key) -> long long {
        auto it = meminfo.find(key);
        return it != meminfo.end() ? it->second : 0;
    };

    result["MemTotal"] = getVal("MemTotal");
    result["MemFree"] = getVal("MemFree");
    result["MemAvailable"] = getVal("MemAvailable");
    result["Cached"] = getVal("Cached");
    result["SwapTotal"] = getVal("SwapTotal");
    result["SwapFree"] = getVal("SwapFree");

    return result.dump();
}

} // namespace phonescope
