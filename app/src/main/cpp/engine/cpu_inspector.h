#pragma once
#include <string>

namespace phonescope {

class CpuInspector {
public:
    /// Full CPU inspection: topology, caches, features, vulnerabilities
    static std::string inspect();

    /// Live sample: per-core frequency + usage (called every 1s)
    static std::string sample();
};

} // namespace phonescope
