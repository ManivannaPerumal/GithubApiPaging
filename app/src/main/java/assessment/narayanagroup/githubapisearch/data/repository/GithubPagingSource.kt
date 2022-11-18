package assessment.narayanagroup.githubapisearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import assessment.narayanagroup.githubapisearch.data.Constant.GITHUB_STARTING_PAGE_INDEX
import assessment.narayanagroup.githubapisearch.data.Constant.NETWORK_PAGE_SIZE
import assessment.narayanagroup.githubapisearch.data.api.ApiServices
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.data.repository.home.HomeLocalDataSourceImpl
import assessment.narayanagroup.githubapisearch.presentation.App
import assessment.narayanagroup.githubapisearch.presentation.utils.Util.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


class GithubPagingSource(
    private val service: ApiServices,
    private val query: String,
    private val homeLocalDataSourceImpl: HomeLocalDataSourceImpl
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        // val apiQuery = query + IN_QUALIFIER
        val apiQuery = query
        return try {
            var repos: List<Repository> = emptyList()

            if (isOnline(App.CONTEXT)) {
                val response = service.getRepositoryList(apiQuery, position, params.loadSize)
                repos = response.items
            } else {
                if (position == 1) {

                    CoroutineScope(Dispatchers.IO).launch {

                        repos = homeLocalDataSourceImpl.getRepositoryFromDB()
                    }
                    delay(3000L)
                }
            }

            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            try {
                if (position == 1) {

                    CoroutineScope(Dispatchers.IO).launch {
                        repos.let {
                            // homeLocalDataSourceImpl.clearAll()
                            homeLocalDataSourceImpl.saveRepositoryToDB(it.take(15))
                        }
                    }
                }
            } catch (_: Exception) {
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

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}