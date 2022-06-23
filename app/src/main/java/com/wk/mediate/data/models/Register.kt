package com.wk.mediate.data.models

object BasicRegisterInfo {
    var info = BasicInfo("","","","")
}

object SelectRegisterInfo {
    var type: String = ""
    var tutorInfo = TutorInfo("","",null,"", 0.0, 0.0, "", "" ,"")
    var tuteeInfo = TuteeInfo("","","",0.0, 0.0, "", "")
}

data class RegisterResult (var body: String, var message: String)

data class BasicInfo(var accountId: String, var name: String, var password: String, var phoneNum: String)

data class TutorInfo(var accountId: String, var address: String, var curriculum: List<String>?, var grade: String, var latitude: Double, var longitude: Double, var major: String, var name: String,  var school: String)
data class TuteeInfo(var accountId: String, var address: String, var grade: String, var latitude: Double, var longitude: Double, var name: String, var school: String)