package dc.danh.tyme.code.challenge.di

import androidx.room.Database
import androidx.room.RoomDatabase
import dc.danh.tyme.code.challenge.data.local.UserDao
import dc.danh.tyme.code.challenge.data.local.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}