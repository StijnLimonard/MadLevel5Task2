package com.example.madlevel5task2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Game(
    val title: String,
    val platform: String,
    val date: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)