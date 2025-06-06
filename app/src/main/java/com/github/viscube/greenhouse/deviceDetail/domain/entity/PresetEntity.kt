package com.github.viscube.greenhouse.deviceDetail.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PresetEntity(
    @PrimaryKey(autoGenerate = false) val name: String,
    val temperature: String,
    val moisture: String,
    val light: String
)