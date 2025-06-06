package com.github.viscube.greenhouse.database

import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity


class PrepopulatePresetsUseCase(private val db: Database) {
    suspend operator fun invoke() {
        if (db.deviceDao().getPresets().isEmpty()) {
            db.deviceDao().insertAllPresets(
                listOf(
                    PresetEntity("Салат", "20", "70", "600"),
                    PresetEntity("Базилик ", "24", "60", "700"),
                    PresetEntity("Петунья", "21", "50", "550")
                )
            )
        }
    }
}