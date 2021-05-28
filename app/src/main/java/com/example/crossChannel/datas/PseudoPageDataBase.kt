package com.example.crossChannel.datas

import com.example.crossChannel.R
import java.util.*
import kotlin.random.Random


// 这个实体用来在没有涉及ROOM时模拟存取和读入
// 在实际中并不会发挥作用
object PseudoPageDataBase {
    var size = 0
    private val Allpages : MutableList<PageEntity> = mutableListOf()
    fun insert(pageEntity: PageEntity){
        Allpages.add(pageEntity)
        size += 1
    }
    init{
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
    fun update(position: Int, primaryKey: Int, content: AItem){
        Allpages[primaryKey].update(position, content)
    }
    fun delete(primaryKey: Int){
        for(i in 0..Allpages.size-1){
            if(Allpages[i].pseudoKey == primaryKey){
                Allpages.removeAt(i)
            }
        }
        size -= 1
    }
    fun query(position: Int) : PageEntity{
        return Allpages[position]
    }
}