package com.example.crossChannel.datas

import java.util.*

interface IItem
abstract class AItem: IItem{
    val timeStamp : Date = Date()
}

data class TextItem(val content: String):AItem()

data class ImageItem(val imageId: Int):AItem()

data class musicItem(val musicId: Int):AItem()