package com.example.downloadfiles.repository.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.downloadfiles.models.FileInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFiles(files: List<FileInfo>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFile(files: FileInfo): Completable

    @Query("SELECT * from file_table ")
    fun getSavedFiles(): Observable<List<FileInfo>>
}