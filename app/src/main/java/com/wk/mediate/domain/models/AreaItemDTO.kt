package com.wk.mediate.domain.models

data class Area(val data: Data)

data class Data(val content: ArrayList<AreaItem>)

class AreaItem(var siGunGu: String)