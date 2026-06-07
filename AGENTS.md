# AGENTS.md

## Cursor Cloud specific instructions

### Product overview

**Astro TV Guide** is a single-module Android app (`:app`) for Astro Malaysia DTV subscribers. The launcher activity is `MainActivity` (channels list); `TvGuideActivity` shows paginated TV guide data. Live data comes from the Astro AMS API (`http://ams-api.astro.com.my/`); favorites/login use Firebase.

### One-time VM prerequisites (not in update script)

The Cloud Agent VM needs **JDK 17** and the **Android SDK** installed outside the repo:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=$HOME/Android/Sdk
export ANDROID_SDK_ROOT=$ANDROID_HOME
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH
```

Create `local.properties` at the repo root (gitignored):

```
sdk.dir=/home/ubuntu/Android/Sdk
```

Install SDK packages once:

```bash
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### Build, lint, and test

All commands from the repo root with `JAVA_HOME` and `ANDROID_HOME` set as above:

| Task | Command |
|------|---------|
| Full CI build (compile + unit tests + lint) | `./gradlew build` |
| Debug APK | `./gradlew assembleDebug` |
| Unit tests only | `./gradlew test` |
| Install on connected device/emulator | `./gradlew installDebug` |

CI workflow: `.github/workflows/android.yml` (JDK 17, `./gradlew build`).

### Running the app

1. Start an emulator or connect a physical device (`adb devices`).
2. `./gradlew installDebug`
3. Launch: `adb shell am start -n com.mukeshteckwani.astro.astroapp/.view.MainActivity`

**Emulator notes:** Software emulators without KVM are slow to boot (several minutes). In some Cloud VM environments the emulator may lack outbound network; verify with `adb shell ping -c 1 google.com`. The Astro API is reachable from the host with `curl -H "city_id: 10" http://ams-api.astro.com.my/ams/v3/getChannelList`.

**Firebase / Google Sign-In:** Requires a valid `app/google-services.json` and debug SHA-1 registered in Firebase Console (see README).

### Gotchas discovered during setup

- Master branch had several Android Gradle Plugin 8.x migration gaps (version catalog `bundle.*` syntax, missing `namespace`, AndroidX flags, invalid dependency versions, Support Library imports). These were fixed on the setup branch so `./gradlew build` succeeds.
- `R.id` values are not compile-time constants in AGP 8+; use `if/else` chains instead of `switch` on menu item IDs.
- The app uses **HTTP** (not HTTPS) for the Astro API; `android:usesCleartextTraffic="true"` is required on the `<application>` tag for API 28+.
