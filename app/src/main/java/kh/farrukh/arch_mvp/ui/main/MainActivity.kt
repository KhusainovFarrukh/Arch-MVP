package kh.farrukh.arch_mvp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kh.farrukh.arch_mvp.R
import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.databinding.ActivityMainBinding
import kh.farrukh.arch_mvp.ui.add_movie.AddMovieActivity
import kh.farrukh.arch_mvp.utils.startActivityForResult
import kh.farrukh.arch_mvp.utils.toast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main), MainContract.ViewInterface {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val mainAdapter by lazy { MainAdapter() }
    private val dataSource by inject<LocalDataSource>()

    private val presenter: MainContract.PresenterInterface by lazy {
        MainPresenter(this, dataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            presenter.getMyMoviesList()
        }
    }

    private fun setupViews() = with(binding) {
        rvMovies.adapter = mainAdapter
        fabAddMovie.setOnClickListener { goToAddMovie() }
        supportActionBar?.title = "Movies to Watch"
    }

    override fun displayMovies(movieList: List<Movie>) = with(binding) {
        mainAdapter.submitList(movieList)
    }

    override fun displayEmptyLayout(isVisible: Boolean) {
        binding.layoutEmpty.isVisible = isVisible
    }

    override fun goToAddMovie() {
        addMovieLauncher.launch(Intent(this, AddMovieActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            lifecycleScope.launch {
                presenter.onDelete(mainAdapter.selectedMovies)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun clearSelectedMovies() {
        mainAdapter.clearSelectedMovies()
    }

    private val addMovieLauncher = startActivityForResult { result ->
        when (result?.resultCode) {
            Activity.RESULT_OK -> displayMessage("Movie successfully added")
            Activity.RESULT_CANCELED -> displayMessage("No movie provided to add")
            else -> displayMessage("Movie could not been added")
        }
    }

    override fun displayMessage(message: String) {
        toast(message)
    }

    override fun displayError(message: String) {
        toast(message)
    }
}