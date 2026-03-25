# PhoneScope

PhoneScope is a professional-grade Android device intelligence platform. Built with a high-performance C++20 native engine and a modern Jetpack Compose UI, it offers deep hardware introspection and real-time system monitoring.

## Features (Planned)
- **Tier A Inspectors**: CPU, Memory, GPU, Thermal, Kernel, Storage, AI/NNAPI, and Process analysis.
- **Live Flight Recorder**: 1-second background sampling of critical system metrics.
- **Design Language**: OLED-black aesthetic with electric cyan accents and smooth micro-animations.

## Technology Stack
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + MVI, Clean Architecture
- **Dependency Injection**: Hilt
- **Database**: Room
- **Native Engine**: C++20, NDK r27, CMake 3.22+
- **Serialization**: kotlinx.serialization (JNI bridge uses JSON payloads)

## Building the Project

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17
- Android SDK 35
- NDK `27.2.12479018`
- CMake 3.22.1+

### Setup
1. Clone the repository: `git clone https://github.com/lascsofficial/PhoneScope.git`
2. Download the `nlohmann/json` standalone header from [GitHub Releases (v3.11.3)](https://github.com/nlohmann/json/releases/tag/v3.11.3).
3. Place `json.hpp` into `app/src/main/cpp/third_party/nlohmann/`.
4. Sync project with Gradle files.
5. Build and run the `app` module on a compatible device/emulator (API 26+).

## License
Copyright 2026. All Rights Reserved.