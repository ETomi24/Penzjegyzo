package hu.bme.aut.penzjegyzo.data

import android.content.Context
import androidx.room.*

@Database(entities = [Bejegyzes::class], version = 3)
@TypeConverters(value = [Bejegyzes.Category::class])
abstract class BejegyzesDatabase : RoomDatabase() {
    abstract fun BejegyzesDao(): BejegyzesDao

    companion object {
        fun getDatabase(applicationContext: Context): BejegyzesDatabase {
            return Room.databaseBuilder(
                applicationContext,
                BejegyzesDatabase::class.java,
                "Bejegyzesek"
            ).fallbackToDestructiveMigration().build();
        }
    }
}