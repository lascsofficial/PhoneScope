#include <gtest/gtest.h>
#include "sys_reader.h"

using namespace phonescope;

class SysReaderTest : public ::testing::Test {
};

// Similar to ProcReader, actual /sys files won't exist on all host OSes 
// (especially Windows/macOS), but we can test struct initialization.

TEST_F(SysReaderTest, CacheInfoStruct) {
    CacheInfo info;
    info.level = 1;
    info.type = "Data";
    info.size = "64K";
    info.ways_of_associativity = 8;
    info.number_of_sets = 128;

    EXPECT_EQ(info.level, 1);
    EXPECT_EQ(info.type, "Data");
    EXPECT_EQ(info.size, "64K");
    EXPECT_EQ(info.ways_of_associativity, 8);
    EXPECT_EQ(info.number_of_sets, 128);
}

TEST_F(SysReaderTest, ThermalZoneStruct) {
    ThermalZone zone;
    zone.type = "cpu-thermal";
    zone.temp = 45000;

    EXPECT_EQ(zone.type, "cpu-thermal");
    EXPECT_EQ(zone.temp, 45000);
}

TEST_F(SysReaderTest, CoolingDeviceStruct) {
    CoolingDevice dev;
    dev.type = "processor";
    dev.max_state = 10;
    dev.cur_state = 2;

    EXPECT_EQ(dev.type, "processor");
    EXPECT_EQ(dev.max_state, 10);
    EXPECT_EQ(dev.cur_state, 2);
}
