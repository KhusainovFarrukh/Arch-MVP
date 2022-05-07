package kh.farrukh.arch_mvp.ui.main

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import org.reactivestreams.Subscription

/**
 *Created by farrukh_kh on 5/6/22 12:22 PM
 *kh.farrukh.arch_mvp.ui.main
 **/
class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var dataSource: LocalDataSource
) : MainContract.PresenterInterface {

    private var allMoviesSubscription: Subscription? = null

    override fun getMyMoviesList() {
        dataSource.allMovies
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { subscription -> allMoviesSubscription = subscription }
            .subscribe(
                { movies ->
                    viewInterface.displayMovies(movies)
                    viewInterface.displayEmptyLayout(movies.isEmpty())
                },
                viewInterface::displayError
            )
    }

    override fun onDelete(selectedMovies: HashSet<Movie>) {
        for (movie in selectedMovies) {
            dataSource.delete(movie)
        }
        viewInterface.clearSelectedMovies()
        if (selectedMovies.size == 1) {
            viewInterface.displayMessage("Movie deleted.")
        } else if (selectedMovies.size > 1) {
            viewInterface.displayMessage("Movies deleted.")
        }
    }

    override fun stop() {
        allMoviesSubscription?.cancel()
        allMoviesSubscription = null
    }
}