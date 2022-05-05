package com.wk.mediate.ui.Register.Select

object SelectRegisterInfo {
    var type: String = ""
    var tutorInfo = SelectInfoTutor("","","","", "", "", "", "" ,"", "")
    var tuteeInfo = SelectInfoTutee("","","","", "", "", "","")
}

data class RegisterResult (var body: String, var message: String)

data class SelectInfoTutor(var accountId: String, var address: String, var curriculum: String, var grade: String, var latitude: String, var longitude: String, var name: String, var major: String, var school: String, var type: String)
data class SelectInfoTutee(var accountId: String, var address: String, var grade: String, var latitude: String, var longitude: String, var name: String, var school: String, var type: String)
