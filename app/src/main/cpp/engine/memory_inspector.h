#pragma once
#include <string>

namespace phonescope {

class MemoryInspector {
public:
    /// Full memory inspection: /proc/meminfo, zRAM, bandwidth
    static std::string inspect();

    /// Live sample: current free/available/cached
    static std::string sample();
};

} // namespace phonescope
