package com.example.appointment.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Doctor::class], version = 3, exportSchema = false)
abstract class DoctorDatabase : RoomDatabase() {

    abstract fun doctorDao(): DoctorDao

    companion object {
        @Volatile
        private var INSTANCE: DoctorDatabase? = null

        // ✅ Migration from version 1 → 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add new column with default NULL
                database.execSQL("ALTER TABLE doctor_table ADD COLUMN imageUri TEXT")
            }
        }

        // ✅ Migration from version 2 → 3 (if future changes needed)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Currently nothing to add, just placeholder for forward migration
                // Example: database.execSQL("ALTER TABLE doctor_table ADD COLUMN newField TEXT")
            }
        }

        fun getDatabase(context: Context): DoctorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DoctorDatabase::class.java,
                    "doctor_database"
                )
                    // Add defined migrations
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    // ✅ Safeguard — rebuild DB automatically if version mismatch (fixes your crash)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
