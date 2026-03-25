#include "file_util.h"
#include <fstream>
#include <sstream>
#include <algorithm>
#include <sys/stat.h>
#include <dirent.h>

namespace phonescope {

std::optional<std::string> FileUtil::readFileToString(const std::string& path) {
    std::ifstream file(path);
    if (!file.is_open()) return std::nullopt;
    std::ostringstream ss;
    ss << file.rdbuf();
    return ss.str();
}

std::vector<std::string> FileUtil::readFileLines(const std::string& path) {
    std::vector<std::string> lines;
    std::ifstream file(path);
    if (!file.is_open()) return lines;
    std::string line;
    while (std::getline(file, line)) {
        lines.push_back(line);
    }
    return lines;
}

bool FileUtil::fileExists(const std::string& path) {
    struct stat st{};
    return stat(path.c_str(), &st) == 0 && S_ISREG(st.st_mode);
}

bool FileUtil::dirExists(const std::string& path) {
    struct stat st{};
    return stat(path.c_str(), &st) == 0 && S_ISDIR(st.st_mode);
}

std::vector<std::string> FileUtil::getDirEntries(const std::string& path) {
    std::vector<std::string> entries;
    DIR* dir = opendir(path.c_str());
    if (!dir) return entries;

    struct dirent* entry;
    while ((entry = readdir(dir)) != nullptr) {
        std::string name(entry->d_name);
        if (name != "." && name != "..") {
            entries.push_back(name);
        }
    }
    closedir(dir);
    return entries;
}

std::optional<long long> FileUtil::parseLong(const std::string& str) {
    if (str.empty()) return std::nullopt;
    try {
        size_t pos;
        long long val = std::stoll(str, &pos, 10);
        // Only return if we parsed at least something and didn't throw
        return val;
    } catch (...) {
        return std::nullopt;
    }
}

std::string FileUtil::trim(const std::string& str) {
    auto start = str.find_first_not_of(" \t\n\r");
    if (start == std::string::npos) return "";
    auto end = str.find_last_not_of(" \t\n\r");
    return str.substr(start, end - start + 1);
}

std::pair<std::string, std::string> FileUtil::parseKeyValue(
    const std::string& line, char delimiter) {
    auto pos = line.find(delimiter);
    if (pos == std::string::npos) return {trim(line), ""};
    return {trim(line.substr(0, pos)), trim(line.substr(pos + 1))};
}

} // namespace phonescope
