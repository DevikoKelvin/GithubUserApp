package id.devikokelvin.githubuserapp.backend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.devikokelvin.githubuserapp.R
import id.devikokelvin.githubuserapp.backend.adapters.MainAdapter.UserViewHolder
import id.devikokelvin.githubuserapp.databinding.ItemUserBinding
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData

class MainAdapter : RecyclerView.Adapter<UserViewHolder>()
{
    private val arrData = ArrayList<SearchData>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(data: ArrayList<SearchData>)
    {
        arrData.clear()
        arrData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder
    {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, i: Int)
    {
        holder.binds.apply {
            txtUsername.text = arrData[i].username
            txtUrl.text = arrData[i].url
            Glide
                .with(holder.itemView.context)
                .load(arrData[i].avatar)
                .apply(
                    RequestOptions()
                        .override(60, 60)
                )
                .into(imgAvatar)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(arrData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = arrData.size

    inner class UserViewHolder(v: View) : ViewHolder(v)
    {
        val binds = ItemUserBinding.bind(v)
    }

    interface OnItemClickCallback
    {
        fun onItemClicked(data: SearchData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback)
    {
        this.onItemClickCallback = onItemClickCallback
    }
}