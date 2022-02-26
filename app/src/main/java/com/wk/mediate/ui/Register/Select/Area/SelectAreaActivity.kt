package com.wk.mediate.ui.Register.Select

import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivityInputAreaBinding
import com.wk.mediate.ui.Register.Select.Area.SelectAreaAdapter
import com.wk.mediate.ui.Register.Select.Area.SelectAreaViewModel


class SelectAreaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputAreaBinding
    private lateinit var model : SelectAreaViewModel
    private lateinit var adapter: SelectAreaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SelectAreaViewModel::class.java)

        model.loadDo("*00000000")
        observeDoResult()
        initTabLayout()

        observeSiGunGuResult()
        initSiGunGuRecyclerView()
    }

    private fun addTab(text: String) {
        binding.tabLayoutArea.addTab(binding.tabLayoutArea.newTab().setText(text))
    }

    private fun observeDoResult() {
        model.getDoResult().observe(this, {
            Log.d("Observe", "Obeserve")
            for (item in it) {
                addTab(item)
                Log.d("Item", item)
            }
        })
    }

    private fun initTabLayout() {
        binding.tabLayoutArea.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                changeSelectedTabItemFontFamily(tab!!.position, NORMAL, R.color.white)

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("click", tab!!.position.toString())
                changeSelectedTabItemFontFamily(tab.position, BOLD, R.color.black)

                when (tab.position) {


                    0 -> {
                        Log.d("Click", "Click")
                        model.loadSiGunGu("11*000000")
                    }
//                    1 -> {
//                        tab.text = "학사공지"
//                    }
//                    2 -> {
//                        tab.text = "장학공지"
//                    }
//                    3 -> {
//                        tab.text = "채용공지"
//                    }
                }

//                pageIndex = 1
//                noticeAdapter.resetList(menu_idx)
//                isListEmpty = true
//                noticeAdapter.notifyDataSetChanged()
//                model.loadNotice(pageIndex, bbs_mst_idx, menu_idx)
            }
        })
    }

    private fun changeSelectedTabItemFontFamily(tabPosition: Int, @FontRes fontFamilyRes: Int, @ColorRes textColor: Int) {
        val linearLayout = (this.binding.tabLayoutArea.getChildAt(0) as ViewGroup).getChildAt(
            tabPosition
        ) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView
        val typeface = Typeface.defaultFromStyle(fontFamilyRes)
        tabTextView.setTextColor(ContextCompat.getColor(this, textColor))
        tabTextView.typeface = typeface
    }

    private fun initSiGunGuRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SelectAreaAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun observeSiGunGuResult() {
        model.getSiGunGuResult().observe(this, {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}
