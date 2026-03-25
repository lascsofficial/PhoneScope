#pragma once
#include <string>

namespace phonescope {

class ProcessInspector {
public:
    /// List all running processes with CPU/RAM/state details
    static std::string inspect();
};

} // namespace phonescope
