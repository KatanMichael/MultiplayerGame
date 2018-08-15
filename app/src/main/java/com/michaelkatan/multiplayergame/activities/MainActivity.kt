package com.michaelkatan.multiplayergame.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.fragments.loginFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_placeHolder,loginFragment())
                .commit()

    }
}
