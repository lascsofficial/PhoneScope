#include "gpu_inspector.h"
#include <nlohmann/json.hpp>
#include "sys_reader.h"

using json = nlohmann::json;

namespace phonescope {

std::string GpuInspector::inspect() {
    json result;

    // Detect Adreno (Qualcomm)
    auto adrenoModel = SysReader::readString("/sys/class/kgsl/kgsl-3d0/gpu_model");
    if (adrenoModel.has_value()) {
        result["vendor"] = "Qualcomm";
        result["model"] = adrenoModel.value();
        result["max_freq"] = SysReader::readLong("/sys/class/kgsl/kgsl-3d0/max_gpuclk").value_or(0);
        result["min_freq"] = SysReader::readLong("/sys/class/kgsl/kgsl-3d0/min_clock_mhz").value_or(0);
    } 
    // Detect Mali (ARM) - Path varies wildly, trying common ones
    else {
        auto maliModel = SysReader::readString("/sys/class/misc/mali0/device/name");
        if (maliModel.has_value()) {
            result["vendor"] = "ARM";
            result["model"] = maliModel.value();
        } else {
            result["vendor"] = "Unknown";
            result["model"] = "Unknown";
        }
    }

    // Note: True OpenGL/Vulkan queries require an active EGL/VK Context 
    // which is best initialized on the JVM side and passed down, or 
    // created via a PBuffer. For Tier A, we rely on sysfs introspection.

    return result.dump();
}

std::string GpuInspector::sample() {
    json result;

    result["busy_percent"] = 0;
    result["current_freq"] = 0;

    // Adreno live stats
    auto adrenoBusy = SysReader::readLong("/sys/class/kgsl/kgsl-3d0/gpubusy"); // format: "busy total"
    if (adrenoBusy.has_value()) { // readLong only gets the first number, but gpubusy has two "busy_time total_time"
        auto busyStr = SysReader::readString("/sys/class/kgsl/kgsl-3d0/gpubusy");
        if (busyStr.has_value()) {
            long long busy = 0, total = 0;
            if (sscanf(busyStr.value().c_str(), "%lld %lld", &busy, &total) == 2 && total > 0) {
                result["busy_percent"] = (busy * 100) / total;
            }
        }
        result["current_freq"] = SysReader::readLong("/sys/class/kgsl/kgsl-3d0/gpuclk").value_or(0);
    }

    return result.dump();
}

} // namespace phonescope
