package com.example.crossChannel.datas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crossChannel.MainActivity
import java.util.*
import com.example.crossChannel.adpters.MyRecyclerAdapter

class PageViewModel: ViewModel(){
    lateinit var date : Date
    lateinit var items : MutableList<AItem>
    var title : String = "Untitled"
    lateinit var adapter : MyRecyclerAdapter
    init {
        //TODO(get data from ROOM)
        date = PageRepository.query(MainActivity.position).date
        title = PageRepository.query(MainActivity.position).title
        items = PageRepository.query(MainActivity.position).items
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