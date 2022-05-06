package kh.farrukh.arch_mvp.ui.main

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import java.util.HashSet

/**
 *Created by farrukh_kh on 5/6/22 12:22 PM
 *kh.farrukh.arch_mvp.ui.main
 **/
class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var dataSource: LocalDataSource
) : MainContract.PresenterInterface {

    override suspend fun getMyMoviesList() {
        // TODO: handle error
        val movies = dataSource.getAll()
        viewInterface.displayEmptyLayout(movies.isEmpty())
        viewInterface.displayMovies(movies)
    }

    override suspend fun onDelete(selectedMovies: HashSet<Movie>) {
        for (movie in selectedMovies) {
            dataSource.delete(movie)
        }
        viewInterface.clearSelectedMovies()
        if (selectedMovies.size == 1) {
            viewInterface.displayMessage("Movie deleted.")
        } else if (selectedMovies.size > 1) {
            viewInterface.displayMessage("Movies deleted.")
        }

        getMyMoviesList()
    }
}