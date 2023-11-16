package com.andy.rios.elektra.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andy.rios.elektra.room.dao.ContactDao
import com.andy.rios.elektra.room.entity.ContactDBEntity
@Database(
    entities = [ContactDBEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GroupSalinaDataBase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}