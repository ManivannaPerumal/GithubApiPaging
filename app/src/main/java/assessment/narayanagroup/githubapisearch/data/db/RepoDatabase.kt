package assessment.narayanagroup.githubapisearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import assessment.narayanagroup.githubapisearch.data.model.Repository

@Database(entities = [Repository::class],
    version = 1,
    exportSchema = false
)

abstract class RepoDatabase :RoomDatabase(){
abstract fun RepositoryDao() : RepositoryDao
}