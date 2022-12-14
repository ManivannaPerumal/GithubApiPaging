package assessment.narayanagroup.githubapisearch.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import assessment.narayanagroup.githubapisearch.base.BaseRepository
import assessment.narayanagroup.githubapisearch.data.Constant.NETWORK_PAGE_SIZE
import assessment.narayanagroup.githubapisearch.data.api.AppRestClient
import assessment.narayanagroup.githubapisearch.data.db.RepoDatabase
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.data.repository.home.HomeLocalDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import java.lang.Exception

class RepoListRepository(
    private val service: AppRestClient,
    private val homeLocalDataSourceImpl: HomeLocalDataSourceImpl
) : BaseRepository() {


    fun getSearchResultStream(query: String): Flow<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubPagingSource(
                    service.wServer,
                    query,
                    homeLocalDataSourceImpl
                )
            }
        ).flow
    }


    suspend fun getArtistsFromDB(): List<Repository> {

        lateinit var artistList: List<Repository>
        try {
            artistList = homeLocalDataSourceImpl.getRepositoryFromDB()
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }

        return artistList
    }


}

