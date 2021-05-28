package com.example.crossChannel.adpters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crossChannel.MainActivity
import com.example.crossChannel.databinding.TextItemBinding
import com.example.crossChannel.datas.PseudoPageDataBase
import com.example.crossChannel.datas.TextItem
import com.example.crossChannel.datas.constants
import java.text.SimpleDateFormat
import java.util.*

/*
inflate item layouts with my data
 */
class TextItemViewHolder private constructor(val binding : TextItemBinding)
    : RecyclerView.ViewHolder(binding.root){
    // TODO viewBinding
    fun bind(item: TextItem){
        //binding.tvContent.text = item.content as Editable
        binding.timeStamp = item.timeStamp
        binding.tvNote.text = SimpleDateFormat("HH:mm", Locale.CHINA)
            .format(binding.timeStamp)
        binding.tvNote.setOnLongClickListener {
            //这里使用viewpager的currentItem而不是pageAdapter的属性
            PseudoPageDataBase.update(MainActivity.position, binding.timeStamp as Date)
            true
        }
        binding.btnDelete.setOnClickListener {
            PseudoPageDataBase.update(MainActivity.position, binding.timeStamp as Date)
        }
        binding.tvContent
        if(item.content.startsWith("6")){
            binding.tvNote.setTextColor(Color.CYAN)
        }else{
            binding.tvNote.setTextColor(Color.DKGRAY)
        }
    }
    // 工厂模式，静态构造方法
    companion object{
        fun from(parent:ViewGroup) : TextItemViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TextItemBinding.inflate(layoutInflater, parent, false)
            return TextItemViewHolder(binding)
        }
    }
}