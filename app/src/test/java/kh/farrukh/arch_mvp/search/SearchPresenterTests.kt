package kh.farrukh.arch_mvp.search

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.remote.MoviesApi
import kh.farrukh.arch_mvp.data.remote.RemoteDataSource
import kh.farrukh.arch_mvp.data.remote.SearchMovieResponse
import kh.farrukh.arch_mvp.ui.search.SearchContract
import kh.farrukh.arch_mvp.ui.search.SearchPresenter
import kh.farrukh.arch_mvp.utils.BaseTest
import kh.farrukh.arch_mvp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 *Created by farrukh_kh on 5/19/22 8:29 AM
 *kh.farrukh.arch_mvp.search
 **/
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockActivity: SearchContract.ViewInterface

    @Mock
    private lateinit var mockApi: MoviesApi

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
        searchPresenter = SearchPresenter(
            mockActivity,
            RemoteDataSource(mockApi, mainCoroutineRule.coroutineContext)
        )
    }

    @Test
    fun testSearchMovie() = mainCoroutineRule.runBlockingTest {

        val myDummyResponse = dummyResponse

        Mockito.`when`(mockApi.searchMovie(anyString(), anyString()))
            .thenReturn(Response.success(myDummyResponse))

        searchPresenter.getSearchResults("Query")

        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Test
    fun testSearchMovie_error() = mainCoroutineRule.runBlockingTest {

        Mockito.`when`(mockApi.searchMovie(anyString(), anyString()))
            .thenReturn(Response.error(400, ResponseBody.create(null, "")))

        searchPresenter.getSearchResults(anyString())

        Mockito.verify(mockActivity).displayError(anyString())
    }
}