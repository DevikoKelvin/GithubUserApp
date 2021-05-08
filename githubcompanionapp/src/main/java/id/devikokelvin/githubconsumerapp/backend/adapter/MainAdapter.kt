package id.devikokelvin.githubconsumerapp.backend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.devikokelvin.githubconsumerapp.databinding.ItemUserBinding
import id.devikokelvin.githubconsumerapp.R
import id.devikokelvin.githubconsumerapp.backend.datalass.FavoriteData
import id.devikokelvin.githubconsumerapp.backend.adapter.MainAdapter.UserViewHolder

class MainAdapter : RecyclerView.Adapter<UserViewHolder>()
{
    private val userModels = ArrayList<FavoriteData>()

    fun setData(user: ArrayList<FavoriteData>)
    {
        userModels.clear()
        userModels.addAll(user)
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

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)
    {
        val user = userModels[position]
        holder.binding.txtUsername.text = user.username
        holder.binding.txtUrl.text = user.url
        Glide.with(holder.itemView.context).load(user.avatar).apply(
            RequestOptions().override(60, 60)
        ).into(holder.binding.imgAvatar)
    }

    override fun getItemCount(): Int = userModels.size

    inner class UserViewHolder(view: View) : ViewHolder(view)
    {
        val binding = ItemUserBinding.bind(view)
    }
}