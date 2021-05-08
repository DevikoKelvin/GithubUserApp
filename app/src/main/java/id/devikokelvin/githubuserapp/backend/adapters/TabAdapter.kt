package id.devikokelvin.githubuserapp.backend.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.devikokelvin.githubuserapp.frontend.fragments.FollowerFrags
import id.devikokelvin.githubuserapp.frontend.fragments.FollowingFrags

class TabAdapter(activity: AppCompatActivity, data: Bundle) :
    FragmentStateAdapter(activity)
{
    private var fragsData: Bundle = data

    override fun createFragment(i: Int): Fragment
    {
        var fragment: Fragment? = null
        when (i)
        {
            0 -> fragment = FollowerFrags()
            1 -> fragment = FollowingFrags()
        }
        fragment?.arguments = this.fragsData
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}