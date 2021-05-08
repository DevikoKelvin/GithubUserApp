package id.devikokelvin.githubuserapp.frontend.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.devikokelvin.githubuserapp.R
import id.devikokelvin.githubuserapp.backend.adapters.TabAdapter
import id.devikokelvin.githubuserapp.databinding.ActivityDetailBinding
import id.devikokelvin.githubuserapp.backend.dataclass.SearchData
import id.devikokelvin.githubuserapp.backend.api_viewmodel.DataViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailedInfoActivity : AppCompatActivity()
{
    private lateinit var binds: ActivityDetailBinding
    private lateinit var vm: DataViewModel
    private var togChecker = false

    companion object
    {
        const val EXT_USR = "EXTRA_USER"
        const val EXT_ID = "EXTRA_ID"
        const val EXT_AVA = "EXTRA_AVATAR"

        private val TABS = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binds = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binds.root)

        supportActionBar?.title = ""

        val data = intent.getParcelableExtra<SearchData>(EXT_USR) as SearchData
        val usrId = intent.getIntExtra(EXT_ID, 0)
        val usrAva = intent.getStringExtra(EXT_AVA)

        val bun = Bundle()
        bun.putString(EXT_USR, data.username.toString())

        vm = ViewModelProvider(this).get(DataViewModel(application)::class.java)

        data.username?.let {
            vm.setDetailed(it)
        }

        binds.apply {
            favTogglebtn.visibility = View.INVISIBLE

            CoroutineScope(Dispatchers.IO)
                .launch {
                    val i = vm.check(usrId)
                    withContext(Dispatchers.Main)
                    {
                        if (i!! > 0)
                        {
                            favTogglebtn.isChecked = true
                            togChecker = true
                        }
                        else
                        {
                            favTogglebtn.isChecked = false
                            togChecker = false
                        }
                    }
                }

            favTogglebtn.setOnClickListener {
                if (!togChecker)
                {
                    vm.addList(
                        usrId,
                        usrAva.toString(),
                        data.username.toString(),
                        data.url.toString()
                    )
                    Toast.makeText(
                        this@DetailedInfoActivity, getString(R.string.favorited), Toast.LENGTH_SHORT
                    ).show()
                }
                else
                    vm.remove(usrId)
                favTogglebtn.isChecked = !togChecker
            }

            vm.getDetail().observe(this@DetailedInfoActivity,
                {
                    supportActionBar?.title = it.usrname
                    Glide.with(this@DetailedInfoActivity).load(it.ava).apply(
                        RequestOptions().override(200, 200)
                    ).into(imgAvatar)
                    if (it.name != "null")
                        txtName.text = it.name
                    else
                        txtName.text = getString(R.string.null_info)
                    if (it.about != "null")
                        txtBio.text = it.about
                    else
                        txtBio.text = getString(R.string.null_info)
                    if (it.cpy != "null")
                        txtCompany.text = it.cpy
                    else
                        txtCompany.text = getString(R.string.null_info)
                    if (it.loc != "null")
                        txtLocation.text = it.loc
                    else
                        txtLocation.text = getString(R.string.null_info)
                    txtFollowercount.text = it.follower
                    txtFollowingcount.text = it.following
                    txtRepo.text = it.rep
                    favTogglebtn.visibility = View.VISIBLE
                }
            )

            pagerView.adapter = TabAdapter(
                this@DetailedInfoActivity,
                bun
            )

            TabLayoutMediator(tabView, pagerView)
            { tab, position ->
                tab.text = resources.getString(TABS[position])
            }.attach()
        }
    }
}

