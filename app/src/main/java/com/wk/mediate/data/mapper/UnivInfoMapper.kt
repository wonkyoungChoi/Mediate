package com.wk.mediate.data.mapper

import android.util.Log
import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.present.views.register.selectInfo.school.search.SearchUnivResult

object UnivInfoMapper {
    operator fun invoke(searchSchoolResult: SearchUnivResult): SearchEntity {
        val items = ArrayList<String>()
        val list: SearchUnivResult = searchSchoolResult

        try {
            if (list.dataSearch?.content?.get(0)  != null) {
                list.dataSearch!!.content?.forEachIndexed { index, s ->
                    if (index < 10) {
                        items.add(s.schoolName.toString())
                    }
                }
            }
        } catch (e: NullPointerException) {
            Log.d("NullCheck", "NullCheck")
        } catch (e: IndexOutOfBoundsException) {
            items.add("검색한 학교가 없습니다.")
        }

        return SearchEntity(items.distinct())
    }
}