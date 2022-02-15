package com.wk.mediate.ui.Register

object RegisterInfo {
    var info: Info? = null
}

data class Info(var address: String, var grade: String, var id: String,
                var name: String, var password: String, var phoneNum: String,
                var school: String, var type: String)