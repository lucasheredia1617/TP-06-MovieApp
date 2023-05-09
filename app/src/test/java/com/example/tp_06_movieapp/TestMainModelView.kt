package com.example.tp_06_movieapp // ktlint-disable package-name

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tp_06_movieapp.mvvm.contract.MainContract
import com.example.tp_06_movieapp.mvvm.viewmodel.MainViewModel
import com.example.tp_06_movieapp.service.model.Movie
import com.example.tp_06_movieapp.util.CoroutineResult
import io.mockk.* // ktlint-disable no-wildcard-imports
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.Exception

class TestMainModelView {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var model: MainContract.Model

    @MockK
    private lateinit var movieList: List<Movie>

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(model)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if the service fails show error message`() {
        coEvery { model.getPopularMovies() } returns CoroutineResult.Failure(Exception())

        runBlocking { viewModel.callService().join() }

        assertEquals(MainViewModel.MainStatus.ERROR, viewModel.getValueViewModel().value?.status)
    }

    @Test
    fun `get the list of movies if the service call is successful`() {
        coEvery { model.getPopularMovies() } returns CoroutineResult.Success(movieList)

        runBlocking { viewModel.callService().join() }

        assertEquals(movieList, viewModel.getValueViewModel().value?.movies)
        assertEquals(MainViewModel.MainStatus.SHOW_INFO, viewModel.getValueViewModel().value?.status)
    }
}
