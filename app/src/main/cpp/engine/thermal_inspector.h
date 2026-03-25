#pragma once
#include <string>

namespace phonescope {

class ThermalInspector {
public:
    /// Full thermal inspection: all zones, trip points, cooling devices
    static std::string inspect();

    /// Live sample: all zone temperatures
    static std::string sample();
};

} // namespace phonescope
