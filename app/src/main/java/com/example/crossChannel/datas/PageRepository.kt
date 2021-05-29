package com.example.crossChannel.datas

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.crossChannel.R
import java.util.*
import kotlin.random.Random
import com.example.crossChannel.datas.MyDao
import com.example.crossChannel.datas.PageDataBase
import com.example.crossChannel.MainActivity
import java.text.SimpleDateFormat

// 间接读写ROOM中的数据
// 在没有ROOM时，可以填写伪造的数据来进行测试
// TODO:将ROOM中的基本类型包装为LiveData
object PageRepository {
    var size = 0
    private lateinit var myDao: MyDao
    lateinit var pageEntities : List<PageEntity>
    private val Allpages : MutableList<PageEntity> = mutableListOf()

    fun insert(pageEntity: PageEntity){
        Allpages.add(pageEntity)
        size += 1
    }
    init{
//        val db = PageDataBase.getDataBase()
//        myDao = db.myDao()
//        myDao.allEntity.observe(
//            this,
//            androidx.lifecycle.Observer {
//                pageEntities = it
//            }
//        )
        //fake datas
        val ran = Random.nextInt(10)
        size = ran
        for(i in 0..ran){
            val items : MutableList<AItem> = mutableListOf()
            for(j in 0..Random.nextInt(15)){
                if(j%3 == 0){
                    items.add(ImageItem(R.drawable.scenery))
                }else{
                    items.add(TextItem("ROOM"))
                }
            }
            Allpages.add(PageEntity(i, Date(), "Untitled", items))
        }
    }
    fun update(primaryKey: Int, content: AItem){
        Allpages[primaryKey].insert(content)
    }
    fun update(primaryKey: Int, date: Date){
        Allpages[primaryKey].delete(date)
    }
    fun update(primaryKey: Int, date: Date, content: String){
        Allpages[primaryKey].update(date, content)
        Log.d("hltn", "updating...")
    }
    fun update(primaryKey: Int, content: String){
        Log.d("hltn", Allpages[primaryKey].title)
        Allpages[primaryKey].title = content
        Log.d("hltn", Allpages[primaryKey].title)
    }
    fun delete(primaryKey: Int){
        for(i in 0..MainActivity.position){
            if(Allpages[i].pseudoKey == primaryKey){
                Allpages.removeAt(i)
            }
        }
        size -= 1
    }
    fun query(position: Int) : PageEntity{
        return Allpages[position]
    }
    fun findPageWithDate(date : Date): Int?{
        val ft = SimpleDateFormat("yyyy-MM-dd")
        for(i in 0..MainActivity.position){
            if(ft.format(date) == ft.format(Allpages[i].date)){
                return i
            }
        }
        return null
    }
}