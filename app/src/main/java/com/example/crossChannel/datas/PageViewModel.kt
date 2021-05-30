package com.example.crossChannel.datas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crossChannel.MainActivity
import java.util.*
import com.example.crossChannel.adpters.MyRecyclerAdapter

class PageViewModel: ViewModel(){
    lateinit var date : Date
    var title : String = "Untitled"
    lateinit var adapter : MyRecyclerAdapter
    fun initPageVM(position: Int){
        //TODO(get data from ROOM)
        if(PageRepository.size != 0){
            date = PageRepository.query(position).date
            title = PageRepository.query(position).title
            adapter = MyRecyclerAdapter(position)
        }
    }
}