package assessment.narayanagroup.githubapisearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.presentation.App

@Database(
    entities = [Repository::class],
    version = 1,
    exportSchema = false
)

abstract class RepoDatabase : RoomDatabase() {
    abstract fun RepositoryDao(): RepositoryDao

    companion object {

        @Volatile
        private var DB: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase =
            DB ?: synchronized(this) {
                DB
                    ?: buildDatabase(context).also { DB = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                App.CONTEXT,
                RepoDatabase::class.java, "assessment.db"
            )
                .build()
    }
}