package com.github.viscube.greenhouse.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.viscube.greenhouse.dao.DeviceDao
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailDbEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity

@Database(
    entities = [DeviceEntity::class, DeviceDetailDbEntity::class, PresetEntity::class],
    version = 2
)
abstract class Database : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao

}