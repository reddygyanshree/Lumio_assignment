# TV Assignment

An Android TV app that displays news articles using Jetpack Compose for TV. It fetches headlines from the [News API](https://newsapi.org/), supports D-pad/remote navigation, and caches articles locally for offline viewing.

## Features 
- **News feed** – Top headlines with title, source, and image
- **Android TV optimized** – Leanback launcher, D-pad focus, TV-friendly UI
- **Offline support** – Room database caches articles when online
- **Network awareness** – Handles connectivity changes and shows cached data when offline
-
## Tech Stack
- **Kotlin** with **Jetpack Compose**
- **Retrofit** + **OkHttp** for News API
- **Room** for local caching
- **Coil** for image loading
- **Coroutines** + **ViewModel**

## Prerequisites 
- Android Studio
- **Android SDK**: compile/target **36**, min **21**

## Setup 
### 1. Clone the project 
### 2. Open in Android Studio 
  1. Open Android Studio.
  2. **File → Open** and select the TVAssignment folder.
  3. Wait for Gradle sync to finish. 
### 3. Run on a device or emulator **Android TV device** 
1. Enable **Developer options** and **USB debugging** on the TV.
2. Connect via USB or use wireless debugging.
3. In Android Studio, choose your TV in the device list and click **Run**.

**Android TV emulator** 
1. **Tools → Device Manager** (or AVD Manager).
2. Create a new Virtual Device and pick a **TV** profile (e.g. “Android TV (1080p)”).
3. Start the emulator, select it as the run target, and click **Run**.

## Project Structure

```
app/
└── src/main/java/com/example/tvassignment
    ├── data
    │   ├── local
    │   ├── remote
    │   └── repository
    ├── state
    ├── ui
    │   ├── components
    │   ├── screen
    │   └── theme
    ├── util
    ├── viewmodel
    ├── MainActivity.kt
    └── TvAssignmentApplication.kt
```
