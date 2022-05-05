package com.wk.mediate.ui.Register.Select

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentSelectTypeBinding
import com.wk.mediate.ui.Register.BasicInfo.BasicRegisterInfo
import com.wk.mediate.ui.Register.Select.School.SelectSchoolInfoFragment

class SelectTypeFragment : ViewBindingFragment<FragmentSelectTypeBinding>() {
    private var check : Boolean = false
    private var selectNum : Int = 0

    private lateinit var type : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvName?.text = BasicRegisterInfo.info.name
        onClick()
    }

    private fun onClick() {
        //튜터 선택 클릭
        binding?.run {
            clSelectTutor.setOnClickListener{
                selectNum = 1
                checkClick(clSelectTutor, ivMortarboard, tvSelectTypeTutor, tvSelectTypeTutorInfo, clSelectTutee, ivSchoolbag, tvSelectTypeTutee, tvSelectTypeTuteeInfo)
            }

            //튜티 선택 클릭
            clSelectTutee.setOnClickListener{
                selectNum = 2
                checkClick(clSelectTutee, ivSchoolbag, tvSelectTypeTutee, tvSelectTypeTuteeInfo, clSelectTutor, ivMortarboard, tvSelectTypeTutor, tvSelectTypeTutorInfo)
            }

            //다음 버튼 클릭
            btNextActive.setOnClickListener {
               if(selectNum == 1) {
                   val type = "TUTOR"
                   SelectRegisterInfo.type = type
                   SelectRegisterInfo.tutorInfo.type =  type
                   SelectRegisterInfo.tutorInfo.name = BasicRegisterInfo.info.name
               } else {
                   val type = "TUTEE"
                   SelectRegisterInfo.type = type
                   SelectRegisterInfo.tuteeInfo.type = type
                   SelectRegisterInfo.tuteeInfo.name = BasicRegisterInfo.info.name
               }
                val fragment = SelectSchoolInfoFragment()
                pushFragment(fragment)
            }
        }

    }

    private fun checkClick(clBtn: ConstraintLayout,iv: ImageView, title:TextView, info: TextView, clOtherBtn: ConstraintLayout, otherIv: ImageView, otherTitle:TextView, otherInfo:TextView) {
        if(check && selectNum == 1) {
            Log.d("checkTrue1","checkTrue1")
            check = false
            clBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.mortarboard_active)
            title.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            otherIv.setImageResource(R.drawable.schoolbag_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        } else if(!check && selectNum == 1){
            Log.d("checkFalse1","checkFalse1")
            check = true
            clBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.mortarboard_active)
            title.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            otherIv.setImageResource(R.drawable.schoolbag_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        } else if(check && selectNum == 2) {
            Log.d("checkTrue2","checkTrue2")
            check = false
            clBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.schoolbag_active)
            title.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            otherIv.setImageResource(R.drawable.mortarboard_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        } else {
            Log.d("checkFalse=2","checkFalse2")
            check = true
            clBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_active)
            clOtherBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.select_type_btn_inactive)
            iv.setImageResource(R.drawable.schoolbag_active)
            title.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            info.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
            otherIv.setImageResource(R.drawable.mortarboard_inactive)
            otherTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            otherInfo.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        }
        binding?.run {
            btNextActive.visibility = View.VISIBLE
            btNextInactive.visibility = View.GONE
        }
    }
}