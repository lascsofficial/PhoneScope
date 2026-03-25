#pragma once
#include <string>

namespace phonescope {

class KernelInspector {
public:
    /// Full kernel & OS inspection: uname, SELinux, boot state, ART config
    static std::string inspect();
};

} // namespace phonescope
