package app.qr.qrutility.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(QRCodeModel::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQRCodeDao(): QRCodeDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val currentInstance = INSTANCE
            if (currentInstance != null) {
                return currentInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "qr_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}