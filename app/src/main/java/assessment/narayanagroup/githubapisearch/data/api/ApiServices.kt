package assessment.narayanagroup.githubapisearch.data.api

import assessment.narayanagroup.githubapisearch.data.api.ApiConstant.GET_CONT
import assessment.narayanagroup.githubapisearch.data.api.ApiConstant.GET_REPO
import assessment.narayanagroup.githubapisearch.data.model.RepoContributorResponse
import assessment.narayanagroup.githubapisearch.data.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET(GET_REPO)
    suspend fun getRepositoryList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ):RepoSearchResponse

    @GET(GET_CONT)
    suspend fun getRepoContributors(
        @Path("owner") owner : String,
        @Path("repo") repo : String
    ) : RepoContributorResponse


}