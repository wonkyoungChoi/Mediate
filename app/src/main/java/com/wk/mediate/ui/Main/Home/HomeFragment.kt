package com.wk.mediate.ui.Main.Home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wk.mediate.R
import com.wk.mediate.base.RVAdapter
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.CellPopularBinding
import com.wk.mediate.databinding.CellPopularListBinding
import com.wk.mediate.databinding.CellTutoringBinding
import com.wk.mediate.databinding.FragmentHomeBinding

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {
    var popularArray = arrayListOf<PopularContentDTO>()
    var tutoringArray = arrayListOf<TutorListDTO>()
    var popularListArray = arrayListOf<PopularListDTO>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularArray.add(PopularContentDTO(R.drawable.school, "테스트"))
        popularArray.add(PopularContentDTO(R.drawable.school, "테스트"))
        popularArray.add(PopularContentDTO(R.drawable.school, "테스트"))
        initRecyclerCommunityPopular(popularArray)

        tutoringArray.add(TutorListDTO(R.drawable.ic_launcher_background, "이름", "튜터", "학교", "학년", "현재주차", "목표주차", 10, "~~%"))
        tutoringArray.add(TutorListDTO(R.drawable.ic_launcher_background, "이름", "튜터", "학교", "학년", "현재주차", "목표주차", 10, "~~%"))
        tutoringArray.add(TutorListDTO(R.drawable.ic_launcher_background, "이름", "튜터", "학교", "학년", "현재주차", "목표주차", 10, "~~%"))
        tutoringArray.add(TutorListDTO(R.drawable.ic_launcher_background, "이름", "튜터", "학교", "학년", "현재주차", "목표주차", 10, "~~%"))
        initRecyclerTutoringList(tutoringArray)

        popularListArray.add(PopularListDTO("테스트", "테스트"))
        popularListArray.add(PopularListDTO("테스트", "테스트"))
        popularListArray.add(PopularListDTO("테스트", "테스트"))
        popularListArray.add(PopularListDTO("테스트", "테스트"))
        initRecyclerCommunityPopularList(popularListArray)


        binding?.run {

        }

    }

    private fun initRecyclerTutoringList(popularListDTOArray: ArrayList<TutorListDTO>) {
        val recyclerTutoring = binding?.recyclerTutoringList
        recyclerTutoring?.run {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isVerticalScrollBarEnabled = false
            suppressLayout(true)
            adapter = object: RVAdapter<CellTutoringBinding>() {
                override fun count(): Int {
                    return 3
                }

                override fun bind(cell: CellTutoringBinding?, pos: Int) {
                    cell?.run {
                        imageProfile.setImageResource(popularListDTOArray[pos].ImageUrl)
                        textName.text = popularListDTOArray[pos].name
                        textType.text = popularListDTOArray[pos].type
                        textSchool.text = popularListDTOArray[pos].school
                        textGrade.text = popularListDTOArray[pos].grade
                        textTutoringNow.text = popularListDTOArray[pos].tutoringNow
                        textTutoringGoal.text = popularListDTOArray[pos].tutoringGoal
                        textPercentDesc.text = popularListDTOArray[pos].percentDesc


                        root.setOnClickListener {

                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerCommunityPopular(popularListDTOArray: ArrayList<PopularContentDTO>) {
        val recyclerPopular = binding?.recyclerCommunityPopular
        recyclerPopular?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerPopular?.adapter = object: RVAdapter<CellPopularBinding>() {
            override fun count(): Int {
                return popularListDTOArray.size
            }

            override fun bind(cell: CellPopularBinding?, pos: Int) {
                cell?.run {

                    imagePopular.setImageResource(popularListDTOArray[pos].imageId)
                    textPopularTitle.text = popularListDTOArray[pos].title

                    root.setOnClickListener {

                    }
                }
            }
        }
    }

    private fun initRecyclerCommunityPopularList(popularListDTOArray: ArrayList<PopularListDTO>) {
        val recyclerPopularList = binding?.recyclerCommunityList

        recyclerPopularList?.run {
            isVerticalScrollBarEnabled = false
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = object: RVAdapter<CellPopularListBinding>() {
                override fun count(): Int {
                    return popularListDTOArray.size
                }

                override fun bind(cell: CellPopularListBinding?, pos: Int) {
                    cell?.run {

                        textTitle.text = popularListDTOArray[pos].title
                        textTime.text = popularListDTOArray[pos].time

                        root.setOnClickListener {

                        }
                    }
                }
            }
        }

    }
}