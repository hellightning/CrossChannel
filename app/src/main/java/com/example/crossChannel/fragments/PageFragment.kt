package com.example.crossChannel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crossChannel.MainActivity
import com.example.crossChannel.adpters.MyPageAdapter
import com.example.crossChannel.adpters.MyRecyclerAdapter
import com.example.crossChannel.databinding.FragmentPageBinding
import com.example.crossChannel.datas.PageViewModel
import java.text.SimpleDateFormat
import java.util.*

class PageFragment(position: Int) : Fragment() {
    private lateinit var binding: FragmentPageBinding
    lateinit var fragmentPageViewModel : PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //创建viewmodel
        fragmentPageViewModel = ViewModelProvider(this)
            .get(PageViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        Log.d("hltn", "starting fragment")
        super.onStart()
        // 这里可以改用Gird或StaggeredGird进行多样化，看开发状况
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        val adapter = MyRecyclerAdapter(fragmentPageViewModel.items)
        binding.recycler.adapter = fragmentPageViewModel.adapter
        binding.recycler.isNestedScrollingEnabled = true
        binding.recycler.itemAnimator
        MainActivity.binding.toolbar.title = fragmentPageViewModel.title
        MainActivity.binding.btnDate.text = SimpleDateFormat("yyyy-MMM-dd-E", Locale.US)
            .format(fragmentPageViewModel.date)
    }

}