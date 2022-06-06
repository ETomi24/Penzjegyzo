package hu.bme.aut.penzjegyzo.data

import androidx.room.*

@Dao
interface BejegyzesDao {
    @Query("SELECT * FROM bejegyzes")
    fun getAll(): List<Bejegyzes>

    @Query("SELECT SUM(value) FROM bejegyzes where category = 0")
    fun getSumBevetel() : Int

    @Query("SELECT SUM(value) FROM bejegyzes where category = 1")
    fun getSumKiadas() : Int

    @Query("Select Count(id) from bejegyzes")
    fun getCount() : Int

    @Query("Select Count(id) from bejegyzes where date = :today")
    fun getTodayCount(today : String) : Int

    @Insert
    fun insert(bejegyzesek: Bejegyzes): Long

    @Delete
    fun deleteItem(bejegyzes: Bejegyzes)
}