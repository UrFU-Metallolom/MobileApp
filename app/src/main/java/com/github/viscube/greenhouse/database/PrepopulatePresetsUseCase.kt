package com.github.viscube.greenhouse.database

import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity


class PrepopulatePresetsUseCase(private val db: Database) {
    suspend operator fun invoke() {
        if (db.deviceDao().getPresets().isEmpty()) {
            db.deviceDao().insertAllPresets(
                listOf(
                    PresetEntity("Базилик ", "24", "60", "700"),
                    PresetEntity("Клубника", "24", "80", "900"),
                    PresetEntity("Лук", "22", "65", "800"),
                    PresetEntity("Петунья", "21", "50", "550"),
                    PresetEntity("Салат", "20", "70", "600"),
                )
            )
        }
    }
}