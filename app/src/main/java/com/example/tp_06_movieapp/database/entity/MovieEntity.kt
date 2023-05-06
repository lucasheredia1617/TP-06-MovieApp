package com.example.tp_06_movieapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class MovieEntity(
    @PrimaryKey var id: Int,
    var title: String,
    var poster_path: String,
    var release_date: String
)