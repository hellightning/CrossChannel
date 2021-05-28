package com.example.crossChannel

import ZoomOutPageTransformer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.crossChannel.adpters.MyPageAdapter
import com.example.crossChannel.anim.FabTransform
import com.example.crossChannel.databinding.ActivityMainBinding
import com.example.crossChannel.datas.*
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var binding: ActivityMainBinding
        var position : Int = 0
    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pageAdapter: MyPageAdapter
    private lateinit var myDao: MyDao
    lateinit var pageEntities : List<PageEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //数据库初始化
//        val db = PageDataBase.getDataBase(this)
//        myDao = db.myDao()
//        myDao.allEntity.observe(
//            this,
//            androidx.lifecycle.Observer {
//                pageEntities = it
//            }
//        )

        // 视图绑定
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 创建MainViewModel实例
        mainViewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)
        binding.toolbar.bringToFront()
        binding.btnDate.bringToFront()
        pageAdapter = MyPageAdapter(this)
        binding.pager.adapter = pageAdapter
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        binding.pager.currentItem = MyPageAdapter.pageNum - 1
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_drawler)
        binding.btnDate.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText("Choose Date")
            builder.setSelection(Date().time)
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener {
                binding.btnDate.text = SimpleDateFormat("yyyy-MMM-dd-E", Locale.US)
                    .format(Date(it))
            }
            picker.show(supportFragmentManager, null)
        }
        binding.fabs.setOnClickListener {
            val h = binding.fabs.height
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        /*
        binding.fabImage.setOnClickListener {
            val h = binding.fabs.height
            pageAdapter.addItem(binding.pager.currentItem, ImageItem(R.drawable.scenery))
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        binding.fabText.setOnClickListener {
            val h = binding.fabs.height
            pageAdapter.addItem(binding.pager.currentItem, TextItem("wipe"))
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }

         */
    }
    override fun onResume() {
        super.onResume()
        position = binding.pager.currentItem
    }
}