package com.wk.mediate.ui.Register.Search

data class SearchUniversityResult(
        val dataSearch: DataSearch
)

data class DataSearch(
        val content: List<Content>
)

data class Content(
        val schoolGubun: String,
        val schoolName: String,
        val totalCount: Int
)