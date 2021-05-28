package com.example.crossChannel.adpters

import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.crossChannel.MainActivity
import com.example.crossChannel.databinding.TextItemBinding
import com.example.crossChannel.datas.PageRepository
import com.example.crossChannel.datas.TextItem
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import com.example.crossChannel.R
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
        binding.tvContent.setText(
            item.content,
            TextView.BufferType.EDITABLE)
        binding.tvNote.text = SimpleDateFormat("HH:mm")
            .format(binding.timeStamp)
        binding.tvContent.setTextColor(R.attr.backgroundColor)
        binding.tvNote.setOnLongClickListener {
            //这里使用viewpager的currentItem而不是pageAdapter的属性
            PageRepository.update(MainActivity.position, binding.timeStamp as Date)
            true
        }
        binding.btnDelete.setOnClickListener {
            PageRepository.update(MainActivity.position, binding.timeStamp as Date)
        }
//        item.content.observe(
//            binding.tvContent,
//            Observer {
//                PageRepository.update(MainActivity.position, binding.timeStamp, binding.tvContent.text as String)
//            }
//        )
        binding.tvContent.addTextChangedListener {
            binding.tvContent.setTextColor(R.attr.badgeTextColor)
            PageRepository.update(MainActivity.position, binding.timeStamp as Date, binding.tvContent.text.toString())
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