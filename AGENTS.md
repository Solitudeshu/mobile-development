# Repository Guidelines

This is a university Android assignment project using Java and XML Views. The single application module is `app`.

## Assignment Requirements & Workflow

- Follow the assignment specification as the primary source of requirements. The assigned UI is **Design 2: User Registration**. Do not silently change or invent requirements; ask the user when a requirement is ambiguous or information is missing.
- For the current submission, the report is **not required**. Focus only on the required Java and XML deliverables.
- Implement the XML version with XML Views in `app/src/main/res/layout/` first, and complete and verify it before implementing the Java programmatic version.
- Build the programmatic UI with Java-created views, matching the completed XML implementation visually as closely as possible. Preserve the completed XML implementation when adding the Java version.
- Keep the solution simple, clear, readable, and appropriate for a university assignment. Prefer standard Android widgets and layouts; do not over-engineer or add unnecessary libraries, dependencies, files, activities, or abstractions.

## Project Structure & Module Organization

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

Use Java for application code and tests; do not use Kotlin or Jetpack Compose. Retain the existing Kotlin DSL Gradle scripts as build configuration.

Use four-space indentation in Java and XML. Use `UpperCamelCase` for classes, `lowerCamelCase` for methods/variables, and `UPPER_SNAKE_CASE` for constants. Name resources and view IDs with `lower_snake_case`, such as `button_register`. Store UI text and colors in resources; use `dp` for dimensions and `sp` for text. No dedicated formatter is configured; follow Android Studio formatting and Android Lint.

## Testing Guidelines

Tests use JUnit 4, AndroidJUnit4, and Espresso. Name test classes `*Test.java` and methods descriptively. Existing tests are template checks; no coverage threshold is specified. After implementation changes, check for relevant build/compile errors with `assembleDebug` and run tests and lint appropriate to the change. Verify the XML version first, then both UI versions on the same emulator, including scrolling, keyboard interaction, visual consistency, and system-bar insets.

## Commit & Pull Request Guidelines

This project is a Git repository connected to GitHub. Use concise imperative commit subjects. Pull requests should explain the change, list validation performed, link relevant issues, and include screenshots for UI changes. Never commit `local.properties`, credentials, or generated build output.

## Change Scope & Configuration

- Preserve the existing project structure whenever possible and retain existing edge-to-edge handling.
- Keep file changes focused on the current task. Do not make unrelated refactors or improvements.
- Do not modify Gradle files, `AndroidManifest.xml`, dependencies, SDK configuration, the package name, or unrelated project files unless genuinely required. Before modifying configuration or unrelated files, explain why the change is necessary.
