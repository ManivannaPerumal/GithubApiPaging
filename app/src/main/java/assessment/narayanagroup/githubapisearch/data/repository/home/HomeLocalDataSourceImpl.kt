package assessment.narayanagroup.githubapisearch.data.repository.home

import assessment.narayanagroup.githubapisearch.data.db.RepositoryDao
import assessment.narayanagroup.githubapisearch.data.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeLocalDataSourceImpl(private val artistDao:RepositoryDao):
    HomeLocalDataSource {
    override suspend fun getRepositoryFromDB(): List<Repository> {
       return artistDao.getRepository()
    }

    override suspend fun saveRepositoryToDB(artists: List<Repository>) {
        CoroutineScope(Dispatchers.IO).launch {
            artistDao.saveRepository(artists)
        }
    }

    override suspend fun clearAll() {
       CoroutineScope(Dispatchers.IO).launch {
           artistDao.clearRepositoryList()
       }
    }
}