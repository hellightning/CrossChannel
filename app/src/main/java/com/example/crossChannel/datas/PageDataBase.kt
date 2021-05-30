package com.example.crossChannel.datas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.lifecycle.MutableLiveData
import com.example.crossChannel.R
import java.util.*
import kotlin.random.Random

//TODO replace with ROOM operation
@Database(entities = [PageEntity::class], version = 1)
abstract class PageDataBase : RoomDatabase(){
    abstract fun myDao():MyDao
    companion object {
        @Volatile
        var size = 0

        //典型的单例
        private var dbInstance: PageDataBase? = null
        fun getDataBase(context: Context): PageDataBase {
            if (dbInstance == null) {
                synchronized(PageDataBase::class.java) {
                    if (dbInstance == null) {
                        dbInstance = Room.databaseBuilder(
                            context.applicationContext,
                            PageDataBase::class.java,
                            "pagedata.db"
                        ).build()
                    }
                }
            }
            return dbInstance!!
        }
    }
}