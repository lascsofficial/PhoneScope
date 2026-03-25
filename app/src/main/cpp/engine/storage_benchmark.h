#pragma once
#include <string>

namespace phonescope {

class StorageBenchmark {
public:
    /// Run storage benchmark: sequential + random 4K read/write
    static std::string run();
};

} // namespace phonescope
