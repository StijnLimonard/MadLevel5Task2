package com.example.madlevel5task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.madlevel5task2.dao.GameDao
import com.example.madlevel5task2.model.Game


@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GamesListRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAMES_LIST_DATABASE"

        @Volatile
        private var gameListRoomDatabaseInstance: GamesListRoomDatabase? = null

        fun getDatabaseInstance(context: Context): GamesListRoomDatabase? {
            if (gameListRoomDatabaseInstance == null) {
                 synchronized(GamesListRoomDatabase::class.java) {
                    if (gameListRoomDatabaseInstance == null) {
                        gameListRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GamesListRoomDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return gameListRoomDatabaseInstance
        }
    }
}