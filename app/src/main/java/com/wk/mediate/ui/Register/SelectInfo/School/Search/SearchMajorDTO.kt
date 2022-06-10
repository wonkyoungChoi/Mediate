package com.wk.mediate.ui.Register.SelectInfo.School.Search

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