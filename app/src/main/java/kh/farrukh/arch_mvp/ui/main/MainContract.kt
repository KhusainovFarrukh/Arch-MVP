package kh.farrukh.arch_mvp.ui.main

import kh.farrukh.arch_mvp.data.Movie
import java.util.HashSet

/**
 *Created by farrukh_kh on 5/6/22 12:20 PM
 *kh.farrukh.arch_mvp.ui.main
 **/
class MainContract {

    interface PresenterInterface {
        fun getMyMoviesList()
        fun onDelete(selectedMovies: HashSet<Movie>)
        fun stop()
    }

    interface ViewInterface {
        fun displayMovies(movieList: List<Movie>)
        fun displayEmptyLayout(isVisible: Boolean)
        fun clearSelectedMovies()
        fun goToAddMovie()
        fun displayMessage(message: String)
        fun displayError(error: Throwable)
    }
}