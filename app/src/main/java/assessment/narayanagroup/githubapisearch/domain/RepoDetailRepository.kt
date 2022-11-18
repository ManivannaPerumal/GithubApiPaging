package assessment.narayanagroup.githubapisearch.domain

import assessment.narayanagroup.githubapisearch.base.BaseRepository
import assessment.narayanagroup.githubapisearch.data.api.AppRestClient
import assessment.narayanagroup.githubapisearch.data.model.ReadMe
import assessment.narayanagroup.githubapisearch.data.repository.ContributorsPagingSource
import assessment.narayanagroup.githubapisearch.data.repository.ProjectPagingSource

class RepoDetailRepository(
    private val appRestClient: AppRestClient,
    private val owner: String,
    private val repo: String
) : BaseRepository() {

    suspend fun getReadme(): ReadMe {
        return apiCall {
            appRestClient.wServer.getReadMe(owner, repo)
        }
    }

    fun getFlow() = ContributorsPagingSource(appRestClient.wServer, owner = owner, repo = repo)

    fun getProjectFlow() = ProjectPagingSource(appRestClient.wServer, owner = owner, repo = repo)


}