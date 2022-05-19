package kh.farrukh.arch_mvp.data.local

import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvp.data.Movie
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
@Singleton
open class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    open val allMovies: Flowable<List<Movie>> by lazy { movieDao.allMovies }

    open fun insert(movie: Movie) = thread {
        movieDao.insert(movie)
    }

    open fun delete(movie: Movie) = thread {
        movieDao.delete(movie.id)
    }

    open fun update(movie: Movie) = thread {
        movieDao.update(movie)
    }
}