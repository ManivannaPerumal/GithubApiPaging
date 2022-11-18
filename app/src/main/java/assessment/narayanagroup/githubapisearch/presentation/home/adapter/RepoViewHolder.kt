package assessment.narayanagroup.githubapisearch.presentation.home.adapter

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import assessment.narayanagroup.githubapisearch.R
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.presentation.repoDetails.RepoDetailActivity
import java.io.Serializable

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)


    private var repo: Repository? = null

    init {
        view.setOnClickListener {
            repo?.let { url ->
                val intent = Intent(view.context, RepoDetailActivity::class.java)
                intent.putExtra("repo_value", repo as Parcelable)
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: Repository?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE

        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repository) {
        this.repo = repo
        name.text = repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}
