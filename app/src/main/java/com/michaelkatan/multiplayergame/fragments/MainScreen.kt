package com.michaelkatan.multiplayergame.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.util.Util
import kotlinx.android.synthetic.main.main_screen.view.*


class MainScreen : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.main_screen,container,false)

        view.mainScreen_singlePlayer.setOnClickListener()
        {
            //TODO add singlePlayer (And The Game!!)
            Util.makeToast(context,"WIP")
        }

        view.mainScreen_multiPlayer.setOnClickListener()
        {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_fragment_placeHolder,MultiPlayerHub())?.commit()

        }

        return view
    }
}