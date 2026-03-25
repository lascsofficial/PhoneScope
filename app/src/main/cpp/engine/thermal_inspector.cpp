#include "thermal_inspector.h"

namespace phonescope {

std::string ThermalInspector::inspect() {
    // TODO: Sprint S4 — Full thermal inspection
    // - Enumerate /sys/class/thermal/thermal_zone*/
    // - Read type, temp, trip_point_*_type, trip_point_*_temp
    // - Enumerate cooling_device*/ with type, max_state, cur_state
    // - Detect thermal governor (step_wise, power_allocator)
    return "{}";
}

std::string ThermalInspector::sample() {
    // TODO: Sprint S4 — Live thermal sampling
    // - Read all thermal_zone*/temp values
    return "{}";
}

} // namespace phonescope
