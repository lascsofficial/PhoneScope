#pragma once

#include <string>
#include <vector>
#include <map>
#include <optional>

namespace phonescope {

struct CpuStat {
    std::string name;
    long long user = 0;
    long long nice = 0;
    long long system = 0;
    long long idle = 0;
    long long iowait = 0;
    long long irq = 0;
    long long softirq = 0;
};

struct CpuInfo {
    std::map<std::string, std::string> global;
    std::vector<std::map<std::string, std::string>> processors;
};

/**
 * Reads files from /proc filesystem.
 * All methods return empty/nullopt on permission denied or missing files.
 */
class ProcReader {
public:
    /// Read entire file contents as string
    static std::optional<std::string> readFile(const std::string& path);

    /// Read file and split into lines
    static std::vector<std::string> readLines(const std::string& path);

    /// Read a single integer value from a file
    static std::optional<long long> readLong(const std::string& path);

    /// Read /proc/cpuinfo and parse into processor blocks and global attributes
    static std::optional<CpuInfo> parseCpuInfo();

    /// Read /proc/meminfo and parse into key-value map (values in KB)
    static std::map<std::string, long long> parseMemInfo();

    /// Read /proc/stat and parse CPU usage times
    static std::vector<CpuStat> parseStat();

    /// Read /proc/uptime and return {uptime, idle_time} in seconds
    static std::optional<std::pair<double, double>> parseUptime();

    /// List directories in /proc that are numeric (PIDs)
    static std::vector<int> listPids();
};

} // namespace phonescope
