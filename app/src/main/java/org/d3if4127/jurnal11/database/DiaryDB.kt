package org.d3if4127.jurnal11.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class DiaryDB : RoomDatabase() {
    abstract val DiaryDao: DiaryDAO
    companion object {
        @Volatile
        private var INSTANCE: DiaryDB? = null

        fun getInstance(context: Context): DiaryDB {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryDB::class.java,
                        "Diary_table"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}