package id.devikokelvin.githubuserapp.backend.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_list")
data class SearchData(
    @PrimaryKey var id: Int,
    var avatar: String?,
    var username: String?,
    var url: String?
) : Parcelable