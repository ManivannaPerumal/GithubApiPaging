package assessment.narayanagroup.githubapisearch.data.repository.home

import androidx.paging.PagingData
import assessment.narayanagroup.githubapisearch.data.model.Repository
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    suspend fun getRepositoryFromDB(): List<Repository>

  suspend fun saveRepositoryToDB(artists:List<Repository>)
  suspend fun clearAll()
}