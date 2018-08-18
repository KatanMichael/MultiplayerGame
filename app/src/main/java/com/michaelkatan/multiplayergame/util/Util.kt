package com.michaelkatan.multiplayergame.util

import android.content.Context
import android.widget.Toast

object Util
{
    public fun makeToast(context: Context?, string: String)
    {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

}