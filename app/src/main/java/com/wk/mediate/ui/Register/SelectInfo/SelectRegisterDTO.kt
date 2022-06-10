package com.wk.mediate.ui.Register.SelectInfo

object SelectRegisterInfo {
    var type: String = ""
    var tutorInfo = SelectInfoTutor("","",null,"", 0.0, 0.0, "", "" ,"")
    var tuteeInfo = SelectInfoTutee("","","",0.0, 0.0, "", "")
}

data class RegisterResult (var body: String, var message: String)

data class SelectInfoTutor(var accountId: String, var address: String, var curriculum: List<String>?, var grade: String, var latitude: Double, var longitude: Double, var major: String, var name: String,  var school: String)
data class SelectInfoTutee(var accountId: String, var address: String, var grade: String, var latitude: Double, var longitude: Double, var name: String, var school: String)
