package com.michaelkatan.multiplayergame.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.adapters.MultiHubRoomAdapter
import com.michaelkatan.multiplayergame.classes.Room
import kotlinx.android.synthetic.main.multiplayer_hub_fragment.*


class MultiPlayerHub: Fragment(), View.OnClickListener
{

    val roomsList = ArrayList<Room>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.multiplayer_hub_fragment, container,false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val roomAdapter = MultiHubRoomAdapter(roomsList,this,activity?.applicationContext)

        multiHub_recyclerView.adapter = roomAdapter
        multiHub_recyclerView.layoutManager =  LinearLayoutManager(context)


    }

    override fun onClick(p0: View?)
    {

    }

}