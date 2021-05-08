package id.devikokelvin.githubuserapp.frontend.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.devikokelvin.githubuserapp.R

class LoadingScreenActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    startActivity(
                        Intent(
                            this@LoadingScreenActivity,
                            MainActivity::class.java
                        )
                    )
                    overridePendingTransition(R.anim.fade, 0)
                    finish()
                },
                1500
            )
    }
}