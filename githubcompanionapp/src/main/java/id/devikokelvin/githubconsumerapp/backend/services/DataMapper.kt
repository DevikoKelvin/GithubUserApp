package id.devikokelvin.githubconsumerapp.backend.services

import android.database.Cursor
import id.devikokelvin.githubconsumerapp.backend.datalass.FavoriteData

object DataMapper
{
    fun mapArrListCur(cursor: Cursor?): ArrayList<FavoriteData>
    {
        val list = ArrayList<FavoriteData>()
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                val id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
                )
                val avatar = cursor.getString(
                    cursor.getColumnIndexOrThrow("avatar")
                )
                val username = cursor.getString(
                    cursor.getColumnIndexOrThrow("username")
                )
                val url = cursor.getString(
                    cursor.getColumnIndexOrThrow("url")
                )
                list.add(
                    FavoriteData(
                        id,
                        avatar,
                        username,
                        url
                    )
                )
            }
        }

        return list
    }
}