package com.example.crossChannel
import ZoomOutPageTransformer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.MutableLiveData
import com.example.crossChannel.adpters.MyPageAdapter
import com.example.crossChannel.anim.FabTransform
import com.example.crossChannel.databinding.ActivityMainBinding
import com.example.crossChannel.datas.*
import com.example.crossChannel.datas.PageEntity
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import com.example.crossChannel.adpters.MyRecyclerAdapter
import androidx.core.view.GravityCompat
import android.util.Log


// created by HYP
class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var binding: ActivityMainBinding
        var position : Int = 0
        var recyclerAdapter = MyRecyclerAdapter(kotlin.collections.mutableListOf())
    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pageAdapter: MyPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.pager.currentItem = pageAdapter.getItemCount() - 1
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_drawler)
        binding.toolbar.setNavigationOnClickListener{
            if(binding.drawer.isDrawerOpen(GravityCompat.START)){
                binding.drawer.closeDrawer(GravityCompat.START)
            }else{
                binding.drawer.openDrawer(GravityCompat.START)
            }
            Log.d("hltn","nav click!")
        }
        binding.btnDate.setOnClickListener {
            //TODO: turn page with date selected
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
        binding.fabImage.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            )
            PageRepository.update(position, ImageItem(R.drawable.scenery))
            val h = binding.fabs.height
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        binding.fabText.setOnClickListener {
            val h = binding.fabs.height
            PageRepository.update(position, TextItem("hello"))
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        binding.drawerBtns.btnNewpage.setOnClickListener {
            PageRepository.insert(PageEntity(position+1, Date(), "untitled", mutableListOf()))
            binding.pager.currentItem = position + 1
        }
        binding.drawerBtns.btnRename.setOnClickListener {

        }
        binding.drawerBtns.btnChange.setOnClickListener {

        }
        binding.drawerBtns.btnDelpage.setOnClickListener {

        }
    }
    override fun onResume() {
        super.onResume()
        position = binding.pager.currentItem
    }
}