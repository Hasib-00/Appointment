package com.example.appointment.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * DoctorDatabase
 * ---------------
 * The main Room Database class for your app.
 * It defines:
 *   - The list of entities (tables) the database will hold.
 *   - The version of the database schema.
 *   - The DAO (Data Access Object) to interact with the data.
 *
 * Room automatically handles creating and updating the database file
 * based on this class.
 */
@Database(
    entities = [Doctor::class],  // The tables inside this database
    version = 3,                 // Must be incremented when you change schema
    exportSchema = false         // Avoids exporting schema JSON files (optional)
)
abstract class DoctorDatabase : RoomDatabase() {

    // This tells Room which DAO to use to access Doctor table
    abstract fun doctorDao(): DoctorDao

    companion object {
        // Singleton instance to prevent creating multiple databases
        @Volatile
        private var INSTANCE: DoctorDatabase? = null

        // 🔹 Migration: version 1 → 2
        // Adds a new column called "imageUri" to the doctor_table
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE doctor_table ADD COLUMN imageUri TEXT")
            }
        }

        // 🔹 Migration: version 2 → 3
        // Placeholder for future upgrades — currently does nothing
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example if needed in future:
                // database.execSQL("ALTER TABLE doctor_table ADD COLUMN newField TEXT")
            }
        }

        /**
         * Creates or returns the existing database instance.
         *
         * The synchronized block ensures only one instance of the DB
         * exists throughout the entire app (thread-safe).
         */
        fun getDatabase(context: Context): DoctorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DoctorDatabase::class.java,
                    "doctor_database"   // The name of the database file
                )
                    // Add migrations to keep data safe when upgrading versions
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    // 🛠️ Fallback: if migration fails or version mismatched,
                    // Room rebuilds DB from scratch (prevents crash)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
