# Repository Guidelines

## Project Structure & Module Organization

This Android assignment uses Java and XML Views, not Kotlin or Jetpack Compose. The single application module is `app`.

- `app/src/main/java/com/example/assignment01/`: Java sources, including `MainActivity.java`.
- `app/src/main/res/layout/`: XML layouts, currently `activity_main.xml`.
- `app/src/main/res/`: drawable assets, launcher icons, strings, colors, and day/night themes.
- `app/src/main/AndroidManifest.xml`: application and activity declarations.
- `app/src/test/`: local unit tests; `app/src/androidTest/`: device tests.
- `gradle/libs.versions.toml`: dependency versions. Gradle uses Kotlin DSL; application code remains Java.

## Build, Test, and Development Commands

Run from the repository root in PowerShell:

- `.\gradlew.bat assembleDebug`: build the debug APK.
- `.\gradlew.bat testDebugUnitTest`: run local unit tests.
- `.\gradlew.bat connectedDebugAndroidTest`: run instrumentation tests on a connected emulator or device.
- `.\gradlew.bat lintDebug`: run Android Lint.
- `.\gradlew.bat installDebug`: install the debug app; launch it from the device or Android Studio.

Use Android Studio for Gradle Sync, emulator management, and Run/Debug. The project declares compile/target SDK 37 and minimum SDK 24.

## Coding Style & Naming Conventions

Use four-space indentation in Java and XML. Use `UpperCamelCase` for classes, `lowerCamelCase` for methods/variables, and `UPPER_SNAKE_CASE` for constants. Name resources and view IDs with `lower_snake_case`, such as `button_register`. Store UI text and colors in resources; use `dp` for dimensions and `sp` for text. No dedicated formatter is configured; follow Android Studio formatting and Android Lint.

## Testing Guidelines

Tests use JUnit 4, AndroidJUnit4, and Espresso. Name test classes `*Test.java` and methods descriptively. Existing tests are template checks; no coverage threshold is specified. Verify both UI versions on the same emulator, including scrolling, keyboard interaction, and system-bar insets.

## Commit & Pull Request Guidelines

No Git metadata is present, so existing commit conventions cannot be established. If version control is added, use concise imperative commit subjects. Pull requests should explain the change, list validation performed, link relevant issues, and include screenshots for UI changes.

## Assignment Scope & Configuration

Implement Design 2, User Registration, in XML and independently through Java-created views. Keep their appearance consistent; the report is not required. Preserve existing edge-to-edge handling. Avoid unnecessary dependencies or build configuration changes. Never commit `local.properties`, credentials, or generated build output.
