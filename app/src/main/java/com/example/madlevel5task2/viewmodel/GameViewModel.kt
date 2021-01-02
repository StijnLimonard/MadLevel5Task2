package com.example.madlevel5task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application.applicationContext)

    private val mainScope = CoroutineScope(Dispatchers.Main)

    val gamesList: LiveData<List<Game>> = repository.getGameList()

    fun insertGame(title: String, platform: String, date: String) {
        mainScope.launch {
            val game = Game(
                title = title,
                platform = platform,
                date = date
            )
            withContext(Dispatchers.IO) {
                repository.insertGame(game)
            }
        }
    }

    fun deleteGame(game: Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteGame(game)
            }
        }
    }

    fun deleteAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteAllGames()
            }
        }
    }

}