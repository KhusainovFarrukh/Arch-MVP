package kh.farrukh.arch_mvp.main

import kh.farrukh.arch_mvp.data.Movie
import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.data.local.MovieDao
import kh.farrukh.arch_mvp.ui.main.MainContract
import kh.farrukh.arch_mvp.ui.main.MainPresenter
import kh.farrukh.arch_mvp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
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
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockActivity: MainContract.ViewInterface

    @Mock
    private lateinit var mockDao: MovieDao

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
        mainPresenter = MainPresenter(
            mockActivity,
            LocalDataSource(mockDao, mainCoroutineRule.coroutineContext)
        )
    }

    @Test
    fun testGetMyMoviesList() = mainCoroutineRule.runBlockingTest {

        val myDummyMovies = dummyAllMovies

        Mockito.`when`(mockDao.allMovies).thenReturn(flowOf(myDummyMovies))

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDao).allMovies
        Mockito.verify(mockActivity).displayMovies(myDummyMovies)
    }

    @Test
    fun testGetMyMoviesList_emptyList() = mainCoroutineRule.runBlockingTest {

        Mockito.`when`(mockDao.allMovies).thenReturn(flowOf(emptyList()))

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDao).allMovies
        Mockito.verify(mockActivity).displayEmptyLayout(true)
    }

    @Test
    fun testDelete_single() = mainCoroutineRule.runBlockingTest {
        val myDeletedHashSet = deletedHashSetSingle

        mainPresenter.onDelete(myDeletedHashSet)

        for (movie in myDeletedHashSet) {
            Mockito.verify(mockDao).delete(movie.id)
        }

        Mockito.verify(mockActivity).displayMessage("Movie deleted.")
    }

    @Test
    fun testDelete_multiple() = mainCoroutineRule.runBlockingTest {
        val myDeletedHashSet = deletedHashSetMultiple

        mainPresenter.onDelete(myDeletedHashSet)

        for (movie in myDeletedHashSet) {
            Mockito.verify(mockDao).delete(movie.id)
        }

        Mockito.verify(mockActivity).displayMessage("Movies deleted.")
    }
}