#pragma once

#include <string>
#include <vector>
#include <optional>

namespace phonescope {

struct ThermalZone {
    std::string type;
    long long temp = 0;
};

struct CoolingDevice {
    std::string type;
    long long max_state = 0;
    long long cur_state = 0;
};

struct CacheInfo {
    int level = 0;
    std::string type;
    std::string size; // e.g. "64K"
    int ways_of_associativity = 0;
    int coherency_line_size = 0;
    int number_of_sets = 0;
    std::string shared_cpu_map;
};

struct Vulnerability {
    std::string name;
    std::string status;
};

/**
 * Reads files from /sys filesystem (sysfs).
 * Used for thermal zones, CPU frequency, GPU state, etc.
 */
class SysReader {
public:
    /// Read a single sysfs file as string (trimmed)
    static std::optional<std::string> readString(const std::string& path);

    /// Read a single integer from sysfs
    static std::optional<long long> readLong(const std::string& path);

    /// Read a single float from sysfs
    static std::optional<double> readDouble(const std::string& path);

    /// List subdirectories matching a prefix in a directory
    static std::vector<std::string> listDirs(const std::string& parentDir, const std::string& prefix = "");

    /// Read all thermal zone names and temperatures
    static std::vector<ThermalZone> readThermalZones();

    /// Read all cooling devices
    static std::vector<CoolingDevice> readCoolingDevices();

    /// Read CPU cache hierarchy for a specific core
    static std::vector<CacheInfo> readCpuCaches(int core);

    /// Read CPU vulnerabilities status
    static std::vector<Vulnerability> readCpuVulnerabilities();

    /// Read CPU frequency for a specific core (Hz)
    static std::optional<long long> readCpuFreq(int core);

    /// Read CPU max frequency for a specific core (Hz)
    static std::optional<long long> readCpuMaxFreq(int core);

    /// Read CPU min frequency for a specific core (Hz)
    static std::optional<long long> readCpuMinFreq(int core);

    /// Read scaling governor for a specific core
    static std::optional<std::string> readCpuGovernor(int core);
};

} // namespace phonescope
