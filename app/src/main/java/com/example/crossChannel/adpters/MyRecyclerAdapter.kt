package com.example.crossChannel.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crossChannel.R
import com.example.crossChannel.datas.*

class MyRecyclerAdapter(private val data: List<AItem>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val textVH = TextItemViewHolder.from(parent)
        val imageVH = ImageViewHolder.from(parent)
        return when(viewType){
            constants.IMAGE_VIEW_TYPE -> imageVH
            constants.TEXT_VIEW_TYPE -> textVH
            else -> textVH
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        return when(item){
            is TextItem -> constants.TEXT_VIEW_TYPE
            is ImageItem -> constants.IMAGE_VIEW_TYPE
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {
        val item = data[position]
        if(holder is TextItemViewHolder && item is TextItem){
            holder.bind(item)
        }else if (holder is ImageViewHolder && item is ImageItem){
            holder.bind(item)
        }
    }
}