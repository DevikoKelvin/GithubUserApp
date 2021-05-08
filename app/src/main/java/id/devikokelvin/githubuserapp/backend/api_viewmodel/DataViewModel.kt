package id.devikokelvin.githubuserapp.backend.api_viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.devikokelvin.githubuserapp.backend.database.DBQuery
import id.devikokelvin.githubuserapp.backend.database.FavoriteDB
import id.devikokelvin.githubuserapp.backend.dataclass.DetailedData
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class DataViewModel(app: Application) : AndroidViewModel(app)
{
    private val search = MutableLiveData<ArrayList<SearchData>>()
    private val detailed = MutableLiveData<DetailedData>()
    private val follwering = MutableLiveData<ArrayList<SearchData>>()
    private var db: FavoriteDB? = FavoriteDB.getDB(app)
    private var query: DBQuery? = db?.query()

    fun getSearch(): LiveData<ArrayList<SearchData>> = search

    fun setSearch(query: String?)
    {
        val data = ArrayList<SearchData>()
        val connect = AsyncHttpClient()
        connect
            .addHeader(
                "Authorization",
                "token 5cbcdd84a7def25ba085d29f872fcd4e390c767b"
            )
        connect
            .addHeader(
                "User-Agent",
                "request"
            )
        connect
            .get(
                "https://api.github.com/search/users?q=${query}",
                object : AsyncHttpResponseHandler()
                {
                    override fun onSuccess(
                        statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?
                    )
                    {
                        val res = String(responseBody!!)
                        try
                        {
                            val obj = JSONObject(res)
                            val arr = obj.getJSONArray("items") as JSONArray
                            for (i in 0 until arr.length())
                            {
                                val json = arr.getJSONObject(i)
                                data.add(
                                    SearchData(
                                        json.getInt("id"),
                                        json.getString("avatar_url"),
                                        json.getString("login"),
                                        json.getString("html_url"),
                                    )
                                )
                            }
                            search.postValue(data)
                        }
                        catch (e: Exception)
                        {
                            Log.d("Trace", e.message.toString())
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(
                        statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?,
                        error: Throwable?
                    )
                    {
                        val errorMessage = when (statusCode)
                        {
                            401 -> "$statusCode : Bad Request"
                            403 -> "$statusCode : Forbidden"
                            404 -> "$statusCode : Not Found"
                            0 -> "$statusCode : No Internet Connection"
                            else -> "$statusCode : ${error!!.message}"
                        }
                        Log.d("Error", errorMessage)
                    }
                }
            )
    }

    fun getDetail(): LiveData<DetailedData> = detailed

    fun setDetailed(query: String)
    {
        val connect = AsyncHttpClient()
        connect
            .addHeader(
                "Authorization",
                "token 5cbcdd84a7def25ba085d29f872fcd4e390c767b"
            )
        connect
            .addHeader(
                "User-Agent",
                "request"
            )
        connect
            .get(
                "https://api.github.com/users/${query}",
                object : AsyncHttpResponseHandler()
                {
                    override fun onSuccess(
                        statusCode: Int, headers: Array<out Header>, responseBody: ByteArray
                    )
                    {
                        val res = String(responseBody)
                        try
                        {
                            val obj = JSONObject(res)
                            detailed
                                .postValue(
                                    DetailedData(
                                        obj.getString("avatar_url"),
                                        obj.getString("login"),
                                        obj.getString("name"),
                                        obj.getString("bio"),
                                        obj.getString("company"),
                                        obj.getString("location"),
                                        obj.getString("followers"),
                                        obj.getString("following"),
                                        obj.getString("public_repos"),
                                    )
                                )
                        }
                        catch (e: Exception)
                        {
                            Log.d("Trace", e.message.toString())
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(
                        statusCode: Int, headers: Array<out Header>, responseBody: ByteArray,
                        error: Throwable
                    )
                    {
                        val errorMessage = when (statusCode)
                        {
                            401 -> "$statusCode : Bad Request"
                            403 -> "$statusCode : Forbidden"
                            404 -> "$statusCode : Not Found"
                            0 -> "$statusCode : No Internet Connection"
                            else -> "$statusCode : ${error.message}"
                        }
                        Log.d("Error", errorMessage)
                    }
                }
            )
    }

    fun getFollwering(): LiveData<ArrayList<SearchData>> = follwering

    fun setFollwering(username: String?, query: String?)
    {
        val data = ArrayList<SearchData>()
        val connect = AsyncHttpClient()
        connect
            .addHeader(
                "Authorization",
                "token 5cbcdd84a7def25ba085d29f872fcd4e390c767b"
            )
        connect
            .addHeader(
                "User-Agent",
                "request"
            )
        connect
            .get(
                "https://api.github.com/users/$username/$query",
                object : AsyncHttpResponseHandler()
                {
                    override fun onSuccess(
                        statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?
                    )
                    {
                        val res = String(responseBody!!)
                        try
                        {
                            val arr = JSONArray(res)
                            for (i in 0 until arr.length())
                            {
                                val obj = arr.getJSONObject(i)
                                data.add(
                                    SearchData(
                                        obj.getInt("id"),
                                        obj.getString("avatar_url"),
                                        obj.getString("login"),
                                        obj.getString("html_url"),
                                    )
                                )
                            }
                            follwering.postValue(data)
                        }
                        catch (e: Exception)
                        {
                            Log.d("Trace", e.message.toString())
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(
                        statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?,
                        error: Throwable?
                    )
                    {
                        val errorMessage = when (statusCode)
                        {
                            401 -> "$statusCode : Bad Request"
                            403 -> "$statusCode : Forbidden"
                            404 -> "$statusCode : Not Found"
                            0 -> "$statusCode : No Internet Connection"
                            else -> "$statusCode : ${error!!.message}"
                        }
                        Log.d("Error", errorMessage)
                    }
                })
    }

    fun getList(): LiveData<List<SearchData>>? = query?.getList()

    fun addList(id: Int, ava: String, usrname: String, url: String)
    {
        CoroutineScope(Dispatchers.IO)
            .launch {
                query?.addList(
                    SearchData(
                        id,
                        ava,
                        usrname,
                        url
                    )
                )
            }
    }

    suspend fun check(id: Int) = query?.check(id)

    fun remove(id: Int)
    {
        CoroutineScope(Dispatchers.IO)
            .launch {
                query?.remove(id)
            }
    }
}