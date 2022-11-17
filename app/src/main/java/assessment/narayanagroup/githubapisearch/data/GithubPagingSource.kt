package assessment.narayanagroup.githubapisearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import assessment.narayanagroup.githubapisearch.data.Constant.IN_QUALIFIER
import assessment.narayanagroup.githubapisearch.data.Constant.NETWORK_PAGE_SIZE
import assessment.narayanagroup.githubapisearch.data.api.ApiServices
import assessment.narayanagroup.githubapisearch.data.model.Repository
import okio.IOException
import retrofit2.HttpException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: ApiServices,
    private val query: String
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = service.getRepositoryList(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
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
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}