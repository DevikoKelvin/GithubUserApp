package id.devikokelvin.githubconsumerapp.backend.database

import android.net.Uri
import android.provider.BaseColumns

object DBContract
{
    internal class FavUserColumn: BaseColumns
    {
        companion object
        {
            val CONT_URI = Uri.Builder().scheme("content")
                .authority("id.devikokelvin.githubuserapp")
                .appendPath("favorite_list").build()
        }
    }
}