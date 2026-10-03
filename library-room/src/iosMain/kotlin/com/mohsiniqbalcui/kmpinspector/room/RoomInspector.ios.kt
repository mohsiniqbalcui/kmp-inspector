package com.mohsiniqbalcui.kmpinspector.room

import androidx.room.RoomDatabase

internal actual fun attachRoom(database: RoomDatabase, fileName: String?) =
    DriverRoomCollector(database, fileName).attach()
