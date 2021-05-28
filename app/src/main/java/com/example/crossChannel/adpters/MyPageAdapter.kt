package com.example.crossChannel.adpters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.crossChannel.MainActivity
import com.example.crossChannel.datas.*
import com.example.crossChannel.fragments.PageFragment
import java.util.*
import kotlin.random.Random

class MyPageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    companion object{
        //data of a page
        var pageNum = 0
        lateinit var date : Date
        var data : MutableList<AItem> = mutableListOf()
        var title : String = "Untitled"
        var adapter = MyRecyclerAdapter(data)
    }
    init{
        //TODO(init first PAGE and store)
    }
    override fun getItemCount(): Int {
        //TODO(get size of ROOM)
        return PseudoPageDataBase.size
    }
    override fun createFragment(position: Int): Fragment {
        return PageFragment(position)
    }
}