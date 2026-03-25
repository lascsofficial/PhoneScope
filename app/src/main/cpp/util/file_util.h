#pragma once

#include <string>
#include <optional>
#include <vector>
#include <cstdint>

namespace phonescope {

/**
 * Generic file I/O utilities used by all other readers.
 */
class FileUtil {
public:
    /// Read entire file contents as a string
    static std::optional<std::string> readFileToString(const std::string& path);

    /// Read file and split into lines
    static std::vector<std::string> readFileLines(const std::string& path);

    /// Check if a file exists
    static bool fileExists(const std::string& path);

    /// Check if a directory exists
    static bool dirExists(const std::string& path);

    /// Get all entries in a directory (files and subdirectories)
    static std::vector<std::string> getDirEntries(const std::string& path);

    /// Parse a safe long long from string
    static std::optional<long long> parseLong(const std::string& str);

    /// Trim whitespace from both ends
    static std::string trim(const std::string& str);

    /// Parse a key-value line like "key: value" or "key = value"
    static std::pair<std::string, std::string> parseKeyValue(
        const std::string& line, char delimiter = ':');
};

} // namespace phonescope
