#include <gtest/gtest.h>
#include "file_util.h"
#include <fstream>
#include <cstdio>

using namespace phonescope;

class FileUtilTest : public ::testing::Test {
protected:
    void SetUp() override {
        std::ofstream out("test_file.txt");
        out << "line1\n";
        out << "key: value \n";
        out << "  trimmed  \n";
        out.close();
    }

    void TearDown() override {
        std::remove("test_file.txt");
    }
};

TEST_F(FileUtilTest, TrimWhiteSpace) {
    EXPECT_EQ(FileUtil::trim("   hello world  "), "hello world");
    EXPECT_EQ(FileUtil::trim("no_spaces"), "no_spaces");
    EXPECT_EQ(FileUtil::trim("   "), "");
}

TEST_F(FileUtilTest, ParseKeyValue) {
    auto kv1 = FileUtil::parseKeyValue("processor : 0");
    EXPECT_EQ(kv1.first, "processor");
    EXPECT_EQ(kv1.second, "0");

    auto kv2 = FileUtil::parseKeyValue("MemTotal:       16384 kB");
    EXPECT_EQ(kv2.first, "MemTotal");
    EXPECT_EQ(kv2.second, "16384 kB");

    auto kv3 = FileUtil::parseKeyValue("no_delimiter");
    EXPECT_EQ(kv3.first, "no_delimiter");
    EXPECT_EQ(kv3.second, "");
}

TEST_F(FileUtilTest, ReadLines) {
    auto lines = FileUtil::readFileLines("test_file.txt");
    ASSERT_EQ(lines.size(), 3);
    EXPECT_EQ(lines[0], "line1");
    EXPECT_EQ(lines[1], "key: value ");
    EXPECT_EQ(lines[2], "  trimmed  ");
}

TEST_F(FileUtilTest, ParseLong) {
    EXPECT_EQ(FileUtil::parseLong("12345").value_or(0), 12345);
    EXPECT_EQ(FileUtil::parseLong("-42").value_or(0), -42);
    EXPECT_FALSE(FileUtil::parseLong("not a number").has_value());
    EXPECT_FALSE(FileUtil::parseLong("").has_value());
}
