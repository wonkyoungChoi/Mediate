package com.wk.mediate.ui.Register.Select.School.Search

data class SearchUniversityResult(
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