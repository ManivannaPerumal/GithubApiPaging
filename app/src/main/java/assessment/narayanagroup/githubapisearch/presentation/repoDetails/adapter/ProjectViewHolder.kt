

package assessment.narayanagroup.githubapisearch.presentation.repoDetails.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import assessment.narayanagroup.githubapisearch.data.model.RepoContributorResponseItem
import assessment.narayanagroup.githubapisearch.data.model.RepoProjectsResponseItem
import assessment.narayanagroup.githubapisearch.databinding.ContributorsViewholderBinding
import assessment.narayanagroup.githubapisearch.databinding.ProjectViewholderBinding
import assessment.narayanagroup.githubapisearch.presentation.App
import assessment.narayanagroup.githubapisearch.presentation.WebViewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class ProjectViewHolder(
    private val binding: ProjectViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: RepoProjectsResponseItem) {
        binding.apply {
            val html = "<u>"+article.name+"</u>"
          binding.description.text = html
            binding.adapterProjectViewHolder.setOnClickListener {
                val intent = Intent(App.CONTEXT, WebViewActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("readmeUrl", article.htmlUrl)
                App.CONTEXT.startActivity(intent)
            }

        }
    }
}
