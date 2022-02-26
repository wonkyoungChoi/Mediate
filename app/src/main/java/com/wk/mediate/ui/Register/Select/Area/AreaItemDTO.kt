package com.wk.mediate.ui.Register.Select.Area

data class Area(val data: Data)

data class Data(val content: ArrayList<AreaItem>)

class AreaItem(var siGunGu: String)