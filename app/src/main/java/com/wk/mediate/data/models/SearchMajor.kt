package com.wk.mediate.data.models

data class SearchMajorResult(
        var dataSearch: MajorDataSearch? = null
)

data class MajorDataSearch(
        var content: List<MajorContent>? = null
)

data class MajorContent(
        var mClass: String? = null,
        var totalCount: Int? = null
)