package assessment.narayanagroup.githubapisearch.presentation.repoDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import assessment.narayanagroup.githubapisearch.data.model.RepoProjectsResponseItem
import assessment.narayanagroup.githubapisearch.databinding.ProjectViewholderBinding


class ProjectAdapter : PagingDataAdapter<RepoProjectsResponseItem, ProjectViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder =
        ProjectViewHolder(
            ProjectViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepoProjectsResponseItem>() {
            override fun areItemsTheSame(oldItem: RepoProjectsResponseItem, newItem: RepoProjectsResponseItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RepoProjectsResponseItem, newItem: RepoProjectsResponseItem): Boolean =
                oldItem == newItem
        }
    }


}
