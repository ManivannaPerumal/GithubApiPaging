package assessment.narayanagroup.githubapisearch.data.repository.home

import assessment.narayanagroup.githubapisearch.data.model.Repository

interface HomeLocalDataSource {
  suspend fun getRepositoryFromDB():List<Repository>
  suspend fun saveRepositoryToDB(artists:List<Repository>)
  suspend fun clearAll()
}