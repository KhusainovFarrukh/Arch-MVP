package kh.farrukh.arch_mvp.search

import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.remote.RemoteDataSource
import kh.farrukh.arch_mvp.data.remote.SearchMovieResponse
import kh.farrukh.arch_mvp.ui.search.SearchContract
import kh.farrukh.arch_mvp.ui.search.SearchPresenter
import kh.farrukh.arch_mvp.utils.BaseTest
import kh.farrukh.arch_mvp.utils.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 *Created by farrukh_kh on 5/19/22 2:18 PM
 *kh.farrukh.arch_mvp.search
 **/
@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: SearchContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    private lateinit var searchPresenter: SearchPresenter

    private val dummyResponse: SearchMovieResponse
        get() {
            val dummyMoviesList = ArrayList<Movie>().apply {
                add(
                    Movie(
                        title = "Movie1",
                        releaseDate = "Date1",
                        posterPath = "Poster1",
                        id = hashCode()
                    )
                )
                add(
                    Movie(
                        title = "Movie2",
                        releaseDate = "Date2",
                        posterPath = "Poster2",
                        id = hashCode()
                    )
                )
                add(
                    Movie(
                        title = "Movie3",
                        releaseDate = "Date3",
                        posterPath = "Poster3",
                        id = hashCode()
                    )
                )
                add(
                    Movie(
                        title = "Movie4",
                        releaseDate = "Date4",
                        posterPath = "Poster4",
                        id = hashCode()
                    )
                )
                add(
                    Movie(
                        title = "Movie5",
                        releaseDate = "Date5",
                        posterPath = "Poster5",
                        id = hashCode()
                    )
                )
            }

            return SearchMovieResponse(1, 5, 5, dummyMoviesList)
        }

    @Before
    fun setup() {
        searchPresenter = SearchPresenter(mockActivity, mockDataSource)
    }

    @Test
    fun testSearchMovie() {

        val myDummyResponse = dummyResponse

        Mockito.`when`(mockDataSource.searchResults(anyString()))
            .thenReturn(Flowable.just(Result.success(myDummyResponse)))

        searchPresenter.getSearchResults("Query")

        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Test
    fun testSearchMovie_error() {

        Mockito.`when`(mockDataSource.searchResults(anyString()))
            .thenReturn(Flowable.just(Result.failure(Throwable("Something went wrong"))))

        searchPresenter.getSearchResults(anyString())

        Mockito.verify(mockActivity).displayError(any())
    }
}