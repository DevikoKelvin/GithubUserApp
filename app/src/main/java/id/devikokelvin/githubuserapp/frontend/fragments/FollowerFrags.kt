package id.devikokelvin.githubuserapp.frontend.fragments

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.devikokelvin.githubuserapp.R
import id.devikokelvin.githubuserapp.frontend.activities.DetailedInfoActivity
import id.devikokelvin.githubuserapp.backend.adapters.MainAdapter
import id.devikokelvin.githubuserapp.databinding.FragmentFollWerIngBinding
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData
import id.devikokelvin.githubuserapp.backend.api_viewmodel.DataViewModel

class FollowerFrags : Fragment(R.layout.fragment_foll_wer_ing)
{
    private var binding: FragmentFollWerIngBinding? = null
    private val binds get() = binding!!
    private lateinit var vm: DataViewModel
    private lateinit var rvMachine: MainAdapter
    private lateinit var data: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            data = it.getString(DetailedInfoActivity.EXT_USR)
                .toString()
        }

        binding = FragmentFollWerIngBinding.bind(view)

        rvMachine = MainAdapter()
        rvMachine.notifyDataSetChanged()

        initRV()

        showLoading(true)
        vm = ViewModelProvider(this).get(DataViewModel(Application())::class.java)
        vm.setFollwering(data, "followers")
        vm.getFollwering().observe(viewLifecycleOwner, {
            if (it != null)
            {
                rvMachine.setData(it)
                showLoading(false)
                if (rvMachine.itemCount == 0)
                {
                    binds.errorMessage.text = getString(R.string.no_followers)
                    binds.errorMessage.visibility = View.VISIBLE
                }
                else
                    binds.errorMessage.visibility = View.INVISIBLE
            }
        })
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        binding = null
    }

    private fun initRV()
    {
        binds.apply {
            rvList.layoutManager = LinearLayoutManager(activity)
            rvList.adapter = rvMachine
            rvMachine.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback
            {
                override fun onItemClicked(data: SearchData)
                {
                    showLoading(true)
                    startActivity(
                        Intent(activity, DetailedInfoActivity::class.java)
                            .putExtra(DetailedInfoActivity.EXT_USR, data)
                    )
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean)
    {
        if (state)
            binds.loading.visibility = View.VISIBLE
        else
            binds.loading.visibility = View.INVISIBLE
    }
}