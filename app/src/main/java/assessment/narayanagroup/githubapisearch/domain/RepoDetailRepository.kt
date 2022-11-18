package assessment.narayanagroup.githubapisearch.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import assessment.narayanagroup.githubapisearch.base.BaseRepository
import assessment.narayanagroup.githubapisearch.base.BaseRestClient
import assessment.narayanagroup.githubapisearch.data.Constant
import assessment.narayanagroup.githubapisearch.data.api.AppRestClient
import assessment.narayanagroup.githubapisearch.data.model.ReadMe
import assessment.narayanagroup.githubapisearch.data.model.RepoContributorResponse
import assessment.narayanagroup.githubapisearch.data.model.RepoContributorResponseItem
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.data.repository.ContributorsPagingSource
import assessment.narayanagroup.githubapisearch.data.repository.GithubPagingSource
import assessment.narayanagroup.githubapisearch.data.repository.ProjectPagingSource
import kotlinx.coroutines.flow.Flow

class RepoDetailRepository(private val appRestClient: AppRestClient , private val owner : String , private val repo : String) : BaseRepository() {

    suspend fun getReadme(): ReadMe  {
return apiCall {
    appRestClient.wServer.getReadMe(owner,repo)
}
    }

/*    fun getContributorResultStream(): Flow<PagingData<RepoContributorResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ContributorsPagingSource( appRestClient.wServer , owner = owner , repo = repo) }
        ).flow
    }*/

    fun getFlow() = ContributorsPagingSource( appRestClient.wServer , owner = owner , repo = repo)

    fun getProjectFlow() = ProjectPagingSource( appRestClient.wServer , owner = owner , repo = repo)


}