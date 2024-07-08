package com.farouk.exersize.features.plan.domain.Entitiy

data class Msg(
    val day: String,
    val exercises: List<Exercise>,
    val id: String,
    val name: String
)