# Gboard AMOLED Dynamic Color

LSPosed module that replaces Gboard's dark grey background with pure black while preserving Material You accent colors.

![GitHub Release](https://img.shields.io/github/v/release/hxreborn/gboard-amoled-dynamic-color)

## Description

Gboard's dark theme uses dark grey surfaces instead of pure black. This module intercepts Material You color lookups and replaces surface container colors with #000000 while preserving dynamic accent colors derived from the wallpaper.

**Tested on:** Pixel 9 Pro, Android 15/16

## Requirements

- **Android:** 12+ (API 31+) – Material You dependency
- **Root:** Magisk/KernelSU with LSPosed
- **Target:** Gboard (`com.google.android.inputmethod.latin`)
- **LSPosed:** libxposed API v100

## Installation

1. Install [LSPosed](https://github.com/LSPosed/LSPosed/releases)
2. Download latest APK from [releases](https://github.com/hxreborn/gboard-amoled-dynamic-color/releases)
3. Install APK and enable module in LSPosed Manager
4. Add **Gboard** to module scope
5. Force stop Gboard or reboot

## Verification

1. Enable dark theme globally
2. Enable Material You dynamic colors
3. Open text field to show Gboard
4. Background should be pure black, keys grey, accents colorful

## Building

```bash
git clone --recurse-submodules https://github.com/hxreborn/gboard-amoled-dynamic-color.git
cd gboard-amoled-dynamic-color
./gradlew assembleDebug
```

**Build requirements:** JDK 21, Gradle 8.7+

## Troubleshooting

- Verify module is enabled and scoped to Gboard in LSPosed Manager
- Check dark mode is active (light theme is unaffected)
- View logs: `adb logcat -s GboardAmoled`
- OEM ROM overlays may require adjustments

## License

MIT
