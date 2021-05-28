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
    companion object{
        @Volatile
        var size = 0
        //典型的单例
        private var dbInstance: PageDataBase ?= null
        fun getDataBase(context : Context) : PageDataBase{
            if(dbInstance == null){
                synchronized(PageDataBase::class.java){
                    if(dbInstance == null){
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
        fun insert(pageEntity: PageEntity){
//            PseudoPageDataBase.Allpages.add(pageEntity)
            size += 1
        }
        init{
            val ran = Random.nextInt(10)
            size = ran
            for(i in 0..ran){
                val items : MutableList<AItem> = mutableListOf()
                for(j in 0..Random.nextInt(15)){
                    if(j%3 == 0){
                        items.add(ImageItem(R.drawable.scenery))
                    }else{
                        items.add(TextItem("ROOM"))
                    }
                }
//                PseudoPageDataBase.Allpages.add(PageEntity(Date(), "Untitled", items, i))
            }
        }
        fun update(primaryKey: Int, content: AItem){
//            PseudoPageDataBase.Allpages[primaryKey].insert(content)
        }
        fun update(primaryKey: Int, date: Date){
//            PseudoPageDataBase.Allpages[primaryKey].delete(date)
        }
        fun update(position: Int, primaryKey: Int, content: AItem){
//            PseudoPageDataBase.Allpages[primaryKey].update(position, content)
        }
        fun delete(primaryKey: Int){
//            for(i in 0..PseudoPageDataBase.Allpages.size-1){
//                if(PseudoPageDataBase.Allpages[i].pseudoKey == primaryKey){
//                    PseudoPageDataBase.Allpages.removeAt(i)
//                }
//            }
            size -= 1
        }
        fun query(position: Int) : PageEntity{
//            return PseudoPageDataBase.Allpages[position]
            return  PageEntity(position, Date(), "Untitled", mutableListOf<AItem>())
        }
    }

}