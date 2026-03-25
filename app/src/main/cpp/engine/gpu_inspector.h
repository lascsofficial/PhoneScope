#pragma once
#include <string>

namespace phonescope {

class GpuInspector {
public:
    /// Full GPU inspection: OpenGL ES + Vulkan queries
    static std::string inspect();
};

} // namespace phonescope
