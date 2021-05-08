package id.devikokelvin.githubuserapp.backend.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailedData(
    var ava: String?,
    var usrname: String?,
    var name: String?,
    var about: String?,
    var cpy: String?,
    var loc: String?,
    var follower: String?,
    var following: String?,
    var rep: String?
) : Parcelable