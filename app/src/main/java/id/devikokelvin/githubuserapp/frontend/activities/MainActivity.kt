package id.devikokelvin.githubuserapp.frontend.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.devikokelvin.githubuserapp.R
import id.devikokelvin.githubuserapp.backend.adapters.MainAdapter
import id.devikokelvin.githubuserapp.databinding.ActivityMainBinding
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData
import id.devikokelvin.githubuserapp.backend.api_viewmodel.DataViewModel

class MainActivity : AppCompatActivity()
{
    private lateinit var binds: ActivityMainBinding
    private lateinit var rvMachine: MainAdapter
    private lateinit var vm: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binds = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binds.root)

        vm = ViewModelProvider(this).get(DataViewModel(application)::class.java)

        val search = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        binds.apply {
            searchField.setSearchableInfo(search.getSearchableInfo(componentName))
            searchField.queryHint = resources.getString(R.string.search_hint)

            searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener
            {
                override fun onQueryTextSubmit(query: String): Boolean = false

                override fun onQueryTextChange(query: String): Boolean
                {
                    rvList.visibility = View.VISIBLE
                    if (query != "")
                    {
                        initRV()
                        showLoadBar(true)
                        txtWelcome.visibility = View.INVISIBLE
                        vm.setSearch(query)
                        vm.getSearch().observe(this@MainActivity, {
                            if (it != null)
                            {
                                rvMachine.setData(it)
                                showLoadBar(false)
                                if (rvMachine.itemCount == 0)
                                {
                                    binds.errorMessage.visibility = View.VISIBLE
                                    binds.imgOcto404.visibility = View.VISIBLE
                                    binds.imgOctowelcome.visibility = View.INVISIBLE
                                }
                                else
                                {
                                    binds.errorMessage.visibility = View.INVISIBLE
                                    binds.imgOcto404.visibility = View.INVISIBLE
                                    binds.imgOctowelcome.visibility = View.VISIBLE
                                }
                            }
                        })
                    }
                    else
                    {
                        showLoadBar(false)
                        binds.txtWelcome.visibility = View.VISIBLE
                        binds.rvList.visibility = View.INVISIBLE
                        binds.errorMessage.visibility = View.INVISIBLE
                        binds.imgOcto404.visibility = View.INVISIBLE
                    }
                    return true
                }
            })
        }
    }

    private fun initRV()
    {
        binds.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMachine = MainAdapter()
            rvList.adapter = rvMachine
            rvMachine.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback
            {
                override fun onItemClicked(data: SearchData)
                {
                    showLoadBar(true)
                    startActivity(
                        Intent(this@MainActivity, DetailedInfoActivity::class.java)
                            .also {
                                it.putExtra(DetailedInfoActivity.EXT_USR, data)
                                it.putExtra(DetailedInfoActivity.EXT_ID, data.id)
                                it.putExtra(DetailedInfoActivity.EXT_AVA, data.avatar)
                            }
                    )
                    showLoadBar(false)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.settings_menu ->
            {
                startActivity(
                    Intent(
                        this,
                        ConfigurationActivity::class.java
                    )
                )
            }

            R.id.fav_menu ->
            {
                startActivity(
                    Intent(
                        this,
                        FavoriteListActivity::class.java
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLoadBar(cond: Boolean)
    {
        if (cond)
            binds.progressBar.visibility = View.VISIBLE
        else
            binds.progressBar.visibility = View.INVISIBLE
    }
}
