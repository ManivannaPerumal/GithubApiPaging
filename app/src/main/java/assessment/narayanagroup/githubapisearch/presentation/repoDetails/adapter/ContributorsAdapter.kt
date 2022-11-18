package assessment.narayanagroup.githubapisearch.presentation.repoDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import assessment.narayanagroup.githubapisearch.data.model.RepoContributorResponseItem
import assessment.narayanagroup.githubapisearch.databinding.ContributorsViewholderBinding


class ContributorsAdapter :
    PagingDataAdapter<RepoContributorResponseItem, ContributorsViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsViewHolder =
        ContributorsViewHolder(
            ContributorsViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ContributorsViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<RepoContributorResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: RepoContributorResponseItem,
                    newItem: RepoContributorResponseItem
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: RepoContributorResponseItem,
                    newItem: RepoContributorResponseItem
                ): Boolean =
                    oldItem == newItem
            }
    }
}
