package com.wk.mediate.ui.Register.SelectInfo.School.Search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Head {
    @SerializedName("list_total_count")
    @Expose
    var listTotalCount: Int? = null

    @SerializedName("RESULT")
    @Expose
    var result: Result? = null
}

class Result {
    @SerializedName("CODE")
    @Expose
    var code: String? = null

    @SerializedName("MESSAGE")
    @Expose
    var message: String? = null
}

class Row {
    @SerializedName("SCHUL_NM")
    @Expose
    var schulNm: String? = null
}



class SchoolInfo {
    @SerializedName("head")
    @Expose
    var head: ArrayList<Head>? = null

    @SerializedName("row")
    @Expose
    var row: ArrayList<Row>? = null
}

data class SearchSchoolResult(
        @SerializedName("schoolInfo")
        @Expose
        var schoolInfo: ArrayList<SchoolInfo>? = null
)