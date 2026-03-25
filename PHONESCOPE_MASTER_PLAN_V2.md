# PhoneScope — Master Project Plan v2.0
### The World's Most Complete Android Device Intelligence Platform
### C++20 · Kotlin · NDK r27 · Jetpack Compose · Professional Grade
---

> **Competitive Position**: PhoneScope is not competing with CPU-Z, AIDA64, or DevCheck.
> It is replacing all three — while doing things none of them have ever attempted.

---

## 1. Project Identity

| Field | Value |
|---|---|
| **App Name** | PhoneScope |
| **Tagline** | *Every secret your phone keeps — revealed.* |
| **Package** | `com.phonescope.inspector` |
| **Version** | 1.0.0 (Plan v2.0) |
| **Language Stack** | Kotlin (UI / Business Logic) + C++20 (Native Inspection Engine) |
| **Min SDK** | API 26 (Android 8.0 Oreo) |
| **Target SDK** | API 35 (Android 15) |
| **Architecture Pattern** | Clean Architecture + MVVM + MVI (UI state) |
| **Build System** | Gradle KTS + CMake 3.22+ |
| **NDK Version** | r27 (latest stable) |
| **ABI Filters** | arm64-v8a, x86_64 |
| **Monetization** | Free core + Pro (one-time IAP) for live monitor, scheduled scans, PDF export |

---

## 2. Competitive Landscape & Gap Analysis

### 2.1 What Competitors Do

| App | Strengths | Critical Weaknesses |
|---|---|---|
| **CPU-Z** | CPU + memory basics | No live monitor, no reports, dated UI, no security |
| **AIDA64** | Broad coverage | No time-series, no actionable insights, ugly UI, no benchmarks |
| **DevCheck** | Clean UI | Surface-level data, no native engine, no battery tracking |
| **AnTuTu** | Benchmark scores | Not an inspector, no system settings, no reports |
| **Geekbench** | CPU/GPU benchmarks | Single purpose, no device introspection at all |
| **Phone Info Samsung** | Samsung-specific deep props | Single OEM, no reports, no monitor |
| **Droid Info** | Simple, fast | Too shallow, no NDK, no connectivity deep-dive |

### 2.2 PhoneScope's Exclusive Advantages

Every item below is something **no competitor currently offers as a complete package**:

1. ✅ C++ native engine reading `/proc` + `/sys` + syscalls directly
2. ✅ SoC identity verification (catches fake/relabeled hardware)
3. ✅ Battery health timeline (months of degradation tracking)
4. ✅ Live real-time flight recorder (persistent foreground service)
5. ✅ Thermal throttling push notifications
6. ✅ AI/NPU/DSP tier detection and ML benchmark
7. ✅ Bluetooth codec matrix (LDAC, aptX HD, LC3, etc.)
8. ✅ USB Power Delivery negotiation readout
9. ✅ OEM skin detection + aggressiveness profile
10. ✅ Security audit with offline CVE cross-reference
11. ✅ Shareable spec-card image (social-native format)
12. ✅ Process monitor with per-PID CPU/RAM breakdown
13. ✅ Widevine DRM level + codec hardware matrix
14. ✅ Display calibration & color accuracy estimation
15. ✅ Biometric security class + TEE / StrongBox presence
16. ✅ Carrier aggregation + 5G band type (mmWave vs Sub-6)
17. ✅ Full `ro.*` system property explorer with search
18. ✅ Hardware feature database comparison (500+ devices)
19. ✅ Glance widgets + WorkManager scheduled scans
20. ✅ Dead pixel test + burn-in detector + touch point counter

---

## 3. Complete Module Registry (v2.0 — 28 Modules)

### TIER A — Hardware Core (C++ Native Engine)
| # | Module | Engine |
|---|---|---|
| 1 | CPU | C++ + `/proc` + `/sys` |
| 2 | Memory & RAM | C++ + `/proc/meminfo` |
| 3 | GPU | C++ + OpenGL ES + Vulkan |
| 4 | Thermal System | C++ + `/sys/class/thermal` |
| 5 | Kernel & OS | C++ + `uname()` + props |
| 6 | Storage Engine | C++ + `StatFs` + benchmark |
| 7 | SoC Verification | C++ + bundled SoC database |
| 8 | AI / NPU / DSP | C++ + NNAPI + Hexagon probe |

### TIER B — Hardware Peripherals (Kotlin + C++)
| # | Module | Engine |
|---|---|---|
| 9 | Battery & Power | Kotlin + BatteryManager + C++ `/sys` |
| 10 | Display & Screen | Kotlin + DisplayManager + Canvas |
| 11 | Camera System | Kotlin + Camera2 API |
| 12 | Audio & Speaker | Kotlin + AAudio C++ + AudioManager |
| 13 | Sensors Matrix | Kotlin + SensorManager |
| 14 | Biometrics & Security Hardware | Kotlin + BiometricManager + KeyStore |
| 15 | Input Hardware | Kotlin + InputManager + C++ |
| 16 | USB & Charging | Kotlin + UsbManager + C++ `/sys` |

### TIER C — Connectivity
| # | Module | Engine |
|---|---|---|
| 17 | Wi-Fi Deep | Kotlin + WifiManager |
| 18 | Cellular & SIM | Kotlin + TelephonyManager |
| 19 | Bluetooth & Codecs | Kotlin + BluetoothAdapter + reflection |
| 20 | NFC & UWB | Kotlin + NfcAdapter + UwbManager |
| 21 | Network Diagnostics | Kotlin + coroutines |

### TIER D — Software & Security
| # | Module | Engine |
|---|---|---|
| 22 | Security Audit | Kotlin + C++ + offline CVE DB |
| 23 | DRM & Codec Matrix | Kotlin + MediaDrm + MediaCodecList |
| 24 | OEM Skin & Customization | Kotlin + heuristic engine |
| 25 | Installed App Analyzer | Kotlin + PackageManager |
| 26 | Process Monitor | C++ + `/proc/[pid]` live reader |
| 27 | System Settings Explorer | Kotlin + Settings.* |
| 28 | ADB & Developer Flags | Kotlin + Settings.Global + Secure |

---

## 4. Module Data Points — Complete Specification

---

### Module 1 — CPU (C++)

| Data Point | Source | Notes |
|---|---|---|
| SoC name (brand + model) | `ro.board.platform`, `ro.chipname`, cross-ref SoC DB | e.g. "Snapdragon 8 Gen 3" |
| Process node (nm) | SoC database lookup | e.g. "4nm TSMC" |
| CPU architecture | `ro.product.cpu.abi` | arm64-v8a / x86_64 |
| Microarchitecture | `/proc/cpuinfo` part + revision cross-ref | e.g. "Cortex-X4 + A720 + A520" |
| Core topology (big.LITTLE / DynamIQ) | `/proc/cpuinfo` cluster grouping | Show per-cluster layout |
| Physical core count | `/proc/cpuinfo` processor count | |
| Per-core current frequency | `/sys/devices/system/cpu/cpu*/cpufreq/scaling_cur_freq` | Live, per-core |
| Per-core min / max frequency | `scaling_min_freq` / `scaling_max_freq` | |
| Available governors | `scaling_available_governors` | |
| Active governor | `scaling_governor` | |
| Governor tunables | `/sys/devices/system/cpu/cpu0/cpufreq/<governor>/` | All params |
| Cache L1d / L1i / L2 / L3 | `/sys/devices/system/cpu/cpu*/cache/index*/size` | Per-level |
| Cache line size | `coherency_line_size` | |
| Cache associativity | `ways_of_associativity` | |
| Instruction sets | `/proc/cpuinfo` Features field | NEON, SVE, SVE2, AES, SHA1/2/3, CRC32, DOTPROD, FP16, BF16 |
| ARMv8 crypto extensions | `/proc/cpuinfo` | AES, PMULL, SHA256, SHA512 |
| Pointer Authentication (PAC) | `/proc/cpuinfo` | Security feature |
| Branch Target Identification (BTI) | `/proc/cpuinfo` | Security feature |
| Memory Tagging Extension (MTE) | `/proc/cpuinfo` | Qualcomm/ARM |
| Kernel version | `uname()` | Major.minor.patch + build date |
| CPU uptime | `/proc/uptime` | Seconds since boot |
| Idle ratio | `/proc/uptime` idle / total | |
| Load averages (1/5/15 min) | `/proc/loadavg` | |
| Context switches total | `/proc/stat` ctxt | |
| Process / thread count | `/proc/stat` processes | |
| CPU time breakdown | `/proc/stat` | user / nice / system / idle / iowait / irq / softirq / steal |
| Interrupts per second | `/proc/stat` intr | |
| Per-core temperature | `/sys/class/thermal/thermal_zone*/temp` + type match | |
| CPU bogomips | `/proc/cpuinfo` | |
| Cpusets (top-app, foreground, bg) | `/dev/cpuset/*/cpus` | OEM scheduler tuning |
| EAS (Energy Aware Scheduling) | `/sys/kernel/sched_domain/` presence | |
| Schedutil tunables | `/sys/devices/system/cpu/cpu*/cpufreq/schedutil/` | rate_limit_us, hispeed_load |
| Interactive governor tunables | hispeed_freq, go_hispeed_load, min_sample_time | If present |
| CPU vulnerability mitigations | `/sys/devices/system/cpu/vulnerabilities/*` | Spectre, Meltdown, etc. |
| TDP estimate | SoC database | Watts |

---

### Module 2 — Memory & RAM (C++)

| Data Point | Source |
|---|---|
| Total RAM | `/proc/meminfo` MemTotal |
| Free / Available RAM | MemFree / MemAvailable |
| Buffers / Cached / SwapCached | `/proc/meminfo` |
| Active(anon) / Inactive(anon) | `/proc/meminfo` |
| Active(file) / Inactive(file) | `/proc/meminfo` |
| Unevictable / Mlocked | `/proc/meminfo` |
| Dirty / Writeback | `/proc/meminfo` |
| AnonPages / Mapped / Shmem | `/proc/meminfo` |
| Slab / SReclaimable / SUnreclaim | `/proc/meminfo` |
| KernelStack / PageTables | `/proc/meminfo` |
| CommitLimit / Committed_AS | `/proc/meminfo` |
| VmallocTotal / VmallocUsed | `/proc/meminfo` |
| Huge pages status | `/proc/meminfo` HugePages_* |
| Transparent huge pages | `/sys/kernel/mm/transparent_hugepage/enabled` |
| Swap total / free | `/proc/meminfo` |
| zRAM block device size | `/sys/block/zram0/disksize` |
| zRAM compressed data size | `/sys/block/zram0/compr_data_size` |
| zRAM savings % | `(orig - compr) / orig * 100` |
| zRAM algorithm | `/sys/block/zram0/comp_algorithm` |
| Memory fragmentation index | `/proc/buddyinfo` — parse free blocks per order |
| OOM score | `/proc/self/oom_score` |
| VM overcommit policy | `/proc/sys/vm/overcommit_memory` |
| LMK minfree thresholds | `/sys/module/lowmemorykiller/parameters/minfree` |
| LMK adj levels | `/sys/module/lowmemorykiller/parameters/adj` |
| Dirty writeback centisecs | `/proc/sys/vm/dirty_writeback_centisecs` |
| Dirty ratio / dirty_background_ratio | `/proc/sys/vm/dirty_*` |
| Page size | `getpagesize()` syscall |
| RAM type (LPDDR4/4X/5/5X) | SoC database + bandwidth benchmark cross-ref |
| RAM bus width (bits) | SoC database (64/128-bit) |
| RAM speed (MHz estimated) | SoC database |
| RAM channels | SoC database |
| Memory bandwidth estimate | Mini NDK benchmark (memcpy throughput) |

---

### Module 3 — GPU (C++ / OpenGL ES / Vulkan)

| Data Point | Source |
|---|---|
| GPU brand / model | `GL_VENDOR` + `GL_RENDERER` |
| GPU driver version | `GL_VERSION` |
| OpenGL ES version | `GL_VERSION` numeric |
| GLSL version | `GL_SHADING_LANGUAGE_VERSION` |
| All GL extensions (count + list) | `GL_EXTENSIONS` |
| Key extensions (ETC2, ASTC, BC7, FP16, etc.) | Parsed from extensions list |
| Max texture size | `GL_MAX_TEXTURE_SIZE` |
| Max viewport dimensions | `GL_MAX_VIEWPORT_DIMS` |
| Max vertex attribs | `GL_MAX_VERTEX_ATTRIBS` |
| Max combined texture units | `GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS` |
| Max MSAA samples | `GL_MAX_SAMPLES` |
| Max anisotropy | `GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT` |
| Vulkan supported | `vkEnumerateInstanceVersion()` |
| Vulkan API version | Major.Minor.Patch |
| Vulkan device name / type | `VkPhysicalDeviceProperties` |
| Vulkan driver version | `VkPhysicalDeviceProperties.driverVersion` |
| Vulkan vendor ID | `vendorID` |
| Vulkan device ID | `deviceID` |
| Vulkan device limits (key ones) | `VkPhysicalDeviceLimits` — max image dimensions, push constant size, etc. |
| Vulkan features (geometry shader, tessellation, etc.) | `VkPhysicalDeviceFeatures` |
| Vulkan extensions count + list | `vkEnumerateDeviceExtensionProperties()` |
| Vulkan memory heaps | `VkPhysicalDeviceMemoryProperties` |
| Vulkan 1.1 / 1.2 / 1.3 features | Promoted feature sets |
| Vulkan profiles conformance | KHR_profiles check |
| OpenCL support | `libOpenCL.so` presence + CL platform query |
| GPU current frequency | `/sys/class/kgsl/kgsl-3d0/devfreq/cur_freq` (Adreno) |
| GPU max frequency | `/sys/class/kgsl/kgsl-3d0/devfreq/max_freq` |
| GPU busy % | `/sys/class/kgsl/kgsl-3d0/gpu_busy_percentage` |
| GPU temperature | Thermal zone type match (`kgsl-3d0`, `gpu`, etc.) |
| Shader cores count | SoC database |
| TMUs / ROPs | SoC database |
| GPU GFLOPS (FP32) | SoC database |
| GPU architecture gen | SoC database (e.g. Adreno 750, Mali G920) |
| Texture compression formats supported | Parsed from GL extensions |

---

### Module 4 — Thermal System (C++)

| Data Point | Source |
|---|---|
| All thermal zones (name, type, temp) | `/sys/class/thermal/thermal_zone*/` — full enumeration |
| Trip points per zone (type + threshold) | `trip_point_*_type` / `trip_point_*_temp` |
| Cooling devices (type, max state) | `/sys/class/thermal/cooling_device*/` |
| Current cooling device state | `cur_state` |
| CPU throttle detection | Compare `scaling_cur_freq` vs `scaling_max_freq` under load |
| Throttle events counter | `/sys/kernel/debug/cpufreq/` if available |
| Thermal governor (step_wise, power_allocator) | `thermal_zone*/policy` |
| Sustained performance mode support | API 24+ `ActivityManager.isDeviceLightIdle()` cross-ref |
| Fan presence (tablet/gaming phone) | Cooling device type = "fan" |

---

### Module 5 — Kernel & OS (C++)

| Data Point | Source |
|---|---|
| Android version + codename | `Build.VERSION.RELEASE` + `ro.build.version.codename` |
| Build fingerprint | `Build.FINGERPRINT` |
| Security patch level | `Build.VERSION.SECURITY_PATCH` |
| Vendor security patch level | `ro.vendor.build.security_patch` |
| Kernel version | `uname()` sysname + release |
| Kernel build date | Parsed from `uname()` version string |
| Kernel architecture | `uname()` machine |
| SELinux mode | `/sys/fs/selinux/enforce` (0=permissive, 1=enforcing) |
| SELinux policy version | `/sys/fs/selinux/policyvers` |
| Verified boot state | `ro.boot.verifiedbootstate` (green/yellow/orange/red) |
| Bootloader unlock status | `ro.boot.flash.locked` / `ro.secure` |
| OEM unlock allowed | `ro.oem_unlock_supported` |
| Rollback protection index | `ro.boot.vbmeta.avb_version` |
| Root detection (multi-method) | su binary, Magisk app UID, `/.magisk`, `/sbin/.magisk`, prop tampering |
| Treble compliance | `ro.treble.enabled` |
| SAR / A/B partition | `ro.boot.slot_suffix` presence |
| Virtual A/B (VAB) | `ro.virtual_ab.enabled` |
| GKI (Generic Kernel Image) | `ro.kernel.version` format check |
| ART heap config | `dalvik.vm.heapsize`, `heapgrowthlimit`, `heapstartsize` |
| ART ISA | `ro.dalvik.vm.isa.*` |
| All `ro.*` properties | `__system_property_foreach()` — every readable property |
| All `persist.*` properties | Same |
| All `sys.*` properties | Same |
| IO scheduler per block device | `/sys/block/*/queue/scheduler` |
| Readahead size per block device | `/sys/block/*/queue/read_ahead_kb` |
| cgroups v1/v2 presence | `/proc/cgroups` |
| Namespaces (user, pid, net, mnt) | `/proc/self/ns/` |
| Seccomp enforcement | `/proc/self/status` Seccomp field |
| KASLR enabled | Kernel randomize_va_space heuristic |

---

### Module 6 — Storage Engine (C++ + Kotlin)

| Data Point | Source |
|---|---|
| Internal storage total / used / free | `StatFs(Environment.getDataDirectory())` |
| External SD total / used / free | `StatFs` if mounted |
| Storage type detection | `ro.boot.hardware.*` + SoC DB + `/sys/block/*/queue/rotational` |
| Storage standard (eMMC 5.1, UFS 2.1, UFS 3.1, UFS 4.0, NVMe) | SoC database + benchmark heuristic |
| Sequential read speed (live benchmark) | C++ — 128 MB sequential read test |
| Sequential write speed (live benchmark) | C++ — 128 MB sequential write test |
| Random 4K read IOPS | C++ — 4K random read benchmark |
| Random 4K write IOPS | C++ — 4K random write benchmark |
| Storage speed tier classification | Budget / Mid / Fast / Flagship based on benchmarks |
| File system type per partition | `/proc/mounts` parser |
| Mount flags per partition | `/proc/mounts` |
| All partition names + sizes | `/proc/partitions` |
| Encryption status | `ro.crypto.type` (file / block / none) |
| FBE (File-Based Encryption) | `ro.crypto.type == "file"` |
| F2FS tuning params | `/sys/fs/f2fs/*/` — gc_urgent, ovp_segments, etc. |
| Dirty page params | `/proc/sys/vm/dirty_ratio`, `dirty_background_ratio` |
| USB storage mode / MTP | `UsbManager.getAccessoryList()` |
| Adoptable storage support | `ro.additionalmounts` |

---

### Module 7 — SoC Verification Engine (C++ + Bundled DB)

**This module is PhoneScope's signature feature — no competitor has it.**

Algorithm:
```
1. Read claimed SoC from ro.board.platform / ro.chipname
2. Read GPU renderer string from OpenGL ES
3. Read CPU features from /proc/cpuinfo
4. Read max CPU frequency (all clusters)
5. Read memory bandwidth from benchmark
6. Cross-reference all 5 against bundled SoC fingerprint database
7. Compute match confidence score (0-100%)
8. If score < 85% → FLAG as "Possible hardware mislabeling"
```

Database: JSON bundled asset (~500 SoCs) with:
- SoC name → expected GPU renderer pattern
- Expected CPU feature flags
- Expected cluster topology + frequencies
- Expected memory bandwidth range
- Known aliases and OEM rebrands (e.g. Helio G99 variants)

Output:
- ✅ **Verified** — all 5 signals match
- ⚠️ **Partial Match** — 3-4 signals match, possible variant
- 🚨 **Mismatch Detected** — strong evidence of hardware substitution

---

### Module 8 — AI / NPU / DSP (C++ + NNAPI + NDK)

| Data Point | Source |
|---|---|
| NNAPI version | `ANeuralNetworks_getRuntimeFeatureLevel()` |
| NNAPI accelerator count | `ANeuralNetworks_getDeviceCount()` |
| NNAPI device names + types | Per device — CPU / GPU / ACCELERATOR / UNKNOWN |
| NNAPI supported operations | Probe key ops (CONV_2D, DEPTHWISE_CONV_2D, LSTM) |
| Hexagon DSP version | Qualcomm-specific `/sys/kernel/debug/dsp/` + props |
| Samsung NPU presence | `ro.hardware` == exynos + NNAPI ACCELERATOR device |
| MediaTek APU presence | `ro.hardware` == mt* + NNAPI ACCELERATOR |
| Google TPU presence | `ro.product.board` == slider + Tensor chip |
| AI benchmark | Run MobileNetV3 inference via NNAPI — measure ms/inference |
| AI tier classification | < 5ms = Flagship AI / 5-15ms = High / 15-40ms = Mid / >40ms = Basic |
| ML accelerator features | FP16, INT8, INT4 quantization support |
| On-device ML frameworks | TFLite delegate probe: GPU, NNAPI, Hexagon, CoreML-style |
| DSP clock speed | `/sys/kernel/debug/dsp/` where accessible |

---

### Module 9 — Battery & Power (Kotlin + C++)

| Data Point | Source |
|---|---|
| Capacity % | `BatteryManager.EXTRA_LEVEL` |
| Voltage (mV) | `EXTRA_VOLTAGE / 1000` |
| Current now (mA) | `BATTERY_PROPERTY_CURRENT_NOW / 1000` |
| Current average (mA) | `BATTERY_PROPERTY_CURRENT_AVERAGE / 1000` |
| Battery temperature (°C) | `EXTRA_TEMPERATURE / 10` |
| Charge counter (mAh) | `BATTERY_PROPERTY_CHARGE_COUNTER / 1000` |
| Energy counter (µWh) | `BATTERY_PROPERTY_ENERGY_COUNTER` |
| Health status | `EXTRA_HEALTH` — Good/Overheat/Dead/OverVoltage/ColdTemp |
| Technology | `EXTRA_TECHNOLOGY` — Li-ion / Li-poly |
| Charging status | Charging / Discharging / Full / Not Charging |
| Plug type | USB / AC / Wireless / Dock |
| Battery cycle count (API 34+) | `BATTERY_PROPERTY_CYCLE_COUNT` |
| Design capacity (mAh) | `/sys/class/power_supply/battery/charge_full_design` |
| Current full capacity (mAh) | `/sys/class/power_supply/battery/charge_full` |
| Health % | `(charge_full / charge_full_design) * 100` |
| Fast charge type | `/sys/class/power_supply/*/type` — USB_PD / USB_HVDCP / USB_HVDCP_3 |
| Charge voltage max | `/sys/class/power_supply/*/voltage_max` |
| Charge current max | `/sys/class/power_supply/*/current_max` |
| Charging wattage (live) | `voltage_now * current_now / 1,000,000` in Watts |
| Power consumption estimate (live) | Same formula in discharge |
| Battery shape | `ro.config.batt_scale` heuristic |
| Wireless charging support | `PackageManager.FEATURE_WIRELESS_CHARGING` + props |
| Reverse wireless charging | `ro.wireless.charging.reversal` + OEM props |
| USB-PD negotiated voltage | `/sys/class/power_supply/usb/voltage_now` during charge |
| USB-PD negotiated wattage | voltage × current live |
| Charger wattage capability | `voltage_max × current_max / 1,000,000` |
| Battery health timeline | Room time-series — plotted as line chart over weeks/months |
| Capacity trend | Linear regression on health % over time |

---

### Module 10 — Display & Screen (Kotlin + Canvas)

| Data Point | Source |
|---|---|
| Physical screen size (inches) | `sqrt(width² + height²) / densityDpi` |
| Screen resolution (pixels) | `DisplayMetrics` |
| Logical density (dp) | `Display.getMode()` |
| Physical DPI x/y | `xdpi` / `ydpi` |
| DPI class (ldpi/mdpi/hdpi/xhdpi/xxhdpi/xxxhdpi) | `densityDpi` ranges |
| Supported display modes (res + refresh) | `Display.getSupportedModes()` |
| Current refresh rate (Hz) | `Display.getRefreshRate()` |
| Min / Max adaptive refresh rate | `Display.getSupportedModes()` min/max |
| VRR (Variable Refresh Rate) capable | Multiple modes with same resolution |
| HDR capabilities | `Display.getHdrCapabilities()` |
| HDR types (HDR10, HDR10+, HLG, Dolby Vision) | `HdrCapabilities.getSupportedHdrTypes()` |
| Peak HDR brightness (nits) | `getDesiredMaxLuminance()` |
| Color mode (sRGB / P3 / Rec2020) | `Display.getColorMode()` |
| Wide color gamut | `isWideColorGamut()` |
| Color accuracy tier | sRGB / DCI-P3 / Rec2020 flag |
| Delta-E estimation | Heuristic: device DB lookup by panel type |
| Panel type | `ro.hardware.display.*` / OEM props heuristic (AMOLED/OLED/IPS/TFT) |
| Adaptive brightness | `Settings.System.SCREEN_BRIGHTNESS_MODE` |
| Brightness range estimate | Context clue from build props + display type |
| Screen timeout | `Settings.System.SCREEN_OFF_TIMEOUT` |
| Font scale | `Settings.System.FONT_SCALE` |
| Display size scale | `Settings.System.DISPLAY_DENSITY_FORCED` |
| Round display | `Configuration.isScreenRound()` |
| Notch / cutout type | `DisplayCutout` — CORNER / DOUBLE / PUNCH_HOLE / WATERFALL |
| Cutout safe areas (px) | `DisplayCutout.getSafeInsets*()` |
| Always-on display support | `ro.vendor.aod_enabled` / OEM props |
| Screen aspect ratio | Width / Height as fraction |
| Pixel format | `PixelFormat` |
| Dead pixel test | Full-screen solid color test sequence (R/G/B/W/K) |
| Burn-in checker | Checkerboard + invert pattern tool |
| Multitouch point count | `InputDevice.getMotionRange(AXIS_PRESSURE).flat` |
| Touch sampling rate | OEM props + hardware class heuristic |
| Touch with gloves | OEM prop heuristic |
| Stylus support | `InputDevice.SOURCE_STYLUS` |
| S-Pen pressure levels | Samsung-specific API |

---

### Module 11 — Camera System (Kotlin + Camera2)

| Data Point | Source |
|---|---|
| Camera count | `CameraManager.getCameraIdList()` |
| For each camera: | |
| → Facing (rear / front / external) | `LENS_FACING` |
| → Pixel count | `SENSOR_INFO_PIXEL_ARRAY_SIZE` W × H |
| → Megapixels (effective) | `SENSOR_INFO_ACTIVE_ARRAY_SIZE` |
| → Sensor physical size (mm) | `SENSOR_INFO_PHYSICAL_SIZE` |
| → Sensor crop factor | Calculated vs full-frame 43.3mm diagonal |
| → Pixel size (µm) | Physical size / pixel array size |
| → Focal length(s) | `LENS_INFO_AVAILABLE_FOCAL_LENGTHS` |
| → Aperture(s) | `LENS_INFO_AVAILABLE_APERTURES` |
| → Optical zoom range | Min / Max focal length ratio |
| → OIS support | `LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION` |
| → EIS support | OEM prop heuristic |
| → AF modes | `CONTROL_AF_AVAILABLE_MODES` |
| → AE modes | `CONTROL_AE_AVAILABLE_MODES` |
| → AWB modes | `CONTROL_AWB_AVAILABLE_MODES` |
| → Flash | `FLASH_INFO_AVAILABLE` |
| → Max video resolution | `StreamConfigurationMap` video sizes |
| → Max video FPS | `StreamConfigurationMap.getHighSpeedVideoFpsRanges()` |
| → Slow motion FPS modes | High-speed video ranges |
| → RAW capture support | `RAW_CAPABILITIES` |
| → 10-bit HDR output | `REQUEST_AVAILABLE_CAPABILITIES` DYNAMIC_RANGE_TEN_BIT |
| → Ultra HDR (JPEG_R) | `JPEG_R` format in output formats |
| → Depth sensor type | `DEPTH_IS_EXCLUSIVE` |
| → ToF / LiDAR | `SENSOR_INFO_LENS_SHADING_MAP_SIZE` + capability |
| → Hardware level | LEGACY / LIMITED / FULL / LEVEL_3 |
| → Noise reduction modes | `NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES` |
| → Edge enhancement modes | `EDGE_AVAILABLE_EDGE_MODES` |
| → Tone map modes | `TONEMAP_AVAILABLE_TONE_MAP_MODES` |
| → Max digital zoom | `SCALER_AVAILABLE_MAX_DIGITAL_ZOOM` |
| → Post-raw sensitivity boost | `CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE` |
| → Capture latency | `SENSOR_INFO_EXPOSURE_TIME_RANGE` |
| → Simultaneous cameras | `getPhysicalCameraIds()` count |

---

### Module 12 — Audio & Speaker (Kotlin + C++ AAudio)

| Data Point | Source |
|---|---|
| Native sample rate (Hz) | `AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE` |
| Native buffer size (frames) | `PROPERTY_OUTPUT_FRAMES_PER_BUFFER` |
| Round-trip latency estimate | From native buffer size + sample rate |
| AAudio support | NDK `AAudioStreamBuilder_openStream()` |
| AAudio MMAP low-latency | `AAUDIO_PERFORMANCE_MODE_LOW_LATENCY` |
| Low-latency audio feature | `FEATURE_AUDIO_LOW_LATENCY` |
| Pro audio feature (<20ms RTL) | `FEATURE_AUDIO_PRO` |
| Hi-Res Audio certified | `ro.audio.hires` + `ro.config.media_vol_steps` |
| Microphone count | `AudioManager.getMicrophones()` |
| Microphone directions | `MicrophoneInfo.getLocation()` |
| Speaker count (mono/stereo/quad) | `PackageManager` + OEM props heuristic |
| Stereo speaker type | Front-firing / Bottom-firing / Hybrid |
| Dolby Atmos hardware | `ro.vendor.dolby.atmos` + effect probe |
| DTS:X / Dirac / Harman tuning | OEM prop scan |
| 3.5mm headphone jack | `AudioManager.isWiredHeadsetOn()` + hardware heuristic |
| Headphone jack impedance support | High-impedance flag in OEM props |
| Spatial audio (Spatializer API) | `Spatializer.isAvailable()` (API 32+) |
| Spatial audio head tracking | `Spatializer.getSupportedHeadTrackingModes()` |
| USB audio class (UAC) support | `UsbManager` + audio device probe |
| Volume steps count | `Settings.System.VOLUME_STEPS` |
| FM radio hardware | `PackageManager.FEATURE_AUDIO_OUTPUT` + `ro.fm.enabled` |

---

### Module 13 — Sensors Matrix (Kotlin)

For **every sensor** detected:

| Data Point | Source |
|---|---|
| Sensor name | `Sensor.getName()` |
| Sensor vendor | `Sensor.getVendor()` |
| Sensor version | `Sensor.getVersion()` |
| Sensor type (code + name) | `Sensor.getType()` |
| Maximum range | `Sensor.getMaximumRange()` + unit |
| Resolution | `Sensor.getResolution()` |
| Power draw (mA) | `Sensor.getPower()` |
| Min delay (µs) | `Sensor.getMinDelay()` |
| Max delay (µs) | `Sensor.getMaxDelay()` |
| FIFO reserved event count | `Sensor.getFifoReservedEventCount()` |
| FIFO max event count | `Sensor.getFifoMaxEventCount()` |
| Reporting mode | Continuous / On-change / One-shot / Special |
| Is wake-up sensor | `Sensor.isWakeUpSensor()` |
| Is dynamic sensor | `Sensor.isDynamicSensor()` |
| Hardware handle | `Sensor.getHandle()` |

**Guaranteed sensor checklist** (show ✅ / ❌ for each):
Accelerometer · Gyroscope · Magnetometer · Barometer · Proximity · Ambient Light · Gravity · Linear Acceleration · Rotation Vector · Game Rotation Vector · Geomagnetic Rotation Vector · Step Counter · Step Detector · Significant Motion · Tilt Detector · Heart Rate · Heart Beat · Fingerprint · Face Authenticate sensor · SAR sensor · Pedometer · Device Temperature · Screen Temperature · Humidity · Color · Hinge Angle · Pose 6DOF · Accelerometer Limited Axes · Gyroscope Limited Axes

**Rare sensors** (flag as special): Radiation sensor · Blood oxygen (SpO2) · ECG · Thermal camera · Depth / ToF

---

### Module 14 — Biometrics & Security Hardware (Kotlin)

| Data Point | Source |
|---|---|
| Fingerprint sensor presence | `BiometricManager` + `PackageManager.FEATURE_FINGERPRINT` |
| Fingerprint sensor type | Heuristic: optical/ultrasonic/capacitive/side-mounted via OEM props |
| Fingerprint sensor location | Under-display / rear / side / front |
| Face unlock presence | `FEATURE_FACE` |
| Face unlock type | 2D (camera) vs 3D (IR structured light) — OEM props heuristic |
| Iris scanner | `FEATURE_IRIS` |
| Biometric class (Class 1/2/3) | `BiometricManager.canAuthenticate()` strength flags |
| StrongBox Keymaster | `KeyInfo.isInsideSecureHardware()` |
| TEE (Trusted Execution Environment) | `ro.hardware.keystore` (e.g. softkeymaster, tee, strongbox) |
| Hardware-backed Keystore | `KeyInfo.getSecurityLevel()` |
| Android Keystore key attestation | Try attestation challenge — succeed = hardware-backed |
| Secure Element (eSE) | `IsoDep` NFC tag presence probe + `ro.se.type` |
| Google Titan M / Titan M2 | `ro.hardware.security_chip` (Pixel-specific) |
| Platform security architecture | `ro.boot.secureboot` |

---

### Module 15 — Input Hardware (Kotlin + C++)

| Data Point | Source |
|---|---|
| All input devices | `InputManager.getInputDeviceIds()` — full enumeration |
| For each device: name, vendor, product ID | `InputDevice` |
| Device sources (touchscreen, keyboard, mouse, gamepad, etc.) | `InputDevice.getSources()` |
| Keyboard type (none / alphanumeric / QWERTY) | `InputDevice.getKeyboardType()` |
| Key layout file | `InputDevice.getKeyCharacterMap()` |
| Vibrator presence | `Vibrator.hasVibrator()` |
| Vibrator amplitude control | `Vibrator.hasAmplitudeControl()` |
| Vibrator type | ERM / LRA / Piezo (heuristic from OEM props) |
| Vibrator effect support | `Vibrator.areEffectsSupported()` — CLICK, TICK, DOUBLE_CLICK, HEAVY_CLICK |
| Vibrator primitive support | CLICK, THUD, SPIN, QUICK_FALL, etc. |
| IR blaster | `ConsumerIrManager.hasIrEmitter()` |
| IR carrier frequency range | `ConsumerIrManager.getCarrierFrequencies()` |
| Stylus present (active) | `InputDevice.SOURCE_STYLUS` |
| Stylus tilt / orientation support | `MotionEvent.AXIS_TILT` |
| Stylus pressure levels | `InputDevice.getMotionRange(AXIS_PRESSURE)` |
| Pointer precision | `InputDevice.getMotionRange(AXIS_X).getResolution()` |
| Touch report rate (Hz) | OEM prop heuristic + game mode props |

---

### Module 16 — USB & Charging (Kotlin + C++)

| Data Point | Source |
|---|---|
| USB version | `ro.usb.pid_suffix` + hardware heuristic (USB 2.0 / 3.0 / 3.1 / 3.2 / 4.0) |
| USB spec speed | 480 Mbps / 5 Gbps / 10 Gbps / 40 Gbps |
| USB OTG support | `PackageManager.FEATURE_USB_HOST` |
| USB data role (host/device/dual) | `UsbManager` |
| USB-C presence | Hardware heuristic from build props |
| USB Power Delivery support | `/sys/class/power_supply/*/type` — USB_PD |
| USB-PD version | 2.0 / 3.0 / 3.1 heuristic from max wattage |
| Negotiated charge voltage (V) | `/sys/class/power_supply/usb/voltage_now` |
| Negotiated charge current (A) | `/sys/class/power_supply/usb/current_now` |
| Negotiated wattage (live) | V × I |
| Max supported charge wattage | `voltage_max × current_max` |
| Fast charge protocol | USB-PD / HVDCP 3 / HVDCP 3.5 / PPS / VOOC / SuperDart / Warp heuristic |
| DisplayPort over USB-C | `ro.config.dp_altmode` / display port OEM prop |
| MHL support | `ro.config.mhl` |
| Accessory Mode | `UsbManager.ACTION_USB_ACCESSORY_ATTACHED` |
| Wireless charging Qi wattage | OEM prop heuristic + power supply type |
| Reverse wireless charging wattage | OEM prop heuristic |
| Magnetic charging | OEM prop (MagSafe-style) |

---

### Module 17 — Wi-Fi Deep (Kotlin)

| Data Point | Source |
|---|---|
| Wi-Fi standard (802.11 a/b/g/n/ac/ax/be) | `WifiInfo.getWifiStandard()` API 30+ |
| Wi-Fi generation label (Wi-Fi 4/5/6/6E/7) | Derived from standard |
| 6 GHz band support (Wi-Fi 6E) | `WifiManager.is6GHzBandSupported()` |
| 60 GHz band support (WiGig) | `WifiManager.is60GHzBandSupported()` |
| Current band (2.4 / 5 / 6 GHz) | `WifiInfo.getFrequency()` |
| Current channel | Frequency → channel conversion |
| RSSI (dBm) | `WifiInfo.getRssi()` |
| Link speed (Mbps) Tx/Rx | `WifiInfo.getLinkSpeed()` / `getRxLinkSpeedMbps()` |
| Max supported link speed | `WifiInfo.getMaxSupportedTxLinkSpeedMbps()` |
| Security type (Open/WPA2/WPA3/OWE) | `WifiInfo.getCurrentSecurityType()` |
| WPA3-SAE support | `WifiManager.isWpa3SaeSupported()` |
| WPA3-Enterprise 192-bit | `WifiManager.isWpa3SaeH2eSupported()` |
| PMF (Protected Management Frames) | `WifiManager.isEnhancedOpenSupported()` |
| Wi-Fi Direct support | `WifiP2pManager` |
| Wi-Fi Display (Miracast) | `WifiManager.isP2pSupported()` + Display projection |
| TDLS support | `WifiManager.isTdlsSupported()` |
| STA+STA (dual concurrent) | `WifiManager.isStaStaConcurrencySupported()` |
| STA+AP concurrent | `WifiManager.isAp()` capability |
| Aware (NAN) support | `WifiAwareManager` availability |
| RTT ranging (802.11mc) | `WifiRttManager.isAvailable()` |
| MAC randomization policy | `WifiInfo.getMacAddress()` — randomized prefix check |
| BSSID of current AP | `WifiInfo.getBSSID()` |
| SSID | `WifiInfo.getSSID()` |
| IP address (IPv4 + IPv6) | `LinkProperties` |
| Private DNS status | `LinkProperties.getPrivateDnsServerName()` |
| DNS-over-HTTPS support | `ConnectivityManager` + OS version check |
| MTU size | `LinkProperties.getMtu()` |

---

### Module 18 — Cellular & SIM (Kotlin)

| Data Point | Source |
|---|---|
| Phone count (SIM slots) | `TelephonyManager.getPhoneCount()` |
| DSDS / DSDA architecture | `TelephonyManager.isMultiSimEnabled()` |
| Active SIMs count | Per-slot subscription check |
| For each SIM: | |
| → Operator name | `TelephonyManager.getNetworkOperatorName()` |
| → MCC + MNC | `TelephonyManager.getNetworkOperator()` |
| → ISO country code | `TelephonyManager.getNetworkCountryIso()` |
| → Network type (2G/3G/4G/5G) | `TelephonyManager.getNetworkType()` |
| → 5G sub-type (NSA / SA) | `TelephonyManager.getNrState()` |
| → 5G type (Sub-6 / mmWave) | `ServiceState.getNrFrequencyRange()` |
| → Data roaming status | `TelephonyManager.isNetworkRoaming()` |
| → VoLTE registered | `TelephonyManager.isImsRegistered()` |
| → VoWiFi (Wi-Fi calling) | `TelephonyManager.isWifiCallingEnabled()` |
| → VoNR (5G voice) | `TelephonyManager.isNrAvailable()` |
| → IMS registration state | `ImsManager.getInstance().isRegistered()` |
| → Signal strength (ASU + dBm) | `TelephonyManager.getSignalStrength()` |
| → LTE signal: RSRP/RSRQ/RSSNR/CQI | `CellSignalStrengthLte` |
| → 5G NR signal: SSRSRP/SSRSRQ/SSSINR | `CellSignalStrengthNr` |
| → Cell ID + LAC / TAC | `CellInfo` |
| → Carrier aggregation bands | `ServiceState.getCellBandwidths()` |
| → MIMO layers | `CellSignalStrengthNr.getSsRsrpDbm()` extended |
| → IMEI (with permission) | `TelephonyManager.getImei()` |
| → ICCID (SIM card ID) | `TelephonyManager.getSimSerialNumber()` |
| → eSIM support | `EuiccManager.isEnabled()` |
| → eSIM EID | `EuiccManager.getEid()` |
| → SIM type (nano / eSIM / hybrid) | Hardware heuristic |
| → Emergency call capability | `TelephonyManager.getEmergencyNumberList()` |
| Satellite connectivity support | `SatelliteManager` (API 34+) |
| Emergency SOS via satellite | `SatelliteManager.isEmergencyModeEnabled()` |

---

### Module 19 — Bluetooth & Codecs (Kotlin + Reflection)

| Data Point | Source |
|---|---|
| Bluetooth supported | `BluetoothAdapter.getDefaultAdapter() != null` |
| BT version estimate | `ro.bluetooth.library_version` + API cross-ref |
| BT 5.x features (LE Audio, LC3) | `BluetoothAdapter.isLe2MPhySupported()` etc. |
| LE 2M PHY | `BluetoothAdapter.isLe2MPhySupported()` |
| LE Coded PHY | `BluetoothAdapter.isLeCodedPhySupported()` |
| LE Extended Advertising | `BluetoothAdapter.isLeExtendedAdvertisingSupported()` |
| LE Periodic Advertising | `BluetoothAdapter.isLePeriodicAdvertisingSupported()` |
| LE Audio (BT 5.2) | `BluetoothAdapter.isLeAudioSupported()` |
| BIS broadcast audio | `BluetoothAdapter.isLeAudioBroadcastSourceSupported()` |
| LC3 codec | `isLeAudioSupported()` |
| Bluetooth codecs matrix: | |
| → SBC | Always yes if BT present |
| → AAC | `BluetoothCodecConfig.SOURCE_CODEC_TYPE_AAC` availability |
| → aptX | Reflection on `BluetoothA2dp` codec list |
| → aptX HD | Same |
| → aptX Adaptive | Same |
| → LDAC | Same (Sony) |
| → Samsung Scalable | OEM codec probe |
| → LHDC / HWA | OEM codec probe |
| Bluetooth profiles: A2DP / HFP / HSP / AVRCP / HID / PBAP / MAP / PAN / GATT | `BluetoothAdapter.getProfileProxy()` presence |
| AVRCP version | Reflection on `BluetoothAvrcpController` |
| Max BT connections | OEM prop heuristic |
| BT MAC address (partial) | `BluetoothAdapter.getAddress()` (pre-API29) |

---

### Module 20 — NFC & UWB (Kotlin)

| Data Point | Source |
|---|---|
| NFC supported | `NfcAdapter.getDefaultAdapter()` |
| NFC enabled state | `NfcAdapter.isEnabled()` |
| NFC controller chipset | `ro.nfc.port` / OEM props |
| NFC standards (ISO 14443 A/B, ISO 15693, ISO 18092) | `PackageManager` feature flags |
| NFC card emulation (HCE) | `FEATURE_NFC_HOST_CARD_EMULATION` |
| NFC off-host (eSE) emulation | `FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE` |
| NFC off-host (UICC) emulation | `FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC` |
| Beam / Android Beam (legacy) | `NfcAdapter.isNdefPushEnabled()` |
| UWB supported | `PackageManager.FEATURE_UWB` |
| UWB ranging capability | `UwbManager.retrieveSpecificationInfo()` |
| UWB channels + preamble codes | `UwbSpecification` |
| UWB FiRa version | `FiraSpecificationParams` |
| Matter / Thread support | `FEATURE_THREAD_NETWORK` (API 35) |
| Wi-Fi Aware (NAN) | `WifiAwareManager.isAvailable()` |

---

### Module 21 — Network Diagnostics (Kotlin)

| Data Point | Source |
|---|---|
| Active network type | `ConnectivityManager.getActiveNetwork()` |
| Network capabilities | `NetworkCapabilities` — NOT_METERED, VALIDATED, etc. |
| IPv4 address | `LinkProperties` |
| IPv6 addresses | `LinkProperties.getLinkAddresses()` |
| IPv6 supported | Any non-link-local IPv6 address present |
| Default DNS servers | `LinkProperties.getDnsServers()` |
| DNS resolution speed (ms) | Live benchmark: resolve 5 domains, average |
| Ping test (ms) | ICMP to 8.8.8.8 via Java socket |
| NAT type detection | STUN probe (RFC 3489) |
| VPN active detection | `NetworkCapabilities.NET_CAPABILITY_NOT_VPN` |
| VPN type (if active) | `NetworkCapabilities.TRANSPORT_VPN` |
| Private DNS status | `PRIVATE_DNS_MODE` setting |
| Hotspot capability | `WifiManager.isPortableHotspotSupported()` |
| Download speed estimate | 1 MB fetch from CDN with timing |
| Upload speed estimate | 512 KB POST with timing |

---

### Module 22 — Security Audit (Kotlin + C++ + Offline DB)

**Score: 0–100 Security Score with tier (Critical / At Risk / Fair / Good / Hardened)**

| Check | Method | Impact |
|---|---|---|
| SELinux enforcing | `/sys/fs/selinux/enforce` | High |
| Bootloader locked | `ro.boot.verifiedbootstate` = green | High |
| USB debugging on | `Settings.Global.ADB_ENABLED` | Medium |
| ADB over network | `Settings.Global.ADB_WIFI_ENABLED` | High |
| OEM unlock enabled | `ro.oem_unlock_supported` + state | High |
| Unknown sources | `Settings.Secure.INSTALL_NON_MARKET_APPS` | Medium |
| Developer options on | `Settings.Global.DEVELOPMENT_SETTINGS_ENABLED` | Low |
| Root detected | Multi-method heuristic | Critical |
| Magisk detected | App UID scan + hide check | Critical |
| Kernel CVE check | Kernel version vs bundled CVE database | High |
| Security patch age | Days since last patch | Medium |
| Vendor patch age | Days since vendor patch | Medium |
| Accessibility services (count) | `AccessibilityManager.getEnabledAccessibilityServiceList()` | Medium |
| Device admin apps (count) | `DevicePolicyManager.getActiveAdmins()` | Medium |
| VPN always-on | `Settings.Secure.ALWAYS_ON_VPN_APP` | Info |
| Biometric class | Class 1 (Convenience) = flag | Medium |
| StrongBox absence | `KeyInfo.isInsideSecureHardware()` = false | Medium |
| Hardware keystore absent | Security level = Software | High |
| Private DNS off | `PRIVATE_DNS_MODE` = off | Low |
| Play Integrity verdict | Play Integrity API call | Info |
| Mock location on | `Settings.Secure.ALLOW_MOCK_LOCATION` | Low |
| Backup enabled | `Settings.Secure.BACKUP_ENABLED` | Info |
| Google Play Protect status | `GoogleApiAvailability` | Medium |
| Bluetooth discoverability | `BluetoothAdapter.getScanMode()` | Low |
| NFC on + screen off | `NfcAdapter` + screen lock settings | Low |

**Offline CVE Database**: JSON asset bundled — kernel version ranges mapped to CVE IDs + severity + summary. Updated each app release.

---

### Module 23 — DRM & Codec Matrix (Kotlin)

| Data Point | Source |
|---|---|
| Widevine DRM level | `MediaDrm(WIDEVINE_UUID).getPropertyString("securityLevel")` |
| Widevine streaming quality | L1=4K / L2=HD / L3=SD only |
| PlayReady support | `MediaDrm(PLAYREADY_UUID)` probe |
| ClearKey support | `MediaDrm(CLEARKEY_UUID)` probe |
| For each codec (encode + decode): | `MediaCodecList(ALL_CODECS)` |
| → H.264 / AVC hardware | `.isHardwareAccelerated()` |
| → H.265 / HEVC hardware | Same |
| → AV1 hardware | Same |
| → VP8 hardware | Same |
| → VP9 hardware | Same |
| → AVC Profile levels | `CodecProfileLevel` |
| → HEVC Profile levels | `CodecProfileLevel` |
| → AV1 Profile levels | `CodecProfileLevel` |
| → Dolby Vision decode | `video/dolby-vision` |
| → HDR10 decode | Capability check |
| → HDR10+ decode | Capability check |
| → 8K decode capable | Max resolution check |
| → Max decode resolution per codec | `CodecCapabilities.getVideoCapabilities()` |
| → Max decode FPS per codec | `VideoCapabilities.getSupportedFrameRates()` |
| → Hardware encode: H.264 | `isHardwareAccelerated()` on encoder |
| → Hardware encode: H.265 | Same |
| → Hardware encode: AV1 | Same |
| → Audio: AAC / MP3 / FLAC / OPUS hardware | Same pattern |

---

### Module 24 — OEM Skin & Customization (Kotlin Heuristic Engine)

| Data Point | Method |
|---|---|
| OEM skin name | `ro.product.manufacturer` + `ro.build.flavor` + unique prop patterns |
| Skin version | `ro.miui.ui.version.name` / `ro.build.version.oneui` / `ro.csc.sales_code` etc. |
| Detected skins: MIUI, HyperOS, One UI, OxygenOS, ColorOS, EMUI, Funtouch OS, ZenUI, MagicOS, Moto My UX, Nothing OS, realme UI | Prop fingerprint matching |
| Background app kill aggressiveness | OEM prop heuristic — MIUI (Phantom process killer), One UI, ColorOS ratings |
| Background process limit | `Settings.Global.BACKGROUND_APP_LIMIT` |
| Memory compression strategy | OEM prop + zRAM algo |
| Phantom process killer active | `Settings.Global.MAX_PHANTOM_PROCESSES` (API 31+) |
| MIUI battery optimization (ultra-saver) | MIUI-specific prop |
| Samsung Game Booster present | Samsung-specific feature flag |
| DeX mode support | Samsung `sys.dex.*` prop |
| Desktop mode capable | `FEATURE_PC_LIKE_DESKTOP_MODE` |
| Bloatware count | Non-system third-party pre-installed apps |
| Pre-installed system apps | Count of apps with `FLAG_SYSTEM` |
| Targeting old API apps | Count of installed apps targeting API < 28 |

---

### Module 25 — Installed App Analyzer (Kotlin)

| Data Point | Source |
|---|---|
| Total installed apps | `PackageManager.getInstalledPackages()` |
| User apps vs system apps | `ApplicationInfo.FLAG_SYSTEM` |
| Pre-installed non-system apps (bloatware) | Third-party + FLAG_SYSTEM |
| For each app (top 50 by RAM usage): | |
| → Package name + version | `PackageInfo` |
| → Target SDK / Min SDK | `ApplicationInfo.targetSdkVersion` |
| → Requested permissions | `PackageInfo.requestedPermissions` |
| → Dangerous permissions granted | Cross-ref with `PROTECTION_DANGEROUS` |
| → Background service running | `ActivityManager.getRunningServices()` |
| → Last used (bucket) | `UsageStatsManager.getAppStandbyBucket()` |
| → Data usage (if PACKAGE_USAGE_STATS granted) | `NetworkStatsManager` |
| → Install source | `PackageManager.getInstallSourceInfo()` |
| → Multi-process | `ApplicationInfo.FLAG_MULTIPROCESS` |
| → Debuggable flag | `FLAG_DEBUGGABLE` — security flag |
| → Allow backup flag | `FLAG_ALLOW_BACKUP` |
| Apps targeting API < 26 | Potential security issues |
| Apps with `BIND_ACCESSIBILITY_SERVICE` | Spyware vector alert |
| Apps with excessive permissions | > 15 dangerous permissions flagged |

---

### Module 26 — Process Monitor (C++ Live)

| Data Point | Source |
|---|---|
| All running processes | `/proc/` directory enumeration |
| Per process: PID, PPID, name | `/proc/[pid]/status` |
| Per process: UID (maps to app) | `/proc/[pid]/status` |
| Per process: CPU % | `/proc/[pid]/stat` delta over 1s |
| Per process: RAM (VmRSS) | `/proc/[pid]/status` VmRSS |
| Per process: Swap (VmSwap) | `/proc/[pid]/status` VmSwap |
| Per process: Thread count | `/proc/[pid]/status` Threads |
| Per process: FD count | `ls /proc/[pid]/fd | wc -l` |
| Per process: Nice value | `/proc/[pid]/stat` nice |
| Per process: OOM score | `/proc/[pid]/oom_score` |
| Per process: cgroup | `/proc/[pid]/cgroup` |
| Per process: State (R/S/D/Z/T) | `/proc/[pid]/status` State |
| Zombie process count | State = Z count |
| Total CPU usage | `/proc/stat` aggregate |
| CPU usage history (30s ring buffer) | Sampled every 1s via foreground service |
| Sortable by: CPU / RAM / PID / Name | UI feature |

---

### Module 27 — System Settings Explorer (Kotlin)

Full searchable dump of all readable settings:

| Namespace | Examples |
|---|---|
| `Settings.System` | `SCREEN_BRIGHTNESS`, `FONT_SCALE`, `HAPTIC_FEEDBACK_ENABLED`, `SOUND_EFFECTS_ENABLED`, `DTMF_TONE_WHEN_DIALING`, `ACCELEROMETER_ROTATION`, `LOCKSCREEN_SOUNDS_ENABLED` |
| `Settings.Secure` | `BACKUP_ENABLED`, `SCREENSAVER_ENABLED`, `DOZE_ENABLED`, `LOCATION_MODE`, `INSTALL_NON_MARKET_APPS`, `ACCESSIBILITY_ENABLED`, `TTS_DEFAULT_SYNTH`, `PRIVATE_DNS_MODE` |
| `Settings.Global` | `WIFI_ON`, `BLUETOOTH_ON`, `DEVELOPMENT_SETTINGS_ENABLED`, `ADB_ENABLED`, `ANIMATOR_DURATION_SCALE`, `TRANSITION_ANIMATION_SCALE`, `WINDOW_ANIMATION_SCALE`, `BATTERY_SAVER_MODE`, `MAX_PHANTOM_PROCESSES`, `BACKGROUND_APP_LIMIT`, `STAY_ON_WHILE_PLUGGED_IN`, `USB_MASS_STORAGE_ENABLED` |

Features: Search bar · Filter by namespace · Flag changed-from-default values · Export as JSON

---

### Module 28 — ADB & Developer Flags (Kotlin)

| Data Point | Source |
|---|---|
| USB debugging | `Settings.Global.ADB_ENABLED` |
| Wireless ADB | `Settings.Global.ADB_WIFI_ENABLED` |
| ADB port | `Settings.Global.ADB_WIFI_PORT` |
| Window animation scale | `WINDOW_ANIMATION_SCALE` |
| Transition animation scale | `TRANSITION_ANIMATION_SCALE` |
| Animator duration scale | `ANIMATOR_DURATION_SCALE` |
| GPU rendering mode | `Settings.Global.GPU_DEBUG_LAYERS` |
| Strict mode enabled | `Settings.Global.DEVELOPMENT_STRICT_MODE_VIOLATIONS` |
| Pointer location overlay | `Settings.System.POINTER_LOCATION` |
| Show taps | `Settings.System.SHOW_TOUCHES` |
| Surface updates overlay | `SHOW_GPU_VIEW_UPDATES` |
| Background process limit | `APP_PROCESS_LIMIT` |
| Logger buffer sizes | `LOGCAT_BUFFER_SIZES` |
| Bluetooth HCI snoop log | `BT_HCI_SNOOP_LOG` |
| Force RTL layout | `DEVELOPMENT_FORCE_RTL` |
| Demo mode | `DEMO_USER_SETUP_COMPLETE` |
| Inactive app auto-kill | `INACTIVE_PACKAGE_AUTO_KILL_DELAY` |
| Mock location app | `Settings.Secure.ALLOW_MOCK_LOCATION` |
| Wi-Fi verbose logging | `WIFI_VERBOSE_LOGGING_ENABLED` |
| Mobile data always active | `MOBILE_DATA_ALWAYS_ON` |
| Bluetooth OPP enabled | `BLUETOOTH_OPP_COMPONENT` |
| Stay awake while charging | `STAY_ON_WHILE_PLUGGED_IN` |
| Auto system update | `AUTO_TIME` |
| Bug report shortcut | `BUGREPORT_IN_POWER_MENU` |

---

## 5. Real-Time Monitor (Flight Recorder)

### 5.1 Architecture
```
ForegroundService: PhoneScopeMonitorService
    ├── Persistent notification (dismissible in Pro)
    ├── Coroutine scope (SupervisorJob)
    ├── 1-second ticker:
    │   ├── CpuSampler.sample()         → CPU % per core + freq
    │   ├── MemorySampler.sample()       → RAM free
    │   ├── ThermalSampler.sample()      → All zone temps
    │   ├── BatterySampler.sample()      → Current (mA) + voltage
    │   └── GpuSampler.sample()          → GPU busy %
    ├── Ring buffer: last 3600 samples (1 hour)
    ├── Room write: every 60 samples (1 min aggregate)
    └── ThrottleDetector: emit alert if CPU < 70% max for 10s under load
```

### 5.2 Alert System
| Alert | Condition | Action |
|---|---|---|
| Thermal throttling | CPU freq < 70% of max for 10s with load > 80% | Push notification |
| CPU overheating | Any thermal zone > 85°C | Push notification |
| RAM critically low | Available < 200 MB | Push notification |
| Battery discharging fast | Current > -3000 mA | Push notification |
| Battery critically low | Level < 5% | Push notification |

### 5.3 Monitor Screens
- **CPU Graph**: Per-core frequency line chart, color-coded by cluster (big/mid/little)
- **Temperature Map**: Live-updating thermal heatmap grid
- **RAM Pressure**: Stacked area chart (used/cached/free)
- **Battery Drain**: Current (mA) over time — negative = discharging
- **GPU Load**: GPU busy % bar chart

---

## 6. Scheduled Scans & History

```
WorkManager Periodic Task: ScanWorker
├── Runs: daily (configurable: hourly / daily / weekly)
├── Constraints: charging, idle preferred (not required)
├── Action: RunFullScanUseCase.execute()
├── Stores: ScanEntity in Room (timestamp + full DeviceProfile JSON)
└── Triggers: battery health trend recalculation

History Screen:
├── Timeline list of past scans
├── Tap scan → full historical detail view
├── Compare: pick any 2 scans → side-by-side diff
└── Battery health chart: health % plotted over all scans
```

---

## 7. Report Generation System

### 7.1 Four Report Formats

| Format | Library | Use Case |
|---|---|---|
| **PDF** | iText7 Community | Professional sharing, email attachment |
| **HTML** | Kotlin HTML DSL | Browser-viewable, self-contained |
| **JSON** | kotlinx.serialization | Developer/technical export, backup |
| **Spec Card (PNG)** | Compose `drawToBitmap()` | Social media sharing, viral |

### 7.2 Spec Card Design
A beautiful 1080×1920px image (portrait) containing:
```
┌─────────────────────────────┐
│  PhoneScope                  │
│  [Device Name]               │
│  [Date]                      │
├─────────────────────────────┤
│  SoC: [Name] · [Process]    │
│  RAM: [X GB LPDDR5]         │
│  GPU: [Name]                 │
│  Display: [Res] [Hz] [Type] │
│  Battery: [mAh] [Health%]   │
│  Score: [XX/100] [TIER]     │
├─────────────────────────────┤
│  ████ CPU  ██ RAM  ██ GPU  │  ← Score bars
│  ██ Storage  ██ Battery    │
├─────────────────────────────┤
│  Security: [Score] [Badge]  │
│  Widevine: [L1/L2/L3]       │
│  Verified Boot: [State]     │
└─────────────────────────────┘
```

### 7.3 PDF Report Sections (30+ pages)
1. Cover page — device photo placeholder + device name + date
2. Executive Summary — scores, tier, highlights
3. Hardware Overview — all core specs in formatted table
4. CPU Detail — topology diagram + freq chart + governor table
5. Memory — meminfo table + fragmentation + zRAM
6. GPU — capabilities table + Vulkan details + extension count
7. Thermal Profile — zone table + trip points
8. Battery — all metrics + health % + charge info
9. Display — all display specs + HDR table
10. Camera Matrix — per-camera spec table
11. Audio — codec support + latency + features
12. Sensors — full sensor list table
13. Connectivity — Wi-Fi + Cellular + BT + NFC + UWB
14. Security Audit — scored checklist with pass/fail + CVEs
15. DRM & Codec Matrix — streaming quality + codec table
16. SoC Verification — match result + confidence %
17. AI / NPU — accelerator details + benchmark result
18. Storage — benchmark results + partition table
19. System Properties — full `ro.*` dump (formatted, searchable in HTML)
20. ADB & Developer Flags — full settings dump
21. OEM Skin Analysis — skin name + aggressiveness rating
22. Installed App Analysis — top apps + risk flags
23. Process Monitor Snapshot — top 20 processes by CPU/RAM
24. Recommendations — actionable insights based on findings
25. Appendix — methodology + data sources

---

## 8. Scoring Engine v2.0

### 8.1 Dimensions (7 total)

| Dimension | Weight | Key Inputs |
|---|---|---|
| Processing Power | 25% | Core count, max freq, architecture gen, AI benchmark |
| Graphics | 15% | Vulkan tier, GPU gen, codec hardware support |
| Memory | 15% | RAM total, type (LPDDR tier), bandwidth, zRAM |
| Storage | 10% | Type (UFS tier), sequential R/W speed, IOPS |
| Battery | 10% | Health %, design capacity, fast charge wattage |
| Connectivity | 10% | Wi-Fi gen, 5G type, BT version, UWB |
| Security | 15% | Security audit score, biometric class, TEE, patch age |

### 8.2 Tiers
| Tier | Score | Label |
|---|---|---|
| 🏆 Titan | 90–100 | Best-in-class flagship |
| 🏅 Flagship | 80–89 | Top-tier performance |
| ⚡ High-End | 68–79 | Above average |
| 💼 Mid-Range | 50–67 | Everyday capable |
| 📱 Budget | 30–49 | Functional basics |
| ⚠️ Legacy | 0–29 | Outdated hardware |

### 8.3 Context-Aware Insights
After scoring, generate natural language insights:
- *"Your CPU is in the top 15% of ARM processors in our database"*
- *"Your battery has degraded to 87% — typically replacement is recommended below 80%"*
- *"Widevine L3 means Netflix and Amazon Prime will only stream in SD on this device"*
- *"Your kernel is 14 months behind on security patches — 3 critical CVEs affect this version"*
- *"Your GPU supports Vulkan 1.3 — all major game engines are fully compatible"*

---

## 9. Complete UI Architecture

### 9.1 Navigation Graph
```
NavHost
├── OnboardingGraph (first launch only)
│   ├── WelcomeScreen
│   ├── PermissionScreen
│   └── PrivacyScreen
│
├── MainGraph
│   ├── DashboardScreen          ← Home: animated live overview
│   ├── ScanScreen               ← Full scan with animated ring progress
│   ├── CategoryListScreen       ← 28 modules in grouped list
│   │   └── CategoryDetailScreen ← Deep-dive any module
│   ├── MonitorScreen            ← Real-time graphs
│   │   ├── CpuGraphScreen
│   │   ├── TemperatureScreen
│   │   ├── RamGraphScreen
│   │   ├── BatteryMonitorScreen
│   │   └── ProcessListScreen
│   ├── HistoryScreen            ← Past scans timeline
│   │   ├── ScanDetailScreen
│   │   └── CompareScreen
│   ├── ReportScreen             ← Generate + preview + share
│   ├── SecurityScreen           ← Security audit detail
│   ├── SoCVerifyScreen          ← SoC verification result
│   ├── AppAnalyzerScreen        ← Installed app analysis
│   ├── PropertiesScreen         ← System properties explorer
│   └── SettingsScreen
│       ├── ScanSettings
│       ├── MonitorSettings
│       ├── NotificationSettings
│       └── ProScreen            ← IAP paywall
```

### 9.2 Design Language
- **Base**: OLED-black `#000000`
- **Surface**: `#0A0A0F` (near-black with blue tint)
- **Panel glass**: `#FFFFFF08` with `BlurMaskFilter` layer
- **Primary accent**: Electric cyan `#00D4FF`
- **Secondary accent**: Amber `#FFB300`
- **Success**: `#00FF87`
- **Warning**: `#FFB300`
- **Error**: `#FF4444`
- **Critical**: `#FF0055`
- **Typography**: JetBrains Mono for all data values; Outfit (display), DM Sans (body)
- **Icon set**: Custom thin-line icons + Phosphor Icons
- **Corner radius**: 16dp (cards), 12dp (inner), 24dp (bottom sheets)
- **Elevation**: Zero flat shadows — layered glow effects instead

### 9.3 Signature Animations
| Animation | Implementation |
|---|---|
| Scan ring | `Canvas` drawing with `animateFloatAsState`, segment-by-segment fill |
| Counter roll-up | Custom `AnimatedContent` digit roller |
| Live graph | Compose `Canvas` with `drawPath`, sliding window |
| Card entrance | `AnimatedVisibility` + `slideInVertically` staggered by index |
| Score reveal | Circular sweep animation on gauge |
| Thermal heatmap | Color interpolation `lerp(cyan, amber, red)` by temperature |
| Tab transitions | `SharedElement` transitions (Compose 1.7+) |

---

## 10. Technology Stack (Complete v2.0)

| Layer | Technology | Version |
|---|---|---|
| **UI** | Jetpack Compose | 1.7+ |
| **Material** | Material 3 | Latest |
| **Navigation** | Compose Navigation | 2.8+ |
| **DI** | Hilt | 2.51+ |
| **Async** | Kotlin Coroutines + Flow | 1.8+ |
| **Native Engine** | C++20 via NDK r27 | r27 |
| **JNI** | JNI + `extern "C"` | NDK |
| **C++ JSON** | nlohmann/json | 3.11 (header-only, MIT) |
| **Database** | Room | 2.6+ |
| **Preferences** | DataStore Proto | 1.1+ |
| **Charts** | Compose Charts (Vico) | 2.x |
| **PDF** | iText7 Community | 8.x |
| **HTML Gen** | Kotlin HTML DSL (kotlinx.html) | 0.11 |
| **JSON** | kotlinx.serialization | 1.7+ |
| **Image** | Coil 3 | 3.x |
| **Permissions** | Accompanist Permissions | Latest |
| **Lifecycle** | ViewModel + SavedStateHandle | 2.8+ |
| **WorkManager** | WorkManager KTX | 2.9+ |
| **Widget** | Glance (Compose) | 1.1+ |
| **Play Integrity** | Play Integrity API | Latest |
| **IAP** | Play Billing Library | 7.x |
| **Testing** | JUnit 5, Mockk, Turbine, Robolectric | Latest |
| **UI Testing** | Compose Test + Espresso | Latest |
| **NDK Tests** | GoogleTest (gtest) via CMake | 1.14 |
| **CI/CD** | GitHub Actions + Fastlane | Latest |
| **Code Quality** | Detekt, ktlint, clang-tidy, cppcheck | Latest |
| **Memory Safety** | ASan + UBSan (NDK debug builds) | NDK |
| **Analytics** | Firebase Crashlytics only (no event tracking) | Latest |

---

## 11. Project Structure (Complete v2.0)

```
PhoneScope/
├── app/
│   └── src/main/
│       ├── kotlin/com/phonescope/inspector/
│       │   ├── PhoneScopeApp.kt
│       │   ├── MainActivity.kt
│       │   │
│       │   ├── ui/
│       │   │   ├── theme/           Color · Type · Theme · Shape · Motion
│       │   │   ├── components/      DataCard · LiveGauge · ThermalMap · ScanRing
│       │   │   │                    CapabilityBadge · PropertyRow · ScoreGauge
│       │   │   │                    GraphCanvas · ProcessRow · SecurityCheckRow
│       │   │   ├── onboarding/
│       │   │   ├── dashboard/
│       │   │   ├── scan/
│       │   │   ├── category/
│       │   │   ├── monitor/
│       │   │   ├── history/
│       │   │   ├── report/
│       │   │   ├── security/
│       │   │   ├── socverify/
│       │   │   ├── apps/
│       │   │   ├── properties/
│       │   │   └── settings/
│       │   │
│       │   ├── viewmodel/
│       │   │   ├── ScanViewModel
│       │   │   ├── MonitorViewModel
│       │   │   ├── HistoryViewModel
│       │   │   ├── ReportViewModel
│       │   │   ├── SecurityViewModel
│       │   │   ├── AppAnalyzerViewModel
│       │   │   └── SettingsViewModel
│       │   │
│       │   ├── domain/
│       │   │   ├── model/
│       │   │   │   ├── CpuInfo · MemoryInfo · GpuInfo · ThermalInfo
│       │   │   │   ├── BatteryInfo · DisplayInfo · CameraInfo · AudioInfo
│       │   │   │   ├── SensorInfo · BiometricInfo · InputInfo · UsbInfo
│       │   │   │   ├── WifiInfo · CellularInfo · BluetoothInfo · NfcInfo
│       │   │   │   ├── SecurityInfo · DrmInfo · OemInfo · AppInfo
│       │   │   │   ├── ProcessInfo · SocVerification · AiInfo
│       │   │   │   ├── NetworkDiagnostics · KernelInfo · StorageInfo
│       │   │   │   ├── MonitorSample · ScanHistory
│       │   │   │   └── DeviceProfile  ← master aggregate
│       │   │   └── usecase/
│       │   │       ├── RunFullScanUseCase
│       │   │       ├── RunLiveScanUseCase
│       │   │       ├── GenerateReportUseCase
│       │   │       ├── ScoreDeviceUseCase
│       │   │       ├── VerifySocUseCase
│       │   │       ├── RunAiBenchmarkUseCase
│       │   │       ├── RunStorageBenchmarkUseCase
│       │   │       ├── RunNetworkDiagnosticsUseCase
│       │   │       └── CompareScanUseCase
│       │   │
│       │   ├── data/
│       │   │   ├── repository/      (one per module, 28 total)
│       │   │   ├── jni/             JNI wrapper objects (Kotlin @JvmStatic)
│       │   │   ├── local/
│       │   │   │   ├── AppDatabase · ScanDao · MonitorDao · SettingsDao
│       │   │   │   └── entity/      ScanEntity · MonitorSampleEntity
│       │   │   └── assets/
│       │   │       ├── soc_database.json       ← 500+ SoC fingerprints
│       │   │       ├── cve_database.json        ← Kernel CVE list
│       │   │       └── device_database.json     ← 500+ device specs for comparison
│       │   │
│       │   ├── service/
│       │   │   ├── PhoneScopeMonitorService    ← ForegroundService
│       │   │   └── ScanWorker                  ← WorkManager
│       │   │
│       │   ├── report/
│       │   │   ├── ReportBuilder
│       │   │   ├── PdfReportGenerator           ← iText7
│       │   │   ├── HtmlReportGenerator          ← kotlinx.html
│       │   │   ├── JsonReportGenerator
│       │   │   └── SpecCardGenerator            ← Compose Canvas → Bitmap
│       │   │
│       │   ├── widget/
│       │   │   ├── PhoneScopeWidget2x2          ← Glance
│       │   │   └── PhoneScopeWidget4x2          ← Glance
│       │   │
│       │   └── di/
│       │       ├── AppModule · RepositoryModule · DatabaseModule
│       │       ├── JniModule · UseCaseModule · ReportModule
│       │
│       └── cpp/
│           ├── CMakeLists.txt
│           ├── engine/
│           │   ├── cpu_inspector.h/.cpp
│           │   ├── memory_inspector.h/.cpp
│           │   ├── gpu_inspector.h/.cpp       ← EGL context, GL queries, Vulkan
│           │   ├── thermal_inspector.h/.cpp
│           │   ├── kernel_inspector.h/.cpp
│           │   ├── storage_benchmark.h/.cpp
│           │   ├── ai_inspector.h/.cpp        ← NNAPI NDK
│           │   └── process_inspector.h/.cpp   ← /proc/[pid] reader
│           ├── jni/
│           │   └── jni_bridge.cpp
│           ├── util/
│           │   ├── proc_reader.h/.cpp
│           │   ├── sys_reader.h/.cpp
│           │   ├── prop_reader.h/.cpp
│           │   └── file_util.h/.cpp
│           └── third_party/
│               ├── nlohmann/json.hpp
│               └── googletest/               ← NDK unit tests
│
├── core/
│   ├── data/                    Shared data interfaces
│   ├── domain/                  Shared entities
│   └── common/                  Extensions, Result type, etc.
│
├── buildSrc/
│   └── libs.versions.toml       Version catalog
│
├── .github/
│   └── workflows/
│       ├── ci.yml               Build + test on every PR
│       ├── release.yml          Sign + deploy to Play Store
│       └── nightly.yml          Nightly DB update (CVE + SoC)
│
└── fastlane/
    ├── Fastfile
    └── metadata/                Play Store listing, screenshots
```

---

## 12. JNI Bridge Design (Complete)

**Strategy**: All C++ → Kotlin data transfer via JSON strings. Single `jstring` return = minimal JNI complexity. Kotlin deserializes via `kotlinx.serialization`.

```cpp
// jni_bridge.cpp — complete export list
extern "C" {
  // Tier A — Native engine
  JNIEXPORT jstring JNICALL Java_CpuInspectorJNI_nativeGetCpuInfo(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_MemoryInspectorJNI_nativeGetMemoryInfo(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_GpuInspectorJNI_nativeGetGpuInfo(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_ThermalInspectorJNI_nativeGetThermalZones(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_KernelInspectorJNI_nativeGetKernelInfo(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_StorageBenchmarkJNI_nativeRunBenchmark(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_AiInspectorJNI_nativeGetNnapiInfo(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_ProcessInspectorJNI_nativeGetProcessList(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_PropReaderJNI_nativeGetAllSystemProps(JNIEnv*, jobject);

  // Live monitor (called every 1s)
  JNIEXPORT jstring JNICALL Java_MonitorJNI_nativeSampleCpu(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_MonitorJNI_nativeSampleMemory(JNIEnv*, jobject);
  JNIEXPORT jstring JNICALL Java_MonitorJNI_nativeSampleThermal(JNIEnv*, jobject);
}
```

---

## 13. CMakeLists.txt (Complete)

```cmake
cmake_minimum_required(VERSION 3.22)
project(phonescope_engine CXX)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Compiler flags
add_compile_options(
    -Wall -Wextra -Wpedantic
    -fstack-protector-strong
    -D_FORTIFY_SOURCE=2
    -O2
)

add_library(phonescope_engine SHARED
    jni/jni_bridge.cpp
    engine/cpu_inspector.cpp
    engine/memory_inspector.cpp
    engine/gpu_inspector.cpp
    engine/thermal_inspector.cpp
    engine/kernel_inspector.cpp
    engine/storage_benchmark.cpp
    engine/ai_inspector.cpp
    engine/process_inspector.cpp
    util/proc_reader.cpp
    util/sys_reader.cpp
    util/prop_reader.cpp
    util/file_util.cpp
)

target_include_directories(phonescope_engine PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/third_party
    ${CMAKE_CURRENT_SOURCE_DIR}/engine
    ${CMAKE_CURRENT_SOURCE_DIR}/util
)

find_library(log-lib log)
find_library(android-lib android)
find_library(GLESv2-lib GLESv2)
find_library(GLESv3-lib GLESv3)
find_library(EGL-lib EGL)
find_library(vulkan-lib vulkan)
find_library(neural-networks-lib neuralnetworks)

target_link_libraries(phonescope_engine
    ${log-lib}
    ${android-lib}
    ${GLESv2-lib}
    ${GLESv3-lib}
    ${EGL-lib}
    ${vulkan-lib}
    ${neural-networks-lib}
)
```

---

## 14. Permissions Matrix (Complete)

### Auto-granted (Normal)
```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SYSTEM_EXEMPTED"/>
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" android:required="false"/>
<uses-permission android:name="android.permission.USE_BIOMETRIC"/>
<uses-permission android:name="android.permission.NFC"/>
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" android:minSdkVersion="31"/>
<uses-permission android:name="android.permission.BLUETOOTH" android:maxSdkVersion="30"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" android:maxSdkVersion="30"/>
<uses-permission android:name="android.permission.UWB_RANGING"/>
<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" tools:ignore="ProtectedPermissions"/>
```

### Runtime (User-granted with explanation)
```xml
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.READ_CALL_LOG" android:required="false"/>
```

### Graceful Degradation Table
| Permission | Lost Feature | Fallback |
|---|---|---|
| READ_PHONE_STATE | IMEI, ICCID, some cellular | Show placeholder |
| ACCESS_FINE_LOCATION | Wi-Fi SSID, BSSID, channel | Show "Location needed" |
| CAMERA | Camera2 detailed specs | Show basic PackageManager camera info |
| RECORD_AUDIO | Audio latency benchmark | Show theoretical from props |
| PACKAGE_USAGE_STATS | App standby bucket | Show without bucket info |

---

## 15. Bundled Asset Databases

### 15.1 SoC Database (`soc_database.json`) — ~500 entries
```json
{
  "snapdragon_8_gen3": {
    "aliases": ["SM8650", "sm8650"],
    "ro_board_platform_patterns": ["kailua"],
    "gpu_renderer_pattern": "Adreno.*750",
    "cpu_features_required": ["aes", "pmull", "sha2", "crc32", "dotprod", "sve2"],
    "cluster_topology": [[1, 3400], [3, 3150], [2, 2960], [2, 2270]],
    "memory_bandwidth_range_gbps": [70, 90],
    "process_node": "4nm TSMC",
    "tdp_watts": 10,
    "gpu_model": "Adreno 750",
    "ram_type": "LPDDR5X",
    "storage_standard": "UFS 4.0"
  }
}
```

### 15.2 CVE Database (`cve_database.json`) — ~300 entries
```json
{
  "CVE-2024-0039": {
    "affects_kernel_range": ["5.10.0", "5.15.100"],
    "severity": "CRITICAL",
    "cvss": 9.8,
    "summary": "Remote code execution via NFC subsystem",
    "fixed_in_patch": "2024-05"
  }
}
```

### 15.3 Device Database (`device_database.json`) — ~500 devices
```json
{
  "samsung_s24_ultra": {
    "display_name": "Samsung Galaxy S24 Ultra",
    "year": 2024,
    "soc": "snapdragon_8_gen3",
    "ram_gb": 12,
    "storage_options_gb": [256, 512, 1024],
    "battery_mah": 5000,
    "score_cpu": 96,
    "score_gpu": 94,
    "score_overall": 95
  }
}
```

---

## 16. Implementation Sprints (v2.0 — 16 Sprints)

| Sprint | Focus | Deliverables |
|---|---|---|
| **S1** | Foundation | Project scaffold, Hilt DI, Room DB, DataStore, theme system, navigation skeleton, CMake setup, CI pipeline |
| **S2** | Native Utils | `proc_reader`, `sys_reader`, `prop_reader`, `file_util` C++ utilities; JNI bridge skeleton; unit tests with gtest |
| **S3** | CPU + Memory Native | CPU inspector (all data points), Memory inspector (all data points), JNI wrappers, Kotlin repositories |
| **S4** | GPU + Thermal Native | EGL context setup, OpenGL ES queries, Vulkan probing, thermal zone enumeration |
| **S5** | Kernel + Storage + AI | Kernel info, `uname()`, system props foreach, storage benchmark (seq + random), NNAPI probe |
| **S6** | Process Monitor Native | `/proc/[pid]` reader, CPU delta calculation, process list JSON serialization |
| **S7** | Kotlin Inspectors Part 1 | Battery, Display, Camera, Audio repositories |
| **S8** | Kotlin Inspectors Part 2 | Sensors, Biometrics, Input, USB, Wi-Fi Deep, Cellular, BT, NFC/UWB |
| **S9** | Analysis Modules | Security Audit engine, DRM/Codec matrix, OEM Skin detector, App Analyzer, Network Diagnostics |
| **S10** | SoC Verify + DB | SoC database JSON, verification algorithm, CVE cross-reference, device comparison DB |
| **S11** | Scan Orchestration | `ScanOrchestrator`, parallel coroutine scan, `DeviceProfile` aggregation, Room persistence, Scoring engine |
| **S12** | Dashboard + Category UI | `DashboardScreen`, all live gauge components, `CategoryListScreen`, `CategoryDetailScreen` for all 28 modules |
| **S13** | Monitor + History UI | `ForegroundService`, all graph screens, `HistoryScreen`, `CompareScreen`, battery health chart |
| **S14** | Report System | PDF generator (all 25 sections), HTML generator, JSON export, Spec Card canvas generator, share sheet |
| **S15** | Widgets + Settings | Glance 2×2 and 4×2 widgets, WorkManager scheduled scans, `SettingsScreen`, onboarding, Pro IAP |
| **S16** | Polish + QA | Animations, haptics, accessibility (TalkBack), i18n (EN/AR/UR/ZH), ASan NDK pass, 20-device test matrix, Play Store submission |

---

## 17. Competitive Edge Summary

### Features No Competitor Has (All of Them)
| Feature | CPU-Z | AIDA64 | DevCheck | AnTuTu | PhoneScope |
|---|---|---|---|---|---|
| SoC identity verification | ❌ | ❌ | ❌ | ❌ | ✅ |
| Battery health timeline (months) | ❌ | ❌ | ❌ | ❌ | ✅ |
| Live flight recorder (1s sampling) | ❌ | Partial | ❌ | ❌ | ✅ |
| Thermal throttle push notifications | ❌ | ❌ | ❌ | ❌ | ✅ |
| Security audit + CVE cross-ref | ❌ | ❌ | ❌ | ❌ | ✅ |
| Biometric class + TEE detection | ❌ | ❌ | ❌ | ❌ | ✅ |
| USB-PD live wattage readout | ❌ | ❌ | ❌ | ❌ | ✅ |
| BT codec matrix (LDAC, LC3, aptX) | ❌ | ❌ | Partial | ❌ | ✅ |
| NNAPI + AI tier benchmark | ❌ | ❌ | ❌ | ❌ | ✅ |
| OEM skin aggressiveness rating | ❌ | ❌ | ❌ | ❌ | ✅ |
| Shareable spec card (social) | ❌ | ❌ | ❌ | Partial | ✅ |
| Full /proc process monitor | ❌ | Partial | ❌ | ❌ | ✅ |
| Vulkan full feature/limit dump | ❌ | Partial | ❌ | ❌ | ✅ |
| 5G mmWave vs Sub-6 detection | ❌ | ❌ | ❌ | ❌ | ✅ |
| Widevine + full codec matrix | Partial | ❌ | ❌ | ❌ | ✅ |
| Display dead pixel/burn-in test | ❌ | ❌ | ❌ | ❌ | ✅ |
| ro.* full property explorer | ❌ | ❌ | Partial | ❌ | ✅ |
| Scheduled auto-scans | ❌ | ❌ | ❌ | ❌ | ✅ |
| Actionable natural language insights | ❌ | ❌ | ❌ | ❌ | ✅ |
| Device DB percentile comparison | ❌ | ❌ | ❌ | Partial | ✅ |
| Glance widgets (live CPU/RAM/Temp) | ❌ | ❌ | ❌ | ❌ | ✅ |
| PDF report (25+ sections) | ❌ | ❌ | ❌ | ❌ | ✅ |
| Private DNS + DNS speed test | ❌ | ❌ | ❌ | ❌ | ✅ |
| UWB capabilities | ❌ | ❌ | ❌ | ❌ | ✅ |
| Kernel CVE check (offline) | ❌ | ❌ | ❌ | ❌ | ✅ |

---

## 18. Quality Gates

### Code
- [ ] All C++ files pass `clang-tidy` with zero warnings
- [ ] C++ passes `cppcheck` static analysis
- [ ] Kotlin passes `Detekt` + `ktlint` with custom ruleset
- [ ] Zero memory leaks: ASan + LeakSanitizer on debug NDK builds
- [ ] Zero UBSan violations in NDK builds
- [ ] >80% unit test coverage on domain + repository layers
- [ ] All JNI methods covered by gtest NDK tests

### Performance
- [ ] App cold start < 500ms
- [ ] Full scan < 2.5 seconds on mid-range device
- [ ] PDF generation < 3 seconds
- [ ] Live monitor CPU overhead < 2% of total CPU
- [ ] Memory overhead of app < 80 MB in background
- [ ] Spec card image generation < 1 second

### Quality
- [ ] Zero ANR / crash in 72-hour soak test on 20 devices
- [ ] Zero crash in Firebase Test Lab matrix (20 devices, API 26–35)
- [ ] TalkBack fully navigable on all screens
- [ ] Content descriptions on all data elements
- [ ] All text scales correctly from 80%–200% font scale
- [ ] RTL layout correct for Arabic + Urdu

### Security
- [ ] R8 / ProGuard: JNI method names excluded from obfuscation
- [ ] No sensitive data (IMEI, ICCID) logged in release builds
- [ ] `FileProvider` properly scoped for report sharing
- [ ] `android:exported="false"` on all non-main activities/services
- [ ] Network security config: no cleartext HTTP

---

## 19. Localization (i18n)

From Sprint 1: all user-visible strings in `strings.xml`. No hardcoded strings in code.

| Language | Code | Direction | Priority |
|---|---|---|---|
| English | `en` | LTR | Launch |
| Urdu | `ur` | RTL | Launch |
| Arabic | `ar` | RTL | Launch |
| Chinese Simplified | `zh-Hans` | LTR | v1.1 |
| Hindi | `hi` | LTR | v1.1 |
| Turkish | `tr` | LTR | v1.2 |

RTL layout: use `start`/`end` everywhere, never `left`/`right`. Test with `FORCE_RTL` developer flag.

---

## 20. Monetization (Pro Features)

| Feature | Free | Pro |
|---|---|---|
| All 28 inspection modules | ✅ | ✅ |
| One-time full scan | ✅ | ✅ |
| Basic report (JSON + text) | ✅ | ✅ |
| Spec card image share | ✅ | ✅ |
| PDF report (full 25-section) | ❌ | ✅ |
| HTML report | ❌ | ✅ |
| Live real-time monitor | ❌ | ✅ |
| Thermal throttle notifications | ❌ | ✅ |
| Scheduled auto-scans | ❌ | ✅ |
| Scan history + timeline | Limited (3) | Unlimited |
| Scan comparison | ❌ | ✅ |
| Battery health chart | ❌ | ✅ |
| Process monitor | ❌ | ✅ |
| App analyzer deep mode | Basic | Full |
| Glance widgets | ❌ | ✅ |
| CSV / JSON full export | ❌ | ✅ |

**Pro price**: One-time IAP — $3.99 USD. No subscription, no ads, ever.

---

*Plan Version 2.0 — Final. 28 modules. 16 sprints. Ready for Sprint S1.*
