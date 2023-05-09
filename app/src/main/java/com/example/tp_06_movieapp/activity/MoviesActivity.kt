package com.example.tp_06_movieapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tp_06_movieapp.adapter.MovieAdapter
import com.example.tp_06_movieapp.database.MovieDatabaseImplementation
import com.example.tp_06_movieapp.database.MoviesRoomDatabase
import com.example.tp_06_movieapp.databinding.ActivityMoviesBinding
import com.example.tp_06_movieapp.mvvm.contract.MainContract
import com.example.tp_06_movieapp.mvvm.model.MainModel
import com.example.tp_06_movieapp.mvvm.viewmodel.MainViewModel
import com.example.tp_06_movieapp.mvvm.viewmodel.factory.ViewModelFactory
import com.example.tp_06_movieapp.service.MovieClient
import com.example.tp_06_movieapp.service.MovieRequestGenerator
import com.example.tp_06_movieapp.service.MovieServiceImplementation

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding
    private lateinit var viewModel: MainContract.ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MainActivity::class.java)
        with(binding.buttonBackToMain) {
            setOnClickListener {
                startActivity(intent)
                finish()
            }
        }

        val db: MoviesRoomDatabase by lazy {
            Room
                .databaseBuilder(this, MoviesRoomDatabase::class.java, "Movie-DB")
                .build()
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                arrayOf(
                    MainModel(
                        MovieServiceImplementation(MovieRequestGenerator.createService(MovieClient::class.java)),
                        MovieDatabaseImplementation(db.movieDao()),
                    ),
                ),
            ),
        )[MainViewModel::class.java]

        viewModel.getValueViewModel().observe(this) { updateUI(it) }
    }

    private fun updateUI(data: MainViewModel.MainData) {
        when (data.status) {
            MainViewModel.MainStatus.SHOW_INFO -> {
                if (data.movies.isEmpty()) {
                    binding.recycler.isVisible = false
                    binding.failure.isVisible = true
                } else {
                    binding.recycler.layoutManager = LinearLayoutManager(this)
                    binding.recycler.adapter = MovieAdapter(data.movies)
                }
            }
            MainViewModel.MainStatus.HIDE_INFO -> {
                binding.failure.isVisible = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.callService()
    }
}
