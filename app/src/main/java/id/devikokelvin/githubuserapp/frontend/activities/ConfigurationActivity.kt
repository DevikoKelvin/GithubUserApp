package id.devikokelvin.githubuserapp.frontend.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import id.devikokelvin.githubuserapp.R
import id.devikokelvin.githubuserapp.databinding.ActivitySettingsBinding
import id.devikokelvin.githubuserapp.backend.dataclass.AlarmChecker
import id.devikokelvin.githubuserapp.backend.services.AlarmRec

class ConfigurationActivity : AppCompatActivity()
{
    private lateinit var binds: ActivitySettingsBinding
    private lateinit var check: AlarmChecker
    private lateinit var alarm: AlarmRec

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binds = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binds.root)
        supportActionBar?.title = getString(R.string.settingsbar)

        binds.apply {
            alarmSwitch.isChecked = getAlarm().isSet

            alarm = AlarmRec()
            alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                {
                    saveReminder(true)
                    alarm.repeat(
                        this@ConfigurationActivity,
                        "09:00",
                        "Github App Reminder"
                    )
                }
                else
                {
                    saveReminder(false)
                    alarm.cancel(this@ConfigurationActivity)
                }
            }

            changeLangBtn.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }

    private fun getAlarm(): AlarmChecker
    {
        val pref = application.getSharedPreferences(
            "ALARM_PREF",
            Context.MODE_PRIVATE
        )
        val check = AlarmChecker()
        check.isSet = pref.getBoolean(
            "reminded",
            false
        )
        return check
    }

    private fun setAlarm(check: AlarmChecker)
    {
        val pref = application.getSharedPreferences(
            "ALARM_PREF",
            Context.MODE_PRIVATE
        )
        pref.edit().putBoolean(
            "reminded",
            check.isSet
        ).apply()
    }

    private fun saveReminder(cond: Boolean)
    {
        check = AlarmChecker()
        check.isSet = cond
        setAlarm(check)
    }
}