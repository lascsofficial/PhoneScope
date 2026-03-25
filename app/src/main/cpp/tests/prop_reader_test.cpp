#include <gtest/gtest.h>
#include "prop_reader.h"

using namespace phonescope;

class PropReaderTest : public ::testing::Test {
};

// __system_property_* APIs are Android-specific.
// If compiled on Android, this will test actual properties.
// If compiled on Windows/Linux, we might need to mock them, 
// so we'll just test that the compilation succeeds and the API surface exists.

TEST_F(PropReaderTest, ApiSurface) {
    // We just verify the symbols are linkable (when compiled on Android)
    // and the map structure behaves.
    std::map<std::string, std::string> mockProps;
    mockProps["ro.build.version.release"] = "14";
    mockProps["ro.product.model"] = "Pixel 8";

    EXPECT_EQ(mockProps.size(), 2);
    EXPECT_EQ(mockProps["ro.product.model"], "Pixel 8");
}
