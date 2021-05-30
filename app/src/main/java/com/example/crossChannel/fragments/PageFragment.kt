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
import com.example.crossChannel.adpters.MyRecyclerAdapter
import com.example.crossChannel.databinding.FragmentPageBinding
import com.example.crossChannel.datas.PageRepository
import com.example.crossChannel.datas.PageViewModel
import java.text.SimpleDateFormat
import java.util.*

class PageFragment(val position: Int) : Fragment() {
    private lateinit var binding: FragmentPageBinding
    lateinit var fragmentPageViewModel : PageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //创建viewmodel
        fragmentPageViewModel = ViewModelProvider(this)
            .get(PageViewModel::class.java)
        //初始化ViewModel
        fragmentPageViewModel.initPageVM(position)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //用数据绑定代替视图绑定？ update：没有需求

//        binding = DataBindingUtil.inflate<PageFragmentBinding>(
//            inflater,
//            R.layout.fragment_page,
//            false
//        )
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        Log.d("hltn", "starting..."+fragmentPageViewModel.adapter.toString())
        Log.d("hltn","MAIN:"+MainActivity.recyclerAdapter.toString())
        // 这里可以改用Gird或StaggeredGird进行多样化，看开发状况
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        val adapter = MyRecyclerAdapter(position)
        binding.recycler.adapter = fragmentPageViewModel.adapter
        binding.recycler.isNestedScrollingEnabled = true
        binding.recycler.itemAnimator

        MainActivity.binding.btnDate.text = SimpleDateFormat("yyyy-MMM-dd-E", Locale.US)
            .format(fragmentPageViewModel.date)
    }

    override fun onResume() {
        super.onResume()

        if(MainActivity.recyclerAdapter != fragmentPageViewModel.adapter){
            MainActivity.recyclerAdapter = fragmentPageViewModel.adapter
        }
        Log.d("hltn","resuming..."+fragmentPageViewModel.adapter.toString())
        Log.d("hltn","MAIN:"+MainActivity.recyclerAdapter.toString())
        //TODO: use LiveDate to simple the logic
    }

    override fun onStop() {
        super.onStop()
        Log.d("hltn", "stopping..." + fragmentPageViewModel.adapter.toString())
        Log.d("hltn","MAIN:"+MainActivity.recyclerAdapter.toString())
    }
}