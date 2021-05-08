package id.devikokelvin.githubconsumerapp.backend.datalass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteData(
    var id: Int,
    var avatar: String?,
    var username: String?,
    var url: String?
) : Parcelable