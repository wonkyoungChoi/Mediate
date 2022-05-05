package com.wk.mediate.ui.Register.BasicInfo

import android.app.Application

object BasicRegisterInfo {
    var info = BasicInfo("","","","")
}

data class RegisterResult (var body: String, var message: String)

data class BasicInfo(var accountId: String, var name: String, var password: String, var phoneNum: String)
