package com.wk.mediate.domain.repository

import javax.inject.Inject

class SelectSubjectRepository  @Inject constructor() {
    var elementSubjects = arrayOf("초등국어", "초등영어", "초등수학", "초등과학", "초등사회")

    var middleSubjects = arrayOf("중등국어", "중등영어", "중등수학", "중등과학", "중등사회")

    var highSubjects = arrayOf("고등국어", "고등영어", "고등수학", "고등과학", "고등사회")

    var entranceExam = arrayOf("학종", "자소서", "대입컨설팅", "대입면접")

    var computer = arrayOf("포토샵", "일러스트레이터", "코딩")

    var foreignLanguage = arrayOf("영어회화", "비즈니스영어", "중국어", "일본어", "프랑스어", "스페인어", "독일어", "아랍어", "러시아어", "베트남어")

    var instrument = arrayOf("피아노", "베이스기타", "일렉기타", "드럼", "바이올린", "통기타", "첼로", "보컬")


}



