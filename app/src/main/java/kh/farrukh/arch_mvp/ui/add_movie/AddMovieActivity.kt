package kh.farrukh.arch_mvp.ui.add_movie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kh.farrukh.arch_mvp.R
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.data.remote.RetrofitClient.TMDB_IMAGE_URL
import kh.farrukh.arch_mvp.databinding.ActivityAddMovieBinding
import kh.farrukh.arch_mvp.ui.search.SearchActivity
import kh.farrukh.arch_mvp.utils.getString
import kh.farrukh.arch_mvp.utils.load
import kh.farrukh.arch_mvp.utils.startActivityForResult
import kh.farrukh.arch_mvp.utils.toast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 *Created by farrukh_kh on 4/3/22 10:26 PM
 *kh.farrukh.arch_mvc.ui
 **/
class AddMovieActivity : AppCompatActivity(R.layout.activity_add_movie),
    AddMovieContract.ViewInterface {

    private val binding by viewBinding(ActivityAddMovieBinding::bind)
    private val dataSource by inject<LocalDataSource>()

    private val presenter: AddMovieContract.PresenterInterface by lazy {
        AddMoviePresenter(this, dataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() = with(binding) {
        btnSearch.setOnClickListener { goToSearch() }
        btnAddMovie.setOnClickListener { addMovie() }
    }

    override fun goToSearch() {
        val intent = Intent(this, SearchActivity::class.java).apply {
            putExtra(SearchActivity.SEARCH_QUERY, binding.tetTitle.text.toString())
        }
        searchMovieLauncher.launch(intent)
    }

    private fun addMovie() = with(binding) {
        lifecycleScope.launch {
            val title = tetTitle.text.toString()
            val releaseDate = tetReleaseYear.text.toString()
            val posterPath = if (ivPoster.tag != null) ivPoster.tag.toString() else ""
            presenter.addMovie(title, releaseDate, posterPath)
        }
    }

    private val searchMovieLauncher = startActivityForResult { result ->
        when (result?.resultCode) {
            Activity.RESULT_OK -> binding.apply {
                tetTitle.setText(result.getString(SearchActivity.EXTRA_TITLE) ?: "")
                tetReleaseYear.setText(
                    result.getString(SearchActivity.EXTRA_RELEASE_DATE) ?: ""
                )
                ivPoster.tag = result.getString(SearchActivity.EXTRA_POSTER_PATH)
                ivPoster.load(TMDB_IMAGE_URL + result.getString(SearchActivity.EXTRA_POSTER_PATH))
            }
            Activity.RESULT_CANCELED -> displayMessage("No movie selected")
            else -> displayMessage("Something went wrong")
        }
    }

    override fun returnToMain() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun displayMessage(message: String) {
        toast(message)
    }

    override fun displayError(message: String) {
        toast(message)
    }
}