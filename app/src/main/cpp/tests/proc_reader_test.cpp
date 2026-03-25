#include <gtest/gtest.h>
#include "proc_reader.h"
#include "file_util.h"
#include <fstream>
#include <cstdio>

using namespace phonescope;

class ProcReaderTest : public ::testing::Test {
protected:
    void SetUp() override {
        // Create a mock meminfo file
        std::ofstream mem("test_meminfo.txt");
        mem << "MemTotal:       16384 kB\n";
        mem << "MemFree:        8192 kB\n";
        mem << "MemAvailable:   10000 kB\n";
        mem.close();
    }

    void TearDown() override {
        std::remove("test_meminfo.txt");
    }
};

// Since ProcReader uses hardcoded paths like /proc/meminfo in its implementation,
// we'd typically inject the path or mock the file system for a real unit test.
// For this stub, we just verify the struct layout and basic functionality 
// on the host if the files happen to exist (e.g. on Linux).
// We'll skip tests that strictly require Android /proc if they fail.

TEST_F(ProcReaderTest, StructLayout) {
    CpuStat stat;
    stat.name = "cpu0";
    stat.user = 100;
    stat.system = 50;
    
    EXPECT_EQ(stat.name, "cpu0");
    EXPECT_EQ(stat.user, 100);
    EXPECT_EQ(stat.system, 50);
}

TEST_F(ProcReaderTest, ParseMemInfoLogic) {
    // If we were parsing test_meminfo.txt directly:
    auto lines = FileUtil::readFileLines("test_meminfo.txt");
    std::map<std::string, long long> meminfo;
    for (const auto& line : lines) {
        auto kv = FileUtil::parseKeyValue(line);
        if (!kv.first.empty() && !kv.second.empty()) {
            std::stringstream ss(kv.second);
            long long val;
            if (ss >> val) {
                meminfo[kv.first] = val;
            }
        }
    }
    
    EXPECT_EQ(meminfo["MemTotal"], 16384);
    EXPECT_EQ(meminfo["MemFree"], 8192);
    EXPECT_EQ(meminfo["MemAvailable"], 10000);
}
