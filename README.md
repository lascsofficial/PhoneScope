# PhoneScope

PhoneScope is a professional-grade Android device intelligence platform. Built with a high-performance C++20 native engine and a modern Jetpack Compose UI, it offers deep hardware introspection and real-time system monitoring.

## Features

### Native C++ Engine (8 Modules)
| Module | Capabilities |
|--------|-------------|
| **CPU** | Topology, per-core frequencies, governor, cache hierarchy, vulnerabilities |
| **Memory** | Full `/proc/meminfo` parsing, live MemFree/MemAvailable/SwapFree sampling |
| **GPU** | Adreno/Mali vendor detection, sysfs frequency limits, live `gpubusy` polling |
| **Thermal** | All thermal zones + cooling devices via sysfs |
| **Kernel** | `uname()` metadata, SELinux enforcing state, root detection |
| **Storage** | Sequential read/write benchmark (MB/s) with tier classification |
| **AI/NPU** | EdgeTPU, Hexagon DSP, MediaTek APU detection via sysfs + properties |
| **Process** | Full `/proc/[pid]` traversal: cmdline, stat, status, VmRSS, threads |

### Jetpack Compose UI (4 Screens)
- **Dashboard**: Live vitals grid (4 animated circular gauges) + glassmorphic hardware spec cards
- **Scan**: Sequential deep scan across all modules with animated progress + result cards
- **Monitor**: Real-time 1Hz flight recorder with custom Canvas sparkline charts (60s history)
- **Settings**: Toggle controls, sample rate slider, About section with app/engine metadata

### Design Language
- OLED-black base (`#000000`) with Electric Cyan (`#00D4FF`) accent
- Glassmorphic card surfaces with gradient backgrounds
- Smooth micro-animations on all interactive elements
- Custom typography: JetBrains Mono (data), Outfit (headings), DM Sans (body)

## Technology Stack
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Database**: Room
- **Native Engine**: C++20, NDK r27, CMake 3.22+
- **JSON Bridge**: nlohmann/json (C++) ↔ kotlinx.serialization (Kotlin)

## Building the Project

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17
- Android SDK 35
- NDK `27.2.12479018`
- CMake 3.22.1+

### Setup
1. Clone the repository: `git clone https://github.com/lascsofficial/PhoneScope.git`
2. The `nlohmann/json` header is already included at `app/src/main/cpp/third_party/nlohmann/json.hpp`.
3. Sync project with Gradle files.
4. Build and run the `app` module on a compatible device/emulator (API 26+).

## Project Structure
```
app/src/main/
├── kotlin/com/phonescope/inspector/
│   ├── di/              # Hilt modules
│   ├── data/
│   │   ├── jni/         # NativeEngine.kt JNI wrapper
│   │   └── local/       # Room database + DAOs
│   ├── domain/model/    # @Serializable data classes
│   └── ui/
│       ├── theme/       # OLED-black color system + typography
│       ├── components/  # HardwareMeter gauge
│       ├── navigation/  # NavGraph + BottomNavBar
│       ├── dashboard/   # Dashboard screen + ViewModel
│       ├── scan/        # Scan screen + ViewModel
│       ├── monitor/     # Monitor screen + ViewModel
│       └── settings/    # Settings screen
├── cpp/
│   ├── engine/          # 8 inspector modules (C++20)
│   ├── util/            # proc_reader, sys_reader, file_util, prop_reader
│   ├── jni/             # JNI bridge
│   └── third_party/     # nlohmann/json
└── AndroidManifest.xml
```

## License
Copyright 2026. All Rights Reserved.