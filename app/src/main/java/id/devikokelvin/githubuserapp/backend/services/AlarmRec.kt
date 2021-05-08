package id.devikokelvin.githubuserapp.backend.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import id.devikokelvin.githubuserapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmRec : BroadcastReceiver()
{
    companion object
    {
        const val EXT_MSG = "EXT_MSG"
    }

    override fun onReceive(con: Context, it: Intent)
    {
        makeNotification(con)
    }

    private fun makeNotification(con: Context)
    {
        val notifManager = con.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val notifBuild = NotificationCompat.Builder(con, "ch_01")
            .setContentIntent(
                PendingIntent.getActivity(
                    con,
                    0,
                    con.packageManager
                        .getLaunchIntentForPackage(
                            "id.devikokelvin.githubuserapp"
                        ),
                    0
                )
            )
            .setSmallIcon(R.drawable.appicon)
            .setContentTitle(con.resources.getString(R.string.app_name))
            .setContentText("${R.string.notification}")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notifBuild.setChannelId("ch_01")
            notifManager.createNotificationChannel(
                NotificationChannel(
                    "ch_01",
                    "Github App Reminder",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        notifManager.notify(1, notifBuild.build())
    }

    fun repeat(con: Context, time: String, msg: String)
    {
        if (dateCheck(time))
        {
            Log.d("ERROR", "DATE_INVALID")
            return
        }
        val manager = con.getSystemService(Context.ALARM_SERVICE)
                as AlarmManager

        val arrTime = time.split(":".toRegex())
            .dropLastWhile {
                it.isEmpty()
            }.toTypedArray()

        Calendar.getInstance()
            .set(
                Calendar.HOUR_OF_DAY,
                Integer.parseInt(arrTime[0])
            )
        Calendar.getInstance()
            .set(
                Calendar.MINUTE,
                Integer.parseInt(arrTime[1])
            )
        Calendar.getInstance()
            .set(
                Calendar.SECOND, 0
            )

        manager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis,
            AlarmManager.INTERVAL_DAY,
            PendingIntent
                .getBroadcast(
                    con,
                    101,
                    Intent(con, AlarmRec::class.java)
                        .putExtra(EXT_MSG, msg),
                    0
                )
        )

        Toast.makeText(
            con,
            "${con.getString(R.string.alarmrepeat)} $time",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun dateCheck(time: String): Boolean
    {
        return try
        {
            val date = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            )
            date.isLenient = false
            date.parse(time)
            false
        }
        catch (e: ParseException)
        {
            true
        }
    }

    fun cancel(con: Context)
    {
        val manager = con.getSystemService(Context.ALARM_SERVICE)
                as AlarmManager
        val pending = PendingIntent.getBroadcast(
            con,
            101,
            Intent(
                con,
                AlarmRec::class.java
            ),
            0
        )

        pending.cancel()
        manager.cancel(pending)

        Toast.makeText(
            con,
            con.getString(R.string.alarmcancel),
            Toast.LENGTH_SHORT
        ).show()
    }
}