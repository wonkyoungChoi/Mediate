package com.wk.mediate.present.views.register.selectInfo.subject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.mediate.data.models.BasicRegisterInfo
import com.wk.mediate.data.models.SelectRegisterInfo
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.databinding.CellSubjectBinding
import com.wk.mediate.databinding.FragmentSelectSubjectBinding
import com.wk.mediate.present.config.LoadingProgress
import com.wk.mediate.present.config.RVAdapter
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.extension.setAlphaClickable
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.present.views.register.selectInfo.RegisterSelectFinishFragment
import com.wk.mediate.present.views.register.selectInfo.SelectRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSubjectFragment : ViewBindingFragment<FragmentSelectSubjectBinding>() {
    private val subjectModel : SelectSubjectViewModel by viewModels()
    private val viewModel : SelectRegisterViewModel by viewModels()

    private var selectArray: ArrayList<String> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                viewModel.getLivedataSelectTutorRegisterResult(SelectRegisterInfo.tutorInfo)
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
                                SelectRegisterInfo.tutorInfo = TutorInfo("", "", null, "", 0.0, 0.0, "", "", "")
                                SelectRegisterInfo.tuteeInfo = TuteeInfo("", "", "", 0.0, 0.0, "", "")
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
        viewModel.registerResult.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    LoadingProgress.shared.dismiss()
                    binding?.run {
                        when (dataHandler.data?.body) {
                            "회원가입이 완료되었습니다." -> {
                                Toast.makeText(context, dataHandler.data.body, Toast.LENGTH_SHORT)
                                    .show()
                                val fragment = RegisterSelectFinishFragment()
                                replaceFragment(fragment)
                            }
                            else -> {
                                Toast.makeText(context, dataHandler.data?.body, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                    }
                }
                is DataHandler.ERROR -> {
                    LoadingProgress.shared.dismiss()
                }
                is DataHandler.LOADING -> {
                    LoadingProgress.shared.show(requireActivity())
                }
                is DataHandler.FAIL -> {
                    //TODO FAIL 처리
                }
            }

        }
    }


}