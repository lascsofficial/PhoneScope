// Placeholder for nlohmann/json single-header library.
// Download the actual header from:
// https://github.com/nlohmann/json/releases/download/v3.11.3/json.hpp
//
// Place the full json.hpp file here to replace this placeholder.
// It is ~24,000 lines and too large to inline.
//
// Usage in engine code:
//   #include <nlohmann/json.hpp>
//   using json = nlohmann::json;

#pragma once

// Minimal stub to allow compilation until the real header is placed here.
// Engine stubs currently return raw string literals, so this is not
// needed yet. It will be required starting Sprint S3.

#ifndef NLOHMANN_JSON_HPP
#define NLOHMANN_JSON_HPP

#warning "Using nlohmann/json placeholder — download the real json.hpp from GitHub"

#include <string>
#include <map>

namespace nlohmann {
    // Minimal stub — NOT the real library
    class json {
    public:
        json() = default;
        std::string dump(int = -1) const { return "{}"; }
        json& operator[](const std::string&) { return *this; }
        json& operator=(const std::string&) { return *this; }
        json& operator=(int) { return *this; }
        json& operator=(double) { return *this; }
        json& operator=(bool) { return *this; }
    };
}

#endif
