package com.example.crossChannel.datas

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: PageEntity)
    @Delete
    fun delete(entity: PageEntity)
    @Update
    fun update(entity: PageEntity)
    @Query("SELECT * FROM pageEntities WHERE pseudoKey>=:pseudoKey")
    fun getEntities(pseudoKey : Int):List<PageEntity>
    @get:Query("SELECT * FROM pageEntities")
    val allEntity:LiveData<MutableList<PageEntity>>
}