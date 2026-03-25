#include "ai_inspector.h"
#include <nlohmann/json.hpp>
#include "sys_reader.h"
#include "prop_reader.h"
#include "file_util.h"

using json = nlohmann::json;

namespace phonescope {

std::string AiInspector::inspect() {
    json result;

    // Detect Google Tensor EdgeTPU
    if (FileUtil::fileExists("/sys/class/edgetpu")) {
        result["accelerator_type"] = "Google EdgeTPU";
        auto paths = SysReader::listDirs("/sys/class/edgetpu");
        if (!paths.empty()) {
            result["accelerator_name"] = paths[0];
        }
    }
    // Detect Qualcomm Hexagon DSP
    else if (FileUtil::fileExists("/sys/class/fastrpc")) {
        result["accelerator_type"] = "Qualcomm Hexagon DSP";
    }
    // Detect MediaTek APU
    else if (PropReader::getProp("ro.mtk_nn_support") == "1") {
        result["accelerator_type"] = "MediaTek APU";
    }
    // Unknown or generic NNAPI
    else {
        result["accelerator_type"] = "Unknown / Generic CPU fallback";
    }

    // Capture NNAPI properties
    json props = json::object();
    auto allProps = PropReader::getAllProps();
    for (const auto& kv : allProps) {
        if (kv.first.find("ro.vendor.npu") != std::string::npos ||
            kv.first.find("ro.soc.manufacturer") != std::string::npos) {
            props[kv.first] = kv.second;
        }
    }
    result["properties"] = props;

    return result.dump();
}

} // namespace phonescope
