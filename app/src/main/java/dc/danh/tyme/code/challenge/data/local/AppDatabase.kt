package dc.danh.tyme.code.challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
// Room database for storing user data
abstract class AppDatabase : RoomDatabase() {

    // DAO for user operations
    abstract fun userDao(): UserDao
}

