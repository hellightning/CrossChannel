package com.example.crossChannel.datas

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.crossChannel.MainActivity
import java.util.*
import kotlin.random.Random
import com.example.crossChannel.R
import com.example.crossChannel.adpters.MyRecyclerAdapter

class PageViewModel: ViewModel(){
    lateinit var date : Date
    lateinit var items : MutableList<AItem>
    var title : String = "Untitled"
    lateinit var adapter : MyRecyclerAdapter
    init {
        //TODO(get data from ROOM)
        date = PseudoPageDataBase.query(MainActivity.position).date
        title = PseudoPageDataBase.query(MainActivity.position).title
        items = PseudoPageDataBase.query(MainActivity.position).items
        adapter = MyRecyclerAdapter(items)
    }
    fun addData(type: Int, item: AItem){
        items.add(
            when(item){
                is TextItem -> item
                else -> item
            }
        )
        adapter.notifyItemInserted(items.size)
    }
    fun removeData(position: Int){
        items.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    fun removeData(note: String){
        val x = items.indexOf(TextItem(note))
        removeData(x)
    }
    fun removeData(date :Date){
        var x = 0
        for(x in 0..items.size-1){
            if(items[x].timeStamp == date){
                removeData(x)
                break
            }
        }
    }
}