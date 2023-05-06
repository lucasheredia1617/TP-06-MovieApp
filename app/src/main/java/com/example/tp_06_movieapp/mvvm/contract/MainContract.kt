package com.example.tp_06_movieapp.mvvm.contract

import androidx.lifecycle.LiveData
import com.example.tp_06_movieapp.mvvm.viewmodel.MainViewModel
import com.example.tp_06_movieapp.service.model.Movie
import com.example.tp_06_movieapp.util.CoroutineResult
import kotlinx.coroutines.Job

interface MainContract {
    interface Model {
        suspend fun getPopularMovies(): CoroutineResult<List<Movie>>
    }
    interface ViewModel {
        fun getValueViewModel(): LiveData<MainViewModel.MainData>
        fun callService(): Job
    }
}
