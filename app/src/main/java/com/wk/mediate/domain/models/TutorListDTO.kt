package com.wk.mediate.domain.models

data class TutorListDTO(var ImageUrl: Int, var name: String, var type: String, var school: String,
                        var grade: String, var tutoringNow: String, var tutoringGoal: String,
                        var percent: Int, var percentDesc: String)