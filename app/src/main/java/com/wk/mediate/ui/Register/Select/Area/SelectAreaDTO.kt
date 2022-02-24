package com.wk.mediate.ui.Register.Select.Area

data class SelectAreaResult (
    val regcodes: List<Regcode>
)

data class Regcode(
    val code: String,
    val name: String
)