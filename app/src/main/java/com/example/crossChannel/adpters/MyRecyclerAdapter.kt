package com.example.crossChannel.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crossChannel.R
import com.example.crossChannel.datas.*

class MyRecyclerAdapter(val pagePosition : Int)
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
        return PageRepository.Allpages[pagePosition].items.size
    }

    override fun getItemViewType(position: Int): Int {
        return PageRepository.Allpages[pagePosition].items[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {
        val item = PageRepository.Allpages[pagePosition].items[position]
        if(holder is TextItemViewHolder && item.type == constants.TEXT_VIEW_TYPE){
            holder.bind(item)
        }else if (holder is ImageViewHolder && item.type == constants.IMAGE_VIEW_TYPE){
            holder.bind(item)
        }
    }
}