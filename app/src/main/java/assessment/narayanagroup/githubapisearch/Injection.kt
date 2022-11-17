package assessment.narayanagroup.githubapisearch

import androidx.lifecycle.ViewModelProvider
import assessment.narayanagroup.githubapisearch.data.api.AppRestClient
import assessment.narayanagroup.githubapisearch.data.repository.RepoListRepository

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {


    private fun provideGithubRepository(): RepoListRepository {
        return RepoListRepository(AppRestClient)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
   /* fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository())
    }*/
}
