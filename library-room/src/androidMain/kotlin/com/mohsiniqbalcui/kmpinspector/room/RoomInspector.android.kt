package com.mohsiniqbalcui.kmpinspector.room

import androidx.room.RoomDatabase
import com.mohsiniqbalcui.kmpinspector.KmpInspector

/** Android already has a collector that knows both Room setups; reuse it. */
internal actual fun attachRoom(database: RoomDatabase, fileName: String?) =
    KmpInspector.attach(database, fileName)
