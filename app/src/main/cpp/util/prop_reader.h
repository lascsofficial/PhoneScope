#pragma once

#include <string>
#include <vector>
#include <map>

namespace phonescope {

/**
 * Reads Android system properties via __system_property_* APIs.
 */
class PropReader {
public:
    /// Read a single system property by name
    static std::string getProp(const std::string& name);

    /// Read all system properties and return as key-value map
    static std::map<std::string, std::string> getAllProps();

    /// Read all properties matching a prefix (e.g. "ro.board.")
    static std::map<std::string, std::string> getPropsWithPrefix(const std::string& prefix);
};

} // namespace phonescope
