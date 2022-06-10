package com.wk.mediate.ui.Register.SelectInfo.Area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wk.mediate.databinding.SigunguItemBinding
import java.util.*

class SelectAreaAdapter : RecyclerView.Adapter<SelectAreaAdapter.SiGunGuViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var items = ArrayList<AreaItem>()

    private var baseUrl = "https://www.mjc.ac.kr/bbs/data/view.do?menu_idx=66&memberAuth=Y"


    inner class SiGunGuViewHolder(private val binding: SigunguItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(area: AreaItem){

            binding.tvArea.text = area.siGunGu
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiGunGuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SigunguItemBinding.inflate(layoutInflater, parent, false)
        return SiGunGuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SiGunGuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(it: List<AreaItem>) {
        items.clear()
        items.addAll(it)
    }
}

