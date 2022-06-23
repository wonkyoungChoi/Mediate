package com.wk.mediate.data.models

data class SearchAreaResult (
    val regcodes: List<Regcode>
)

data class Regcode(
    val code: String,
    val name: String
)