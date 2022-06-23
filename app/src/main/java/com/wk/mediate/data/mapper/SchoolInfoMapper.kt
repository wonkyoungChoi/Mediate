package com.wk.mediate.data.mapper

import com.wk.mediate.data.models.SearchSchoolResult
import com.wk.mediate.domain.models.SearchEntity

object SchoolInfoMapper {
    operator fun invoke(searchSchoolResult: SearchSchoolResult): SearchEntity {
        val items = ArrayList<String>()
        val list: SearchSchoolResult = searchSchoolResult

        try {
            list.schoolInfo?.get(1)?.row?.forEachIndexed { index, s ->
                if (index < 10) {
                    items.add(s.schulNm.toString())
                }
            }

        } catch (e: NullPointerException) {
            items.add("검색한 학교가 없습니다.")
        }

        return SearchEntity(items.distinct())
    }
}