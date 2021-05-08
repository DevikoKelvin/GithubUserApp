package id.devikokelvin.githubconsumerapp.backend.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.devikokelvin.githubconsumerapp.backend.database.DBContract
import id.devikokelvin.githubconsumerapp.backend.services.DataMapper
import id.devikokelvin.githubconsumerapp.backend.datalass.FavoriteData

class MainViewModel(application: Application) : AndroidViewModel(application)
{
    private var list = MutableLiveData<ArrayList<FavoriteData>>()

    @SuppressLint("Recycle")
    fun setFavorite(context: Context)
    {
        val cur = context.contentResolver.query(
            DBContract.FavUserColumn.CONT_URI,
            null,
            null,
            null,
            null
        )

        val convert = DataMapper.mapArrListCur((cur))
        list.postValue(convert)
    }

    fun getFavorite(): LiveData<ArrayList<FavoriteData>> = list
}