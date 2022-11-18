package assessment.narayanagroup.githubapisearch.presentation

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
     super.onCreate()
      CONTEXT = applicationContext
    }
}