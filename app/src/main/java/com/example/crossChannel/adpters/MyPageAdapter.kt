package com.example.crossChannel.adpters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.crossChannel.datas.*
import com.example.crossChannel.fragments.PageFragment
import java.util.*

class MyPageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    companion object{
        //data of a page
        lateinit var date : Date
        var data : MutableList<AItem> = mutableListOf()
        var title : String = "Untitled"
        var adapter = MyRecyclerAdapter(data)
    }
    init{
        //TODO(init first PAGE and store)
    }
    override fun getItemCount(): Int {
        return PageRepository.size
    }
    override fun createFragment(position: Int): Fragment {
        return PageFragment(position)
    }
}