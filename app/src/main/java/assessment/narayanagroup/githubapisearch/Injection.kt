package assessment.narayanagroup.githubapisearch

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import assessment.narayanagroup.githubapisearch.data.api.AppRestClient
import assessment.narayanagroup.githubapisearch.data.db.RepoDatabase
import assessment.narayanagroup.githubapisearch.data.repository.RepoListRepository
import assessment.narayanagroup.githubapisearch.data.repository.home.HomeLocalDataSourceImpl
import assessment.narayanagroup.githubapisearch.domain.RepoDetailRepository
import assessment.narayanagroup.githubapisearch.presentation.home.ViewModelFactory
import assessment.narayanagroup.githubapisearch.presentation.repoDetails.RepoDetailViewModelFactory

object Injection {


    private fun provideGithubRepository(context: Context): RepoListRepository {

        return RepoListRepository(
            AppRestClient,
            HomeLocalDataSourceImpl(RepoDatabase.getInstance(context).RepositoryDao())
        )
    }

    private fun provideDetailRepository(
        context: Context,
        owner: String,
        repo: String
    ): RepoDetailRepository {

        return RepoDetailRepository(AppRestClient, owner = owner, repo = repo)
    }

    fun provideViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository(context))
    }

    fun provideDetailViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner,
        owners: String,
        repo: String
    ): ViewModelProvider.Factory {
        return RepoDetailViewModelFactory(owner, provideDetailRepository(context, owners, repo))
    }
}
