package id.devikokelvin.githubuserapp.backend.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData

@Dao
interface DBQuery
{
    @Insert
    suspend fun addList(fav: SearchData)

    @Query("SELECT * FROM favorite_list")
    fun getList(): LiveData<List<SearchData>>

    @Query("SELECT count(*) FROM favorite_list WHERE favorite_list.id = :id")
    suspend fun check(id: Int): Int

    @Query("DELETE FROM favorite_list WHERE favorite_list.id = :id")
    suspend fun remove(id: Int): Int

    @Query("SELECT * FROM favorite_list")
    fun getAll(): Cursor
}