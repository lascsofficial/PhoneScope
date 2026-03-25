#include "sys_reader.h"
#include "file_util.h"
#include <dirent.h>
#include <algorithm>

namespace phonescope {

std::optional<std::string> SysReader::readString(const std::string& path) {
    auto content = FileUtil::readFileToString(path);
    if (!content.has_value()) return std::nullopt;
    return FileUtil::trim(content.value());
}

std::optional<long long> SysReader::readLong(const std::string& path) {
    auto content = readString(path);
    if (!content.has_value()) return std::nullopt;
    return FileUtil::parseLong(content.value());
}

std::optional<double> SysReader::readDouble(const std::string& path) {
    auto content = readString(path);
    if (!content.has_value()) return std::nullopt;
    try {
        return std::stod(content.value());
    } catch (...) {
        return std::nullopt;
    }
}

std::vector<std::string> SysReader::listDirs(const std::string& parentDir, const std::string& prefix) {
    std::vector<std::string> dirs;
    DIR* dir = opendir(parentDir.c_str());
    if (!dir) return dirs;

    struct dirent* entry;
    while ((entry = readdir(dir)) != nullptr) {
        if (entry->d_type == DT_DIR || entry->d_type == DT_LNK) {
            std::string name(entry->d_name);
            if (prefix.empty() || name.find(prefix) == 0) {
                dirs.push_back(name);
            }
        }
    }
    closedir(dir);
    std::sort(dirs.begin(), dirs.end());
    return dirs;
}

std::vector<ThermalZone> SysReader::readThermalZones() {
    std::vector<ThermalZone> zones;
    auto dirs = listDirs("/sys/class/thermal", "thermal_zone");
    for (const auto& dir : dirs) {
        std::string basePath = "/sys/class/thermal/" + dir;
        auto type = readString(basePath + "/type");
        auto temp = readLong(basePath + "/temp");
        if (type.has_value() && temp.has_value()) {
            zones.push_back({type.value(), temp.value()});
        }
    }
    return zones;
}

std::vector<CoolingDevice> SysReader::readCoolingDevices() {
    std::vector<CoolingDevice> devices;
    auto dirs = listDirs("/sys/class/thermal", "cooling_device");
    for (const auto& dir : dirs) {
        std::string basePath = "/sys/class/thermal/" + dir;
        auto type = readString(basePath + "/type");
        auto max_state = readLong(basePath + "/max_state");
        auto cur_state = readLong(basePath + "/cur_state");
        if (type.has_value() && max_state.has_value() && cur_state.has_value()) {
            devices.push_back({type.value(), max_state.value(), cur_state.value()});
        }
    }
    return devices;
}

std::vector<CacheInfo> SysReader::readCpuCaches(int core) {
    std::vector<CacheInfo> caches;
    std::string basePath = "/sys/devices/system/cpu/cpu" + std::to_string(core) + "/cache";
    auto dirs = listDirs(basePath, "index");
    for (const auto& dir : dirs) {
        std::string cachePath = basePath + "/" + dir;
        CacheInfo info;
        info.level = readLong(cachePath + "/level").value_or(0);
        info.type = readString(cachePath + "/type").value_or("");
        info.size = readString(cachePath + "/size").value_or("");
        info.ways_of_associativity = readLong(cachePath + "/ways_of_associativity").value_or(0);
        info.coherency_line_size = readLong(cachePath + "/coherency_line_size").value_or(0);
        info.number_of_sets = readLong(cachePath + "/number_of_sets").value_or(0);
        info.shared_cpu_map = readString(cachePath + "/shared_cpu_map").value_or("");
        caches.push_back(info);
    }
    return caches;
}

std::vector<Vulnerability> SysReader::readCpuVulnerabilities() {
    std::vector<Vulnerability> vulns;
    std::string basePath = "/sys/devices/system/cpu/vulnerabilities";
    auto files = FileUtil::getDirEntries(basePath);
    for (const auto& file : files) {
        auto status = readString(basePath + "/" + file);
        if (status.has_value()) {
            vulns.push_back({file, status.value()});
        }
    }
    return vulns;
}

std::optional<long long> SysReader::readCpuFreq(int core) {
    return readLong("/sys/devices/system/cpu/cpu" + std::to_string(core) +
                    "/cpufreq/scaling_cur_freq");
}

std::optional<long long> SysReader::readCpuMaxFreq(int core) {
    return readLong("/sys/devices/system/cpu/cpu" + std::to_string(core) +
                    "/cpufreq/scaling_max_freq");
}

std::optional<long long> SysReader::readCpuMinFreq(int core) {
    return readLong("/sys/devices/system/cpu/cpu" + std::to_string(core) +
                    "/cpufreq/scaling_min_freq");
}

std::optional<std::string> SysReader::readCpuGovernor(int core) {
    return readString("/sys/devices/system/cpu/cpu" + std::to_string(core) +
                      "/cpufreq/scaling_governor");
}

} // namespace phonescope
