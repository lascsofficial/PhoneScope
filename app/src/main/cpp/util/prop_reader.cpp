#include "prop_reader.h"
#include <sys/system_properties.h>
#include <cstring>

namespace phonescope {

std::string PropReader::getProp(const std::string& name) {
    char value[PROP_VALUE_MAX] = {0};
    __system_property_get(name.c_str(), value);
    return std::string(value);
}

// Callback context for property enumeration
struct PropEnumContext {
    std::map<std::string, std::string>* props;
    std::string prefix;
};

static void propEnumCallback(const prop_info* pi, void* cookie) {
    auto* ctx = static_cast<PropEnumContext*>(cookie);
    char name[PROP_NAME_MAX] = {0};
    char value[PROP_VALUE_MAX] = {0};
    __system_property_read_callback(pi,
        [](void* cookie, const char* name, const char* value, uint32_t /*serial*/) {
            auto* ctx = static_cast<PropEnumContext*>(cookie);
            std::string nameStr(name);
            if (ctx->prefix.empty() || nameStr.find(ctx->prefix) == 0) {
                (*ctx->props)[nameStr] = std::string(value);
            }
        },
        ctx);
}

std::map<std::string, std::string> PropReader::getAllProps() {
    std::map<std::string, std::string> props;
    PropEnumContext ctx{&props, ""};
    __system_property_foreach(
        [](const prop_info* pi, void* cookie) {
            propEnumCallback(pi, cookie);
        },
        &ctx);
    return props;
}

std::map<std::string, std::string> PropReader::getPropsWithPrefix(const std::string& prefix) {
    std::map<std::string, std::string> props;
    PropEnumContext ctx{&props, prefix};
    __system_property_foreach(
        [](const prop_info* pi, void* cookie) {
            propEnumCallback(pi, cookie);
        },
        &ctx);
    return props;
}

} // namespace phonescope
