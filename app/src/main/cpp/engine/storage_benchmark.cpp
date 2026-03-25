#include "storage_benchmark.h"
#include <nlohmann/json.hpp>
#include <fstream>
#include <chrono>
#include <vector>
#include <cstdio>
#include <unistd.h>
#include <fcntl.h>

using json = nlohmann::json;

namespace phonescope {

std::string StorageBenchmark::run() {
    json result;
    
    // We will do a small 32MB sequential benchmark in /data/local/tmp 
    // since we don't have the context filesDir easily available from generic C++ without JNI plumbing.
    // In production, the Kotlin layer passes the specific cacheDir to benchmark.
    const std::string testFile = "/data/local/tmp/phonescope_bench.tmp";
    const size_t bufferSize = 1024 * 1024; // 1 MB
    const int iterCount = 32; // 32 MB total
    
    std::vector<char> buffer(bufferSize, 0xAF);

    // Write Test (O_SYNC for more accurate physical write, but O_DIRECT is best if supported. We'll use fsync.)
    int fd = open(testFile.c_str(), O_CREAT | O_WRONLY | O_TRUNC, 0666);
    if (fd >= 0) {
        auto t0 = std::chrono::high_resolution_clock::now();
        for (int i = 0; i < iterCount; i++) {
            write(fd, buffer.data(), bufferSize);
        }
        fsync(fd);
        auto t1 = std::chrono::high_resolution_clock::now();
        close(fd);

        double writeSeconds = std::chrono::duration<double>(t1 - t0).count();
        double writeSpeed = iterCount / writeSeconds; // MB/s
        result["sequential_write_mbps"] = writeSpeed;
    } else {
        result["sequential_write_mbps"] = -1.0;
        result["error"] = "Cannot open /data/local/tmp";
        return result.dump();
    }

    // Read Test
    fd = open(testFile.c_str(), O_RDONLY);
    if (fd >= 0) {
        // Drop caches if we had root, but we don't. So this measures cached read speed largely.
        auto t0 = std::chrono::high_resolution_clock::now();
        for (int i = 0; i < iterCount; i++) {
            read(fd, buffer.data(), bufferSize);
        }
        auto t1 = std::chrono::high_resolution_clock::now();
        close(fd);

        double readSeconds = std::chrono::duration<double>(t1 - t0).count();
        double readSpeed = iterCount / readSeconds; // MB/s
        result["sequential_read_mbps"] = readSpeed;
    } else {
        result["sequential_read_mbps"] = -1.0;
    }

    // Cleanup
    remove(testFile.c_str());

    // Basic Tiers
    double writeSpeed = result.value("sequential_write_mbps", 0.0);
    if (writeSpeed > 1000) result["tier"] = "Flagship (UFS 3.1+)";
    else if (writeSpeed > 400) result["tier"] = "High-End (UFS 2.1+)";
    else if (writeSpeed > 100) result["tier"] = "Mid-Range (eMMC 5.1/UFS 2.0)";
    else result["tier"] = "Budget (eMMC)";

    return result.dump();
}

} // namespace phonescope
