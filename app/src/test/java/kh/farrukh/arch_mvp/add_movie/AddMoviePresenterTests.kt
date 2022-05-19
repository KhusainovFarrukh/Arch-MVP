package kh.farrukh.arch_mvp.add_movie

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.data.local.MovieDao
import kh.farrukh.arch_mvp.ui.add_movie.AddMovieContract
import kh.farrukh.arch_mvp.ui.add_movie.AddMoviePresenter
import kh.farrukh.arch_mvp.utils.BaseTest
import kh.farrukh.arch_mvp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 *Created by farrukh_kh on 5/19/22 8:04 AM
 *kh.farrukh.arch_mvp.add_movie
 **/
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddMoviePresenterTests : BaseTest() {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockActivity: AddMovieContract.ViewInterface

    @Mock
    private lateinit var mockDao: MovieDao

    private lateinit var addMoviePresenter: AddMoviePresenter

    @Before
    fun setup() {
        addMoviePresenter = AddMoviePresenter(
            mockActivity,
            LocalDataSource(mockDao, mainCoroutineRule.coroutineContext)
        )
    }

    @Test
    fun testAddMovie_noTitle() = mainCoroutineRule.runBlockingTest {
        addMoviePresenter.addMovie("", "", "")

        Mockito.verify(mockActivity).displayError("Movie title cannot be empty.")
    }

    @Captor
    private lateinit var movieArgCaptor: ArgumentCaptor<Movie>

    @Test
    fun testAddMovie_withTitle() = mainCoroutineRule.runBlockingTest {
        addMoviePresenter.addMovie("Tenet", "2021", "/tenet-2021.png")

        Mockito.verify(mockDao).insert(captureArg(movieArgCaptor))

        assertEquals("Tenet", movieArgCaptor.value.title)

        Mockito.verify(mockActivity).returnToMain()
    }
}