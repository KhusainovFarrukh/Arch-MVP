package kh.farrukh.arch_mvp.add_movie

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.ui.add_movie.AddMovieContract
import kh.farrukh.arch_mvp.ui.add_movie.AddMoviePresenter
import kh.farrukh.arch_mvp.utils.BaseTest
import kh.farrukh.arch_mvp.utils.RxImmediateSchedulerRule
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
 *Created by farrukh_kh on 5/19/22 2:18 PM
 *kh.farrukh.arch_mvp.add_movie
 **/
@RunWith(MockitoJUnitRunner::class)
class AddMoviePresenterTests : BaseTest() {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: AddMovieContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: LocalDataSource

    private lateinit var addMoviePresenter: AddMoviePresenter

    @Before
    fun setup() {
        addMoviePresenter = AddMoviePresenter(mockActivity, mockDataSource)
    }

    @Test
    fun testAddMovie_noTitle() {
        addMoviePresenter.addMovie("", "", "")

        Mockito.verify(mockActivity).displayError("Movie title cannot be empty.")
    }

    @Captor
    private lateinit var movieArgCaptor: ArgumentCaptor<Movie>

    @Test
    fun testAddMovie_withTitle() {
        addMoviePresenter.addMovie("Tenet", "2021", "/tenet-2021.png")

        Mockito.verify(mockDataSource).insert(captureArg(movieArgCaptor))

        assertEquals("Tenet", movieArgCaptor.value.title)

        Mockito.verify(mockActivity).returnToMain()
    }
}