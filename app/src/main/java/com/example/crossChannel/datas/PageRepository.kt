package com.example.crossChannel.datas

import android.content.Context
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
import kotlin.concurrent.thread

// 间接读写ROOM中的数据
// 在没有ROOM时，可以填写伪造的数据来进行测试
// TODO:将ROOM中的基本类型包装为LiveData
object PageRepository {
    // 外部只读
    var size = 0
    get() = field
    private set(value){
        field = value
    }
    lateinit var myDao: MyDao
    var Allpages : MutableList<PageEntity> = mutableListOf()
    fun insert(pageEntity: PageEntity){
        Allpages.add(pageEntity)
        size = size + 1
        thread{
            myDao.insert(pageEntity)
        }
    }
    fun initRepository(context: Context){
        //fake datas
        val ran = Random.nextInt(10)
        for(i in 0..ran){
            val items : MutableList<AItem> = mutableListOf()
            for(j in 0..Random.nextInt(15)){
                if(j%3 == 0){
                    items.add(AItem(constants.IMAGE_VIEW_TYPE, null, R.drawable.flower))
                }else{
                    items.add(AItem(constants.TEXT_VIEW_TYPE, "Note your Life", null))
                }
            }
            insert(PageEntity(i, Date(), "Untitled", items))
        }
    }
    fun update(primaryKey: Int, content: AItem){
        Log.d("hltn", primaryKey.toString())
        Allpages[primaryKey].insert(content)
    }
    fun update(primaryKey: Int, date: Date){
        Allpages[primaryKey].delete(date)
    }
    fun update(primaryKey: Int, date: Date, content: String){
        Allpages[primaryKey].update(date, content)
        thread{
            myDao.update(Allpages[primaryKey])
        }
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
                if(Allpages.size > 0){
                    thread {
                        myDao.delete(Allpages[i])
                    }
                }
                Allpages.removeAt(i)
                break
            }
        }
        size -= 1
    }
    fun query(position: Int) : PageEntity{
        if(position >= size){
            return Allpages[size - 1]
        }
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