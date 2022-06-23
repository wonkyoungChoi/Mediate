package com.wk.mediate.present.views.register.selectInfo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.wk.mediate.R
import com.wk.mediate.data.models.BasicRegisterInfo
import com.wk.mediate.data.models.SelectRegisterInfo
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.databinding.FragmentSelectTypeBinding
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.extension.setAlphaClickable
import com.wk.mediate.present.views.register.selectInfo.school.SelectSchoolInfoFragment

class SelectTypeFragment : ViewBindingFragment<FragmentSelectTypeBinding>() {
    private var check : Boolean = false
    private var selectNum : Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            btBack.setAlphaClickable(true)
            clSelectTutee.setAlphaClickable(true)
            clSelectTutor.setAlphaClickable(true)

            tvName.text = BasicRegisterInfo.info.name

            btBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if(isEnabled) {
                            showConfirm(requireContext().getString(R.string.alert_cancel_select_info), requireContext().getString(R.string.alert_cancel_select_info_out), requireContext().getString(R.string.confirm) ,{
                                isEnabled = false
                                moveToRoot()
                                Toast.makeText(requireContext(),requireContext().getString(R.string.cancel_select_info), Toast.LENGTH_SHORT).show()
                                SelectRegisterInfo.tutorInfo = TutorInfo("", "", null, "", 0.0, 0.0, "", "", "")
                                SelectRegisterInfo.tuteeInfo = TuteeInfo("", "", "", 0.0, 0.0, "", "")
                            }, requireContext().getString(R.string.cancel), {})
                        }
                    }
                })



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
                   SelectRegisterInfo.tutorInfo.name = BasicRegisterInfo.info.name
                   SelectRegisterInfo.tutorInfo.accountId = BasicRegisterInfo.info.accountId
               } else {
                   val type = "TUTEE"
                   SelectRegisterInfo.type = type
                   SelectRegisterInfo.tuteeInfo.name = BasicRegisterInfo.info.name
                   SelectRegisterInfo.tuteeInfo.accountId = BasicRegisterInfo.info.accountId
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