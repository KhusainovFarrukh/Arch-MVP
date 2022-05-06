package kh.farrukh.arch_mvp.ui.add_movie

/**
 *Created by farrukh_kh on 5/6/22 12:42 PM
 *kh.farrukh.arch_mvp.ui.add_movie
 **/
class AddMovieContract {

    interface PresenterInterface {
        suspend fun addMovie(title: String, releaseDate: String, posterPath: String)
    }

    interface ViewInterface {
        fun goToSearch()
        fun returnToMain()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}