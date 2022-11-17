package assessment.narayanagroup.githubapisearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import assessment.narayanagroup.githubapisearch.data.Constant.NETWORK_PAGE_SIZE
import assessment.narayanagroup.githubapisearch.data.GithubPagingSource
import assessment.narayanagroup.githubapisearch.data.api.AppRestClient
import assessment.narayanagroup.githubapisearch.data.model.Repository
import kotlinx.coroutines.flow.Flow

class RepoListRepository(private val service: AppRestClient) {

    fun getSearchResultStream(query: String): Flow<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(service.wServer, query) }
        ).flow
    }

}