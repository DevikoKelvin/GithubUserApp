package id.devikokelvin.githubuserapp.backend.services

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import id.devikokelvin.githubuserapp.backend.database.DBQuery
import id.devikokelvin.githubuserapp.backend.database.FavoriteDB

class AppProvider : ContentProvider()
{
    private lateinit var query: DBQuery
    private val uriM = UriMatcher(UriMatcher.NO_MATCH)
    private var c: Cursor? = null

    init
    {
        uriM.addURI(
            "id.devikokelvin.githubuserapp",
            "favorite_list",
            1
        )
    }

    override fun onCreate(): Boolean
    {
        getData()

        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor?
    {
        when (uriM.match(uri))
        {
            1 ->
            {
                c = query.getAll()
                if (context != null)
                    c!!.setNotificationUri(
                        context?.contentResolver,
                        uri
                    )
            }
        }
        return c
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    private fun getData()
    {
        query = context?.let { FavoriteDB.getDB(it)?.query() }!!
    }
}