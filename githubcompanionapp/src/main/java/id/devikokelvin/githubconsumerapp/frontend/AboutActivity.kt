package id.devikokelvin.githubconsumerapp.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.devikokelvin.githubconsumerapp.R
import id.devikokelvin.githubconsumerapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity()
{
    private lateinit var binds: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binds = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binds.root)

        supportActionBar!!.title = resources.getString(R.string.about)
    }
}