#include "thermal_inspector.h"
#include <nlohmann/json.hpp>
#include "sys_reader.h"

using json = nlohmann::json;

namespace phonescope {

std::string ThermalInspector::inspect() {
    json result;

    // 1. Thermal Zones
    auto zones = SysReader::readThermalZones();
    json zonesJson = json::array();
    
    if (zones.empty()) {
        auto battTempOpt = SysReader::readLong("/sys/class/power_supply/battery/temp");
        if (battTempOpt.has_value()) {
            json z;
            z["type"] = "battery_fallback";
            z["temp"] = battTempOpt.value();
            zonesJson.push_back(z);
        }
    } else {
        for (const auto& zone : zones) {
            json z;
            z["type"] = zone.type;
            z["temp"] = zone.temp; // Usually in millidegrees Celsius
            zonesJson.push_back(z);
        }
    }
    result["zones"] = zonesJson;

    // 2. Cooling Devices
    auto coolingDevices = SysReader::readCoolingDevices();
    json coolingJson = json::array();
    for (const auto& dev : coolingDevices) {
        json d;
        d["type"] = dev.type;
        d["max_state"] = dev.max_state;
        d["cur_state"] = dev.cur_state;
        coolingJson.push_back(d);
    }
    result["cooling_devices"] = coolingJson;

    return result.dump();
}

std::string ThermalInspector::sample() {
    json result;

    // Read current temperatures for all zones
    auto zones = SysReader::readThermalZones();
    json zonesJson = json::array();
    for (const auto& zone : zones) {
        json z;
        z["type"] = zone.type;
        z["temp"] = zone.temp;
        zonesJson.push_back(z);
    }
    result["temperatures"] = zonesJson;

    return result.dump();
}

} // namespace phonescope
