package com.wk.mediate.ui.Register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivitySelectTypeBinding

class SelectTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectTypeBinding
    private var check : Boolean = false
    private var selectNum : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name")

        selectClick()

        binding.btNextActive.setOnClickListener {
            val intent = Intent(this, InputCodeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun selectClick() {
        binding.clSelectTutor.setOnClickListener{
            Log.d("ClickTutor", "ClickTutor")
            selectNum = 1
            checkClick(binding.clSelectTutor, binding.ivMortarboard, binding.tvSelectTypeTutor, binding.tvSelectTypeTutorInfo, binding.clSelectTutee, binding.ivSchoolbag, binding.tvSelectTypeTutee, binding.tvSelectTypeTuteeInfo)
        }

        binding.clSelectTutee.setOnClickListener{
            Log.d("ClickTutee", "ClickTutee")
            selectNum = 2
            checkClick(binding.clSelectTutee, binding.ivSchoolbag, binding.tvSelectTypeTutee, binding.tvSelectTypeTuteeInfo, binding.clSelectTutor, binding.ivMortarboard, binding.tvSelectTypeTutor, binding.tvSelectTypeTutorInfo)
        }
    }

    private fun checkClick(clBtn: ConstraintLayout,iv: ImageView, title:TextView, info: TextView, clOtherBtn: ConstraintLayout, otherIv: ImageView, otherTitle:TextView, otherInfo:TextView) {
        if(check && selectNum == 1) {
            Log.d("checkTrue1","checkTrue1")
            check = false
            clBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.mortarboard_active)
            title.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            otherIv.setImageResource(R.drawable.schoolbag_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_grey))
        } else if(!check && selectNum == 1){
            Log.d("checkFalse1","checkFalse1")
            check = true
            clBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.mortarboard_active)
            title.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            otherIv.setImageResource(R.drawable.schoolbag_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_grey))
        } else if(check && selectNum == 2) {
            Log.d("checkTrue2","checkTrue2")
            check = false
            clBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.schoolbag_active)
            title.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            otherIv.setImageResource(R.drawable.mortarboard_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_grey))
        } else {
            Log.d("checkFalse=2","checkFalse2")
            check = true
            clBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(applicationContext, R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.schoolbag_active)
            title.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
            otherIv.setImageResource(R.drawable.mortarboard_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_grey))
        }
        binding.btNextActive.visibility = View.VISIBLE
        binding.btNextInactive.visibility = View.GONE
    }
}