package com.example.crossChannel.datas

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "pageEntities")
@TypeConverters(ItemsConverter::class)
class PageEntity(
    @PrimaryKey(autoGenerate = true)
    val pseudoKey: Int,
    val date : Date,
    var title : String,
    val items : MutableList<AItem>
){
    fun update(position: Int, content: AItem){

    }
    fun delete(position: Int){
        items.removeAt(position)
        //TODO notify
    }
    fun delete(date : Date){
        for(i in 0..items.size-1){
            if(items[i].timeStamp == date){
                delete(i)
                break
            }
        }
    }
    fun insert(content: AItem){
        items.add(content)
    }
}