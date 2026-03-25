#include "kernel_inspector.h"
#include <sys/utsname.h>
#include <nlohmann/json.hpp>
#include "file_util.h"
#include <string>
#include <vector>

using json = nlohmann::json;

namespace phonescope {

std::string KernelInspector::inspect() {
    json result;

    struct utsname buffer;
    if (uname(&buffer) == 0) {
        result["sysname"] = buffer.sysname;
        result["nodename"] = buffer.nodename;
        result["release"] = buffer.release;
        result["version"] = buffer.version;
        result["machine"] = buffer.machine;
    }

    auto selinux = FileUtil::readFileToString("/sys/fs/selinux/enforce");
    if (selinux.has_value()) {
        result["selinux_enforcing"] = (FileUtil::trim(selinux.value()) == "1");
    } else {
        result["selinux_enforcing"] = nullptr;
    }

    // Root detection (basic heuristics)
    bool isRooted = false;
    std::vector<std::string> suPaths = {
        "/sbin/su", "/system/bin/su", "/system/xbin/su", 
        "/data/local/xbin/su", "/data/local/bin/su", 
        "/system/sd/xbin/su", "/system/bin/failsafe/su", 
        "/data/local/su", "/su/bin/su"
    };
    for (const auto& path : suPaths) {
        if (FileUtil::fileExists(path)) {
            isRooted = true;
            break;
        }
    }
    result["rooted"] = isRooted;

    return result.dump();
}

} // namespace phonescope
