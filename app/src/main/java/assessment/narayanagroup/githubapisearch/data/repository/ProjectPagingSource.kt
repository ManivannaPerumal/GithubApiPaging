package assessment.narayanagroup.githubapisearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import assessment.narayanagroup.githubapisearch.data.Constant.GITHUB_STARTING_PAGE_INDEX
import assessment.narayanagroup.githubapisearch.data.Constant.NETWORK_PAGE_SIZE
import assessment.narayanagroup.githubapisearch.data.api.ApiServices
import assessment.narayanagroup.githubapisearch.data.model.RepoProjectsResponseItem
import okio.IOException
import retrofit2.HttpException


class ProjectPagingSource(
    private val service: ApiServices,
    private val owner: String, private val repo: String
) : PagingSource<Int, RepoProjectsResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoProjectsResponseItem> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        // val apiQuery = query + IN_QUALIFIER

        return try {
            val response = service.getRepoProjects(
                owner = owner, repo = repo, position, params.loadSize
            )


            val nextKey = if (response.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = response,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, RepoProjectsResponseItem>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}