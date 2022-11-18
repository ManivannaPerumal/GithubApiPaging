package assessment.narayanagroup.githubapisearch.presentation.repoDetails

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import assessment.narayanagroup.githubapisearch.Injection
import assessment.narayanagroup.githubapisearch.data.model.ReadMe
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.databinding.ActivityRepoDetailBinding
import assessment.narayanagroup.githubapisearch.presentation.WebViewActivity
import assessment.narayanagroup.githubapisearch.presentation.repoDetails.adapter.ContributorsAdapter
import assessment.narayanagroup.githubapisearch.presentation.repoDetails.adapter.ProjectAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch


class RepoDetailActivity : AppCompatActivity() {

    var url : ReadMe = ReadMe("","")
    var projectAdapter = ProjectAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repoDetails = intent.getParcelableExtra<Repository>("repo_value") as Repository

        // get the view model
        val viewModel =
            ViewModelProvider(
                this,
                Injection.provideDetailViewModelFactory(owner = this, context = this, owners = repoDetails.owner.login , repo = repoDetails.name)
            )[RepoDetailsViewModel::class.java]



        binding.appBarLayout.appBar.title = repoDetails.name
        binding.appBarLayout.appBar.setNavigationOnClickListener {
            finish()
        }

        binding.txtDescription.text = repoDetails.description
        binding.txtVisibility.text = repoDetails.visibility
        binding.txtFullName.text = repoDetails.fullName

        val html = "<u>"+repoDetails.url+"</u>"

        if(repoDetails.hasProjects){
            binding.cardProjectList.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }
        else{
            binding.cardProjectList.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
        }

        binding.txtURL.text = Html.fromHtml(html)
        repoDetails.language?.let {
            binding.txtLanguage.visibility = View.VISIBLE
            binding.txtLanguage.text = "Language: $it"
        }

        binding.txtURL.setOnClickListener{
            val intent = Intent(this@RepoDetailActivity,WebViewActivity::class.java)
            intent.putExtra("readmeUrl", repoDetails.url)
            startActivity(intent)
        }

        Glide.with(this)
            .load(repoDetails.owner.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(android.R.drawable.stat_notify_error)
            .error(android.R.drawable.stat_notify_error)
            .into(binding.imgRepoDp)

        viewModel.getReadMe()

        viewModel.readmeDetail.observe(this){
            it?.let {
                url = it
                binding.txtReadMe.text = it.name
                binding.webView.loadUrl(it.htmlUrl)
            }
        }

      /*  binding.txtReadMe.setOnClickListener{

                val intent = Intent(this@RepoDetailActivity,WebViewActivity::class.java)
                intent.putExtra("readmeUrl", url.htmlUrl)
                startActivity(intent)
        }*/

        binding.layoutReadMe.setOnClickListener {
            if (binding.webView.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(binding.cardContributors, AutoTransition())
                binding.webView.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.cardContributors, AutoTransition())
                binding.webView.visibility = View.VISIBLE
            }
        }

        val items = viewModel.pagingDataFlow
        val itemProject = viewModel.pagingProjectDataFlow
        val contributorAdapter = ContributorsAdapter()
         projectAdapter = ProjectAdapter()
        binding.bindAdapter(contributorAdapter = contributorAdapter)
        binding.bindAdapters(projectAdapter = projectAdapter)

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    contributorAdapter.submitData(it)

                }



            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

              itemProject.collectLatest {
                    projectAdapter.submitData(it)
                }


            }
        }

    }



    private fun ActivityRepoDetailBinding.bindAdapter(contributorAdapter: ContributorsAdapter) {
        list.adapter = contributorAdapter

        list.layoutManager = LinearLayoutManager(list.context,
            LinearLayoutManager.HORIZONTAL,
            false)
        val decoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
    }

    private fun ActivityRepoDetailBinding.bindAdapters(projectAdapter: ProjectAdapter) {
        projectList.adapter = projectAdapter
        projectList.layoutManager = LinearLayoutManager(projectList.context)
        val decoration = DividerItemDecoration(projectList.context, DividerItemDecoration.VERTICAL)
        projectList.addItemDecoration(decoration)
    }


}

