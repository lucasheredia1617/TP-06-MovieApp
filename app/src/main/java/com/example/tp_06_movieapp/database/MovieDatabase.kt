package com.example.tp_06_movieapp.database

import com.example.tp_06_movieapp.database.dao.MovieDao
import com.example.tp_06_movieapp.database.mapper.mapToDataBaseMovie
import com.example.tp_06_movieapp.database.mapper.mapToLocalMovie
import com.example.tp_06_movieapp.service.model.Movie

interface MovieDatabase {
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun getAllMovies(): List<Movie>
}

class MovieDatabaseImplementation(private val movieDao: MovieDao) : MovieDatabase {
    override suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach {
                movie ->
            movieDao.insertMovie(movie.mapToDataBaseMovie())
        }
    }

    override suspend fun getAllMovies(): List<Movie> {
        return movieDao.getDBMovies().mapToLocalMovie()
    }
}
