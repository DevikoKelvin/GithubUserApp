package id.devikokelvin.githubuserapp.backend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData

@Database(
    entities = [SearchData::class],
    version = 1
)
abstract class FavoriteDB : RoomDatabase()
{
    companion object
    {
        private var INS: FavoriteDB? = null

        fun getDB(con: Context): FavoriteDB?
        {
            if (INS == null)
                synchronized(FavoriteDB::class)
                {
                    INS = Room
                        .databaseBuilder(
                            con.applicationContext,
                            FavoriteDB::class.java, "favoriteDB"
                        )
                        .build()
                }
            return INS
        }
    }

    abstract fun query(): DBQuery
}