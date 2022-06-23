package com.wk.mediate.present.views.register.selectInfo.school.search

data class SearchUnivResult(
        var dataSearch: DataSearch? = null
)

data class DataSearch(
        var content: List<Content>? = null
)

data class Content(
        var schoolGubun: String? = null,
        var schoolName: String? = null,
        var totalCount: Int? = null
)