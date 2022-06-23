package com.wk.mediate.domain.repository

import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.present.utils.DataHandler

interface RegisterRepository {
    suspend fun getBasicRegister(info: BasicInfo): DataHandler<RegisterEntity>
    suspend fun getSelectTutorRegister(info: TutorInfo): DataHandler<RegisterEntity>
    suspend fun getSelectTuteeRegister(info: TuteeInfo): DataHandler<RegisterEntity>
}



