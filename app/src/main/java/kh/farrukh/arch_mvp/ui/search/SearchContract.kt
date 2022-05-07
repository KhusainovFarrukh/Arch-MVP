package kh.farrukh.arch_mvp.ui.search

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.remote.SearchMovieResponse

/**
 *Created by farrukh_kh on 5/6/22 12:50 PM
 *kh.farrukh.arch_mvp.ui.search
 **/
class SearchContract {

    interface PresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
    }

    interface ViewInterface {
        fun displayResult(response: SearchMovieResponse)
        fun displayEmptyLayout(isVisible: Boolean)
        fun returnToAddMovie(movie: Movie)
        fun displayMessage(message: String)
        fun displayError(error: Throwable?)
    }
}