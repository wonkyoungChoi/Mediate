package com.wk.mediate.ui.Register.Select.Subject

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.wk.mediate.base.RVAdapter
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.CellSubjectBinding
import com.wk.mediate.databinding.FragmentSelectSubjectBinding
import com.wk.mediate.extensions.afterTextChanged
import com.wk.mediate.ui.Login.LoginFragment
import com.wk.mediate.ui.Register.BasicInfo.RegisterViewModel
import com.wk.mediate.ui.Register.Select.SelectRegisterInfo

class SelectSubjectFragment : ViewBindingFragment<FragmentSelectSubjectBinding>() {
    private lateinit var subjectModel : SelectSubjectViewModel
    private lateinit var registerModel : RegisterViewModel

    private var selectArray: ArrayList<String> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subjectModel = ViewModelProvider(this).get(SelectSubjectViewModel::class.java)
        registerModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding?.run {
            if(SelectRegisterInfo.type == "TUTOR") {
                tvName.text = SelectRegisterInfo.tutorInfo.name
            } else {
                tvName.text = SelectRegisterInfo.tuteeInfo.name
            }

            btNextActive.setOnClickListener {
                if(SelectRegisterInfo.type == "TUTOR") {
                    registerModel.loadSelectTutorRegister(SelectRegisterInfo.tutorInfo)
                } else {
                    registerModel.loadSelectTuteeRegister(SelectRegisterInfo.tuteeInfo)
                }

            }

            initRecyclerView(subjectModel.getElementArray(), recyclerElementarySubject)
            initRecyclerView(subjectModel.getMiddleArray(), recyclerMiddleSubject)
            initRecyclerView(subjectModel.getHighArray(), recyclerHighSubject)
            initRecyclerView(subjectModel.getEntranceArray(), recyclerEntranceExam)
            initRecyclerView(subjectModel.getComputerArray(), recyclerComputer)
            initRecyclerView(subjectModel.getForeignArray(), recyclerForeignLanguage)
            initRecyclerView(subjectModel.getInstrumentArray(), recyclerInstrument)
        }



        observeSelectResigterResult()
    }

    private fun initRecyclerView(array: Array<String>, recyclerView: RecyclerView) {

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = object: RVAdapter<CellSubjectBinding>() {
            override fun count(): Int {
                return array.size
            }

            override fun bind(cell: CellSubjectBinding?, pos: Int) {
                val subject = array[pos]
                cell?.run {
                    textKeyword.text = subject
                    textKeyword.setOnClickListener {
                        if(selectArray.size < 5) {
                            textKeyword.isSelected = !textKeyword.isSelected
                            if(textKeyword.isSelected) {
                                selectArray.add(subject)
                            } else {
                                selectArray.remove(subject)
                            }
                        } else if(selectArray.size == 5 && textKeyword.isSelected) {
                            textKeyword.isSelected = !textKeyword.isSelected
                            selectArray.remove(subject)
                        }
                        else {
                            Toast.makeText(requireContext(), "5개 이상의 과목을 선택할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        }

                        notifyDataSetChanged()
                    }

                }
            }
        }

    }

    //회원가입 관련 LiveData 관찰
    private fun observeSelectResigterResult() {
        registerModel.getSelectRegisterResult().observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if(it.contains("회원가입이 완료되었습니다.")) {
                val fragment = LoginFragment()
                replaceFragment(fragment)
            }
        })
    }


}