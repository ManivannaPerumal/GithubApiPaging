

package assessment.narayanagroup.githubapisearch.presentation.repoDetails.adapter


import androidx.recyclerview.widget.RecyclerView
import assessment.narayanagroup.githubapisearch.data.model.RepoContributorResponseItem
import assessment.narayanagroup.githubapisearch.databinding.ContributorsViewholderBinding
import assessment.narayanagroup.githubapisearch.presentation.App
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class ContributorsViewHolder(
    private val binding: ContributorsViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.imgContributorDP.setOnClickListener {

        }
    }

    fun bind(article: RepoContributorResponseItem) {
        binding.apply {
          //  binding.title.text = article.login
            Glide.with(App.CONTEXT)
                .load(article.avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(android.R.drawable.stat_notify_error)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.imgContributorDP)
        }
    }
}
