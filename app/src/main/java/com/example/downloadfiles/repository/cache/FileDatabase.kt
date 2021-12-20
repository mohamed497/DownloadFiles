package com.example.downloadfiles.repository.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.downloadfiles.base.Constants
import com.example.downloadfiles.models.FileInfo

@Database(entities = [FileInfo::class], version = Constants.DB_VERSION, exportSchema = false)
abstract class FileDatabase : RoomDatabase() {
    abstract val fileDao: FileDao

    companion object {
        @Volatile
        private var INSTANCE: FileDatabase? = null
        fun getInstance(context: Context): FileDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FileDatabase::class.java, Constants.DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}