package com.example.crossChannel
import ZoomOutPageTransformer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import com.example.crossChannel.adpters.MyPageAdapter
import com.example.crossChannel.anim.FabTransform
import com.example.crossChannel.databinding.ActivityMainBinding
import com.example.crossChannel.databinding.DrawerMenuBinding
import com.example.crossChannel.datas.*
import com.example.crossChannel.datas.PageEntity
import com.example.crossChannel.datas.MyDao
import com.example.crossChannel.datas.PageDataBase
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.lang.reflect.Field
import java.util.*
import com.example.crossChannel.adpters.MyRecyclerAdapter
import androidx.core.view.GravityCompat
import android.util.Log
import android.net.Uri
import android.os.FileUtils
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog

// created by HYP
class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var binding: ActivityMainBinding
        var recyclerAdapter = MyRecyclerAdapter(0)
        var position : Int = 0
        get(){
            field = binding.pager.currentItem
            return field
        }
    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pageAdapter: MyPageAdapter
    private fun initDB(){
        val db = PageDataBase.getDataBase(this)
        PageRepository.myDao = db.myDao()
        PageRepository.myDao.allEntity.observe(
            this,
            Observer{
                PageRepository.Allpages = it
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 视图绑定
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 创建MainViewModel实例
        mainViewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        initDB()
        //测试使用 随机初始化PageRepository
        PageRepository.initRepository(this)
        binding.toolbar.bringToFront()
        binding.btnDate.bringToFront()
        pageAdapter = MyPageAdapter(this)
        binding.pager.adapter = pageAdapter
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        binding.pager.currentItem = pageAdapter.getItemCount() - 1
        // 可自定义viewpager2类以提高灵敏度，由于工期不足，不进行拓展
//        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
//        recyclerViewField.isAccessible = true
//        val recyclerView = recyclerViewField.get(this) as RecyclerView
//
//        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
//        touchSlopField.isAccessible = true
//        val touchSlop = touchSlopField.get(recyclerView) as Int
//        touchSlopField.set(recyclerView, touchSlop*0.5)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_drawler)
        binding.toolbar.setNavigationOnClickListener{
            if(binding.drawer.isDrawerOpen(GravityCompat.START)){
                binding.drawer.closeDrawer(GravityCompat.START)
            }else{
                binding.drawer.openDrawer(GravityCompat.START)
            }
        }
        // 翻到指定日期的第一页
        binding.btnDate.setOnClickListener {
            // TODO: fix bug  --> Done
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText("Choose Date You Want to Go to...")
            builder.setSelection(Date().time)
            val picker = builder.build()
            var page : Int? = 0
            var findingDate = Date()
            picker.addOnPositiveButtonClickListener {
                findingDate = Date(it)
            }
            picker.show(supportFragmentManager, null)
            page = PageRepository.findPageWithDate(findingDate)
            binding.pager.setCurrentItem(page?:binding.pager.currentItem)
        }
        binding.fabs.setOnClickListener {
            val h = binding.fabs.height
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        binding.fabImage.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                2
            )
            val h = binding.fabs.height
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        binding.fabText.setOnClickListener {
            val h = binding.fabs.height
            recyclerAdapter.notifyItemInserted(recyclerAdapter.itemCount)
            PageRepository.update(position, AItem(constants.TEXT_VIEW_TYPE, "hello", null))
            FabTransform.activateFabAnim(binding.fabText, h)
            FabTransform.activateFabAnim(binding.fabImage,h/2)
        }
        binding.btnNewpage.setOnClickListener {
            PageRepository.insert(PageEntity(MainActivity.position+1, Date(), "untitled", mutableListOf()))
            binding.pager.adapter?.notifyDataSetChanged()
            binding.pager.setCurrentItem(position+1, true)
        }
        // 暂未实现
        binding.btnRename.setOnClickListener {
            val et : EditText = EditText(this)
            var newTitle : String = "untitled"
            val builder = AlertDialog.Builder(this)
            builder.setTitle("New Title:")
            builder.setView(et)
            builder.setPositiveButton("Done", android.content.DialogInterface.OnClickListener {
                dialogInterface, i ->
                newTitle = et.text.toString()
                PageRepository.update(position, newTitle)
                // 不知道为啥，这几个都不能更新显示界面的title
                binding.toolbar.title = newTitle
                supportActionBar?.title = newTitle
                title = newTitle
            })
            builder.show()
            PageRepository.update(position, newTitle)
            binding.toolbar.title = newTitle
            supportActionBar?.title = newTitle
            title = newTitle
        }
        binding.btnChange.setOnClickListener {
            //TODO: change the theme of the app
        }
        binding.btnDelpage.setOnClickListener {
            if(position != 0){
                PageRepository.delete(position)
                binding.pager.currentItem -= 1
                binding.pager.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("hltn", "request"+requestCode.toString()+"result"+resultCode.toString())
        if(requestCode == 2 &&  resultCode == android.app.Activity.RESULT_OK){
            val uri: Uri = data?.data!!
            PageRepository.update(position, AItem(constants.IMAGE_VIEW_TYPE, "NONE", null, uri))
//            val path: String = UriUtils.getFileAbsolutePath(this, uri)!!
//            Log.d("hltn","path=="+path)
//            if(!TextUtils.isEmpty(path)){
//                Log.d("hltn", path)
//                PageRepository.update(position, AItem(constants.IMAGE_VIEW_TYPE, path, null))
//            }
        }
    }
}