package com.wk.mediate.data.mapper

import com.wk.mediate.data.models.SearchAreaResult
import com.wk.mediate.domain.models.SearchAreaEntity

object AreaInfoMapper {
    operator fun invoke(searchAreaResult: SearchAreaResult): SearchAreaEntity {
        val doItems = ArrayList<String>()
        val siGunGuItems = ArrayList<String>()
        val dongItems = ArrayList<String>()
        val list: SearchAreaResult = searchAreaResult

        for(i in list.regcodes.indices) {
            //도 정보
            doItems.add(reName(list.regcodes[i].name))

            //시군구 정보
            if(i == 0) {
                siGunGuItems.add(reName(list.regcodes[i].name) + " 전체")
            } else siGunGuItems.add(list.regcodes[i].name.replace(list.regcodes[0].name, "").trim())

            //동 정보
            dongItems.add(list.regcodes[i].name.trim().replace(list.regcodes[0].name, ""))
        }

        return SearchAreaEntity(doItems, siGunGuItems, dongItems)
    }

    private fun reName(name: String): String {
        return if(name.contains("북") || name.contains("남")) {
            name[0] + name[2].toString()
        } else name.substring(0, 2)
    }
}