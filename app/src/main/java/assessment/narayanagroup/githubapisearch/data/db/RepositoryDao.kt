package assessment.narayanagroup.githubapisearch.data.db

import androidx.paging.PagingData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import assessment.narayanagroup.githubapisearch.data.model.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRepository(artists : List<Repository>)

    @Query("DELETE FROM repository_list")
    suspend fun clearRepositoryList()

    @Query("SELECT * FROM repository_list")
    suspend fun getRepository(): List<Repository>
}