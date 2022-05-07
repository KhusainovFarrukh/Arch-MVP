package kh.farrukh.arch_mvp.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kh.farrukh.arch_mvp.R
import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.remote.RemoteDataSource
import kh.farrukh.arch_mvp.data.remote.SearchMovieResponse
import kh.farrukh.arch_mvp.databinding.ActivitySearchBinding
import kh.farrukh.arch_mvp.utils.toast
import org.koin.android.ext.android.inject

/**
 *Created by farrukh_kh on 4/6/22 5:49 PM
 *kh.farrukh.arch_mvc.ui
 **/
class SearchActivity : AppCompatActivity(R.layout.activity_search), SearchContract.ViewInterface {

    private val binding by viewBinding(ActivitySearchBinding::bind)
    private val searchAdapter by lazy { SearchAdapter(::returnToAddMovie) }
    private val dataSource by inject<RemoteDataSource>()

    private val presenter: SearchContract.PresenterInterface by lazy {
        SearchPresenter(this, dataSource)
    }

    private val query by lazy { intent?.getStringExtra(SEARCH_QUERY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted { presenter.getSearchResults(query) }
    }

    private fun setupViews() = with(binding) {
        rvSearchResults.adapter = searchAdapter
    }

    override fun displayResult(response: SearchMovieResponse) = with(binding) {
        pbLoading.isVisible = false
        searchAdapter.submitList(response.results)
    }

    override fun displayEmptyLayout(isVisible: Boolean) = with(binding) {
        tvNoMovies.isVisible = isVisible
        if (isVisible) tvNoMovies.text = "No movies found"
    }

    override fun displayMessage(message: String) {
        toast(message)
    }

    override fun displayError(message: String) = with(binding) {
        pbLoading.isVisible = false
        rvSearchResults.isVisible = false
        tvNoMovies.isVisible = true
        tvNoMovies.text = message
    }

    override fun returnToAddMovie(movie: Movie) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_TITLE, movie.title)
            putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate())
            putExtra(EXTRA_POSTER_PATH, movie.posterPath)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val SEARCH_QUERY = "searchQuery"
        const val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        const val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        const val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }
}