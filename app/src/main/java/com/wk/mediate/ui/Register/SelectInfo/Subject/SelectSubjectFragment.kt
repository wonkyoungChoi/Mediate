package com.wk.mediate.ui.Register.SelectInfo.Subject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.mediate.base.RVAdapter
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.CellSubjectBinding
import com.wk.mediate.databinding.FragmentSelectSubjectBinding
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.ui.Register.BasicInfo.BasicRegisterInfo
import com.wk.mediate.ui.Register.BasicInfo.RegisterViewModel
import com.wk.mediate.ui.Register.SelectInfo.*

class SelectSubjectFragment : ViewBindingFragment<FragmentSelectSubjectBinding>() {
    private lateinit var subjectModel : SelectSubjectViewModel
    private lateinit var registerModel : RegisterViewModel

    private var selectArray: ArrayList<String> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subjectModel = ViewModelProvider(this).get(SelectSubjectViewModel::class.java)
        registerModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        observeSelectResigterResult()

        binding?.run {

            btNextActive.setAlphaClickable(true)
            btBack.setAlphaClickable(true)

            tvName.text = BasicRegisterInfo.info.name
            tvTutInfo.text = getString(com.wk.mediate.R.string.tutor)


            btNextActive.setOnClickListener {
                SelectRegisterInfo.tutorInfo.curriculum = selectArray
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.accountId)
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.address)
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.grade)
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.major)
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.name)
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.school)
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.curriculum!![0])
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.longitude.toString())
                Log.d("SelectInfo", SelectRegisterInfo.tutorInfo.latitude.toString())


                registerModel.loadSelectTutorRegister(SelectRegisterInfo.tutorInfo)
            }


            btBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            initRecyclerView(subjectModel.getElementArray(), recyclerElementarySubject)
            initRecyclerView(subjectModel.getMiddleArray(), recyclerMiddleSubject)
            initRecyclerView(subjectModel.getHighArray(), recyclerHighSubject)
            initRecyclerView(subjectModel.getEntranceArray(), recyclerEntranceExam)
            initRecyclerView(subjectModel.getComputerArray(), recyclerComputer)
            initRecyclerView(subjectModel.getForeignArray(), recyclerForeignLanguage)
            initRecyclerView(subjectModel.getInstrumentArray(), recyclerInstrument)
        }

        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if(isEnabled) {
                            showConfirm(requireContext().getString(com.wk.mediate.R.string.alert_cancel_select_info), requireContext().getString(com.wk.mediate.R.string.alert_cancel_select_info_out), requireContext().getString(com.wk.mediate.R.string.confirm) ,{
                                isEnabled = false
                                moveToRoot()
                                Toast.makeText(requireContext(),requireContext().getString(com.wk.mediate.R.string.cancel_select_info), Toast.LENGTH_SHORT).show()
                                SelectRegisterInfo.tutorInfo = SelectInfoTutor("", "", null, "", 0.0, 0.0, "", "", "")
                                SelectRegisterInfo.tuteeInfo = SelectInfoTutee("", "", "", 0.0, 0.0, "", "")
                            }, requireContext().getString(com.wk.mediate.R.string.cancel), {})
                        }
                    }
                })
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
                        if(selectArray.size < 8) {
                            textKeyword.isSelected = !textKeyword.isSelected
                            if(textKeyword.isSelected) {
                                binding?.run{
                                    btNextActive.visibility = View.VISIBLE
                                    btNextInactive.visibility = View.GONE
                                }
                                selectArray.add(subject)
                            } else {
                                if(selectArray.size>0) {
                                    binding?.run{
                                        btNextActive.visibility = View.VISIBLE
                                        btNextInactive.visibility = View.GONE
                                    }
                                } else {
                                    binding?.run{
                                        btNextActive.visibility = View.GONE
                                        btNextInactive.visibility = View.VISIBLE
                                    }
                                }
                                selectArray.remove(subject)
                            }
                        } else if(selectArray.size == 5 && textKeyword.isSelected) {
                            textKeyword.isSelected = !textKeyword.isSelected
                            selectArray.remove(subject)
                        }
                        else {
                            Toast.makeText(requireContext(), "8개 이상의 과목을 선택할 수 없습니다.", Toast.LENGTH_SHORT).show()
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
                val fragment = RegisterSelectFinishFragment()
                replaceFragment(fragment)
            }
        })
    }


}