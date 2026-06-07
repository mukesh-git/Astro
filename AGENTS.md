# AGENTS.md

## Cursor Cloud specific instructions

### Product overview

**Astro TV Guide** is a **Compose-only** Android app (`:app`). `MainActivity` hosts Navigation 3 (`NavDisplay`) with `ChannelsKey` and `TvGuideKey` destinations. Data comes from the Astro AMS API and Firebase.

### One-time VM prerequisites

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=$HOME/Android/Sdk
export ANDROID_SDK_ROOT=$ANDROID_HOME
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH
```

`local.properties`:
```
sdk.dir=/home/ubuntu/Android/Sdk
```

Install SDK packages:
```bash
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-36" "build-tools;34.0.0"
```

### Build commands

| Task | Command |
|------|---------|
| Full build | `./gradlew build` |
| Debug APK | `./gradlew assembleDebug` |
| Unit tests | `./gradlew test` |

### Gotchas

- **compileSdk 36** and **AGP 8.9.1** are required for Navigation 3.
- **Kotlin 2.1** with the Compose compiler plugin (`org.jetbrains.kotlin.plugin.compose`).
- **minSdk 23** (Navigation 3 requirement).
- Astro API uses HTTP; `android:usesCleartextTraffic="true"` is set in the manifest.
