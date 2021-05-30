package com.example.crossChannel.adpters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crossChannel.MainActivity
import com.example.crossChannel.databinding.DrawerMenuBinding
import com.example.crossChannel.databinding.ImageItemBinding
import com.example.crossChannel.datas.AItem
import com.example.crossChannel.datas.PageRepository
import java.text.SimpleDateFormat
import java.util.*

class ImageViewHolder(private val binding : ImageItemBinding, var context: Context? = null)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AItem){
        if(item.uri == null){
            binding.ivTest.setImageResource(item.resId)
        }else{
//            context?:Glide.with(context).load(item.content).into(binding.root as ImageView)
            binding.ivTest.setImageURI(item.uri)
        }
        binding.timeStamp = item.timeStamp
        binding.tvNote.text = SimpleDateFormat("HH:mm")
            .format(binding.timeStamp)
        binding.ivTest.setOnLongClickListener {
                PageRepository.update(MainActivity.position, binding.timeStamp as Date)
            true
        }
        binding.btnDelete.setOnClickListener {
            PageRepository.update(MainActivity.position, binding.timeStamp as Date)
        }

    }
    companion object{
        fun from(parent:ViewGroup) : ImageViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
            return ImageViewHolder(binding, parent.context)
        }
    }
}