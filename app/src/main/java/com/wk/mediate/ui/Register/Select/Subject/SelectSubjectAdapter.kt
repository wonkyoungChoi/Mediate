package com.wk.mediate.ui.Register.Select.Subject

import android.R
import android.content.Context
import android.provider.Contacts.People
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView


class SelectSubjectAdapter(
        context: Context,
        resource: Int
) : ArrayAdapter<String>(context, resource), Filterable {

    private var result: MutableList<String> = mutableListOf()

    fun setResult(list: List<String>) {
        result.clear()
        result.addAll(list)
    }

    override fun getCount(): Int {
        return result.size
    }

    override fun getItem(position: Int): String? {
        return result[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    filterResults.apply {
                        values = result
                        count = result.size
                    }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}