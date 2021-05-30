package com.example.crossChannel.datas

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import java.util.*

interface IItem
class AItem constructor(_type: Int, _content: String?, _resId: Int?, _uri: Uri? = null): IItem{
    val timeStamp : Date = Date()
    var type : Int = _type
    var content : String = _content?:"NONE"
    val resId : Int = _resId?:0
    val uri :Uri? = _uri
}
//本来这里想用多态，但抽象类被存进ROOM会产生很多不必要的麻烦
//data class TextItem(var content: String):AItem()
//
//data class ImageItem(val imageId: Int):AItem()
//
//data class musicItem(val musicId: Int):AItem()