package com.example.crossChannel.datas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*
import com.example.crossChannel.MainActivity

@Entity(tableName = "pageEntities")
@TypeConverters(ItemsConverter::class)
class PageEntity(
    @PrimaryKey(autoGenerate = true)
    val pseudoKey: Int,
    val date : Date,
    var title : String,
    val items : MutableList<AItem>
){
    fun update(date: Date, content: String){
        for(item in items){
            if(item.timeStamp == date && item.type == constants.TEXT_VIEW_TYPE){
                (item).content = content
                break
            }
        }
    }
    fun delete(position: Int){
        items.removeAt(position)
        MainActivity.recyclerAdapter.notifyItemRemoved(position)
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
        MainActivity.recyclerAdapter.notifyItemInserted(items.size - 1)
    }
}