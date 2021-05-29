package com.example.crossChannel.adpters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crossChannel.MainActivity
import com.example.crossChannel.databinding.ImageItemBinding
import com.example.crossChannel.datas.ImageItem
import com.example.crossChannel.datas.PageRepository
import java.text.SimpleDateFormat
import java.util.*

class ImageViewHolder(private val binding : ImageItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ImageItem){
        binding.ivTest.setImageResource(item.imageId)
        binding.timeStamp = item.timeStamp
        binding.tvNote.text = SimpleDateFormat("HH:mm")
            .format(binding.timeStamp)
        binding.ivTest.setOnLongClickListener {
                PageRepository.update(MainActivity.position, binding.timeStamp as Date)
            true
        }
        binding.btnDelete.setOnClickListener {
            Log.d("hltn","image delete clicked")
            PageRepository.update(MainActivity.position, binding.timeStamp as Date)
        }
    }
    companion object{
        fun from(parent:ViewGroup) : ImageViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
            return ImageViewHolder(binding)
        }
    }
}