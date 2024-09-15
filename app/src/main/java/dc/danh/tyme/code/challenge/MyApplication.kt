package dc.danh.tyme.code.challenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main application class for the app. Annotated with `@HiltAndroidApp` to trigger Hilt's code generation
 * and dependency injection initialization.
 */
@HiltAndroidApp
class MyApplication : Application()
