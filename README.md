# Gboard Material Expressive Black

LSPosed module that restores pitch black backgrounds to Gboard on Android 16, bypassing Material 3 Expressive enforcement while preserving dynamic theme colors.

![GitHub Release](https://img.shields.io/github/v/release/hxreborn/gboard-material-expressive-black)

## Why This Module?

Starting with Android 16 and Material 3 Expressive, Gboard enforces tinted grey surface colors instead of pitch black. This design change breaks AMOLED power savings and prevents pitch black themes.


This module intercepts Material You color lookups at the system level using LSPosed hooks, replacing surface container colors with pitch black while preserving wallpaper-derived dynamic theme colors. Pitch black pixels remain electrically inactive on OLED panels, reducing power consumption without sacrificing dynamic theming.

**Tested configuration:**
- Device: Pixel 9 Pro (caiman)
- Android: 16.0.0 (BP3A.251105.015, Nov 2025)
- ROM: Stock Android November build
- Root: KernelSU Next via GKI2 12797 (no LKM)
- LSPosed: [JingMatrix fork](https://github.com/JingMatrix/LSPosed)
- ColorBlendr: 2.0.3 (unrelated but present; worked for black backgrounds pre-16, may replace this module when updated for Android 16)

Functionality on other devices, ROM versions, or LSPosed implementations is untested.

## Visual Comparison

### With Key Borders ON

**Stock Gboard (Grey Background)**

<img src="screenshots/stock_borders_on.png" alt="Stock with borders" width="100%"/>

**With This Module (Pitch Black)**

<img src="screenshots/module_borders_on.png" alt="Module with borders" width="100%"/>

### With Key Borders OFF

**Stock Gboard (Grey Background)**

<img src="screenshots/stock_borders_off.png" alt="Stock without borders" width="100%"/>

**With This Module (Pitch Black)**

<img src="screenshots/module_borders_off.png" alt="Module without borders" width="100%"/>

---

Notice the grey/tinted background in stock Gboard vs pitch black with this module. Dynamic theme colors (blue/purple) are preserved in both configurations.

## Requirements

- Android 12+ (Android 16+ recommended - fixes grey background issue)
- LSPosed framework

## Installation

1. Install [LSPosed](https://github.com/LSPosed/LSPosed/releases)
2. Download latest APK from [releases](https://github.com/hxreborn/gboard-material-expressive-black/releases)
3. Install APK and enable module in LSPosed Manager
4. Add **Gboard** to module scope
5. Force stop Gboard or reboot

## Building from source

```bash
git clone --recurse-submodules https://github.com/hxreborn/gboard-material-expressive-black.git
cd gboard-material-expressive-black
./gradlew assembleDebug
```

**Build requirements:** JDK 21, Gradle 8.7+


## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
