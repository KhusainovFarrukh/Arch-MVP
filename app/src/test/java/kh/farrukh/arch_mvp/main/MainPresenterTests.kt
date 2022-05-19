package kh.farrukh.arch_mvp.main

import io.reactivex.rxjava3.core.Flowable
import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.ui.main.MainContract
import kh.farrukh.arch_mvp.ui.main.MainPresenter
import kh.farrukh.arch_mvp.utils.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 *Created by farrukh_kh on 5/19/22 2:18 PM
 *kh.farrukh.arch_mvp.main
 **/
@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: MainContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: LocalDataSource

    private lateinit var mainPresenter: MainPresenter

    private val dummyAllMovies: ArrayList<Movie>
        get() = ArrayList<Movie>().apply {
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

    private val deletedHashSetSingle: HashSet<Movie>
        get() {
            val deletedHashSet = HashSet<Movie>()
            deletedHashSet.add(dummyAllMovies[0])
            return deletedHashSet
        }

    private val deletedHashSetMultiple: HashSet<Movie>
        get() {
            val deletedHashSet = HashSet<Movie>()
            deletedHashSet.add(dummyAllMovies[0])
            deletedHashSet.add(dummyAllMovies[1])
            return deletedHashSet
        }

    @Before
    fun setUp() {
        mainPresenter = MainPresenter(mockActivity, mockDataSource)
    }

    @Test
    fun testGetMyMoviesList() {

        val myDummyMovies = dummyAllMovies

        Mockito.`when`(mockDataSource.allMovies).thenReturn(Flowable.just(myDummyMovies))

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDataSource).allMovies
        Mockito.verify(mockActivity).displayMovies(myDummyMovies)
    }

    @Test
    fun testGetMyMoviesList_emptyList() {

        Mockito.`when`(mockDataSource.allMovies).thenReturn(Flowable.just(emptyList()))

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDataSource).allMovies
        Mockito.verify(mockActivity).displayEmptyLayout(true)
    }

    @Test
    fun testDelete_single() {
        val myDeletedHashSet = deletedHashSetSingle

        mainPresenter.onDelete(myDeletedHashSet)

        for (movie in myDeletedHashSet) {
            Mockito.verify(mockDataSource).delete(movie)
        }

        Mockito.verify(mockActivity).displayMessage("Movie deleted.")
    }

    @Test
    fun testDelete_multiple() {
        val myDeletedHashSet = deletedHashSetMultiple

        mainPresenter.onDelete(myDeletedHashSet)

        for (movie in myDeletedHashSet) {
            Mockito.verify(mockDataSource).delete(movie)
        }

        Mockito.verify(mockActivity).displayMessage("Movies deleted.")
    }
}