package kh.farrukh.arch_mvp.ui.add_movie

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource

/**
 *Created by farrukh_kh on 5/6/22 12:44 PM
 *kh.farrukh.arch_mvp.ui.add_movie
 **/
class AddMoviePresenter(
    private var viewInterface: AddMovieContract.ViewInterface,
    private var dataSource: LocalDataSource
) : AddMovieContract.PresenterInterface {

    override suspend fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Movie title cannot be empty.")
        } else {
            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            dataSource.insert(movie)
            viewInterface.returnToMain()
        }
    }
}