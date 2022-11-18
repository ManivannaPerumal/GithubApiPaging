package assessment.narayanagroup.githubapisearch.presentation.repoDetails

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import assessment.narayanagroup.githubapisearch.base.BaseViewModel
import assessment.narayanagroup.githubapisearch.data.Constant.DISPATCHER_IO
import assessment.narayanagroup.githubapisearch.data.model.*
import assessment.narayanagroup.githubapisearch.domain.RepoDetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RepoDetailsViewModel(private val repoDetailRepository: RepoDetailRepository,
                           private val savedStateHandle: SavedStateHandle) : BaseViewModel()  {


    val readmeDetail : LiveData<ReadMe?>
        get() = readmeDetails

     private val readmeDetails : MutableLiveData<ReadMe?> = MutableLiveData()

    val resultsError: MutableLiveData<String> = MutableLiveData()

    fun getReadMe() {
        launch {
            try {
                val result = withContext(DISPATCHER_IO) {
                    repoDetailRepository.getReadme()
                }
                readmeDetails.value = result
            } catch (e: Exception) {
                resultsError.value = e.toString()
            }
        }
    }


    val pagingDataFlow: Flow<PagingData<RepoContributorResponseItem>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { repoDetailRepository.getFlow() }
    ).flow
        .cachedIn(viewModelScope)

    val pagingProjectDataFlow: Flow<PagingData<RepoProjectsResponseItem>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { repoDetailRepository.getProjectFlow() }
    ).flow
        .cachedIn(viewModelScope)





}