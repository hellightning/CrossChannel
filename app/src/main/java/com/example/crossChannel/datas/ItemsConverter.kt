package com.example.crossChannel.datas

import android.icu.text.SimpleDateFormat
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ItemsConverter {
    var format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
    @TypeConverter
    fun toItems(json : String) : MutableList<AItem>{
        val itemsType = object : TypeToken<MutableList<AItem>>(){}.type
        return Gson().fromJson(
            json,
            itemsType
        )
    }
    @TypeConverter
    fun fromItems(items :List<AItem>) : String{
        return Gson().toJson(items)
    }
    @TypeConverter
    fun toDate(dateString :String): Date {
        return format.parse(dateString)
    }
    @TypeConverter
    fun fromDate(date : Date) : String{
        return format.format(date)
    }
}