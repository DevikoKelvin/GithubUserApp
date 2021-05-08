package id.devikokelvin.githubconsumerapp.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.devikokelvin.githubconsumerapp.R
import id.devikokelvin.githubconsumerapp.backend.viewmodel.MainViewModel
import id.devikokelvin.githubconsumerapp.backend.adapter.MainAdapter
import id.devikokelvin.githubconsumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binds: ActivityMainBinding
    private lateinit var rvMachine: MainAdapter
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binds = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binds.root)

        rvMachine = MainAdapter()
        rvMachine.notifyDataSetChanged()

        vm = ViewModelProvider(this).get(MainViewModel(application)::class.java)

        initRV()

        vm.setFavorite(this)
        vm.getFavorite().observe(this, {
            if (it != null)
            {
                rvMachine.setData(it)
            }
        })
    }

    private fun initRV()
    {
        binds.apply{
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = rvMachine
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
            R.id.about_menu ->
            {
                startActivity(
                    Intent(
                        this,
                        AboutActivity::class.java
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}