package id.devikokelvin.githubuserapp.frontend.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.devikokelvin.githubuserapp.R
import id.devikokelvin.githubuserapp.backend.adapters.MainAdapter
import id.devikokelvin.githubuserapp.databinding.ActivityFavoriteBinding
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData
import id.devikokelvin.githubuserapp.backend.api_viewmodel.DataViewModel

class FavoriteListActivity : AppCompatActivity()
{
    private lateinit var binds: ActivityFavoriteBinding
    private lateinit var rvMachine: MainAdapter
    private lateinit var vm: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binds = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binds.root)
        supportActionBar?.title = getString(R.string.favoritebar)

        rvMachine = MainAdapter()
        rvMachine.notifyDataSetChanged()

        vm = ViewModelProvider(this)
            .get(DataViewModel(application)::class.java)

        rvMachine.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback
        {
            override fun onItemClicked(data: SearchData)
            {
                showLoadBar(true)
                startActivity(
                    Intent(
                        this@FavoriteListActivity,
                        DetailedInfoActivity::class.java
                    ).also {
                        it.putExtra(DetailedInfoActivity.EXT_USR, data)
                            .putExtra(DetailedInfoActivity.EXT_ID, data.id)
                            .putExtra(DetailedInfoActivity.EXT_AVA, data.avatar)
                    }
                )
                showLoadBar(false)
            }
        })

        initRV()
        getData()
    }

    private fun initRV()
    {
        binds.apply {
            rvList.layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            rvList.adapter = rvMachine
        }
    }

    private fun getData()
    {
        vm.getList()?.observe(this, {
            rvMachine.setData(mapConverter(it))
        })
    }

    private fun mapConverter(list: List<SearchData>): ArrayList<SearchData>
    {
        val listUser = ArrayList<SearchData>()
        for (i in list)
        {
            listUser.add(
                SearchData(
                    i.id,
                    i.avatar,
                    i.username,
                    i.url
                )
            )
        }
        return listUser
    }

    fun showLoadBar(cond: Boolean)
    {
        if (cond)
            binds.progressBar.visibility = View.VISIBLE
        else
            binds.progressBar.visibility = View.INVISIBLE
    }
}