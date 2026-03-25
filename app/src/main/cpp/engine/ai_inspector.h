#pragma once
#include <string>

namespace phonescope {

class AiInspector {
public:
    /// NNAPI inspection: accelerators, supported ops, benchmark
    static std::string inspect();
};

} // namespace phonescope
