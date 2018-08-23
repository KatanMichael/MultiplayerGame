package com.michaelkatan.multiplayergame.util

import android.app.Notification
import android.content.Context
import android.widget.Toast
import br.com.goncalves.pugnotification.notification.PugNotification
import com.michaelkatan.multiplayergame.R


object Util
{


    fun makeToast(context: Context?, string: String)
    {

            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun makeNotification(context: Context?, title: String?,message: String, icon: Int)
    {
        PugNotification.with(context)
                .load()
                .title(title)
                .message(message)
                .smallIcon(R.drawable.navigation_empty_icon)
                .largeIcon(R.drawable.navigation_empty_icon)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();
    }

}