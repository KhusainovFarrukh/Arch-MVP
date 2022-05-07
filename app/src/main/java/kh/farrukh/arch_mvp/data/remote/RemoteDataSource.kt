package kh.farrukh.arch_mvp.data.remote

import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvp.BuildConfig
import kh.farrukh.arch_mvp.utils.toResult
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 *Created by farrukh_kh on 4/3/22 4:14 PM
 *kh.farrukh.arch_mvc.model
 **/
@Singleton
class RemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi
) {

    fun searchResults(query: String): Flowable<Result<SearchMovieResponse>> =
        moviesApi.searchMovie(BuildConfig.TMDB_API_KEY, query)
            .map { response -> response.toResult() }
}