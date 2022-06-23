package com.wk.mediate.present.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class RVAdapter<BINDING: ViewBinding> : RecyclerView.Adapter<RVAdapter.ViewHolder<BINDING>>() {

    class ViewHolder<BINDING>(val binding: BINDING?): RecyclerView.ViewHolder(binding?.root!!) where BINDING: ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<BINDING> {
        val binding = createBindingInstance(LayoutInflater.from(parent.context), parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<BINDING>, position: Int) {
        bind(holder.binding, position)
    }

    override fun getItemCount(): Int {
        return count()
    }

    @Suppress("UNCHECKED_CAST")
    private fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): BINDING {
        val vbType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val vbClass = vbType as Class<BINDING>
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        return method.invoke(null, inflater, container, false) as BINDING
    }

    abstract fun count(): Int
    abstract fun bind(cell: BINDING?, pos: Int)
}