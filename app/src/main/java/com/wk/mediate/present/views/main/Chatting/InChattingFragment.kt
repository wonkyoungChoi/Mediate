package com.wk.mediate.present.views.main.Chatting

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wk.mediate.present.config.RVAdapter
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.databinding.CellChattingBinding
import com.wk.mediate.databinding.FragmentInChattingBinding
import com.wk.mediate.domain.models.Chatting

class InChattingFragment : ViewBindingFragment<FragmentInChattingBinding>() {

    var chattingListArray = arrayListOf<Chatting>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chattingListArray.add(Chatting("테스트", "테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트", "11111111"))
        initRecyclerChatting(chattingListArray)
    }

    private fun initRecyclerChatting(items: ArrayList<Chatting>) {
        binding?.run {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.isVerticalScrollBarEnabled = false
            recyclerView.suppressLayout(true)
            recyclerView.adapter = object: RVAdapter<CellChattingBinding>() {
                override fun count(): Int {
                    return items.size
                }

                override fun bind(cell: CellChattingBinding?, pos: Int) {
                    cell?.run {
                        //imageProfile.setImageURI(Uri.parse(items[pos].profile_url))
                        tvOtherChatting.text = items[pos].text
                        tvOtherTimestamp.text = items[pos].time

                        root.setOnClickListener {

                        }
                    }
                }
            }
        }

    }
}