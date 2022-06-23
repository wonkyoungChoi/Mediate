package com.wk.mediate.present.views.main.Chatting

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wk.mediate.present.config.RVAdapter
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.databinding.CellChattingListBinding
import com.wk.mediate.databinding.FragmentChattingBinding
import com.wk.mediate.domain.models.ChattingList

class ChattingFragment : ViewBindingFragment<FragmentChattingBinding>() {
    var chattingListArray = arrayListOf<ChattingList>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chattingListArray.add(ChattingList("테스트", "테스트", 11111111 ,"테스트"))
        chattingListArray.add(ChattingList("테스트", "테스트", 11111111 ,"테스트"))
        chattingListArray.add(ChattingList("테스트", "테스트", 11111111 ,"테스트"))
        chattingListArray.add(ChattingList("테스트", "테스트", 11111111 ,"테스트"))
        chattingListArray.add(ChattingList("테스트", "테스트", 11111111 ,"테스트"))

        initRecyclerChattingRoom(chattingListArray)
    }

    private fun initRecyclerChattingRoom(items: ArrayList<ChattingList>) {
        binding?.run {
            rvChattingRoom.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvChattingRoom.isVerticalScrollBarEnabled = false
            rvChattingRoom.suppressLayout(true)
            rvChattingRoom.adapter = object: RVAdapter<CellChattingListBinding>() {
                override fun count(): Int {
                    return items.size
                }

                override fun bind(cell: CellChattingListBinding?, pos: Int) {
                    cell?.run {
                        imageProfile.setImageURI(Uri.parse(items[pos].profile_url))
                        textTitle.text = items[pos].title
                        textLastMsg.text = items[pos].text
                        textTime.text = items[pos].time.toString()

                        root.setOnClickListener {

                        }
                    }
                }
            }
        }

    }
}