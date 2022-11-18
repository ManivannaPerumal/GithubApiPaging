package assessment.narayanagroup.githubapisearch.presentation.repoDetails

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import assessment.narayanagroup.githubapisearch.data.repository.RepoListRepository
import assessment.narayanagroup.githubapisearch.domain.RepoDetailRepository

/**
 * Factory for ViewModels
 */
class RepoDetailViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: RepoDetailRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(RepoDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepoDetailsViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
