package com.michaelkatan.multiplayergame.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.adapters.MultiHubRoomAdapter
import com.michaelkatan.multiplayergame.classes.Room
import com.michaelkatan.multiplayergame.controllers.AppWarpController
import com.michaelkatan.multiplayergame.controllers.FirebaseController
import com.michaelkatan.multiplayergame.util.NotifyMsg
import com.michaelkatan.multiplayergame.util.Util
import kotlinx.android.synthetic.main.multiplayer_hub_fragment.*
import java.util.*
import kotlin.collections.ArrayList


class MultiPlayerHub: Fragment(), View.OnClickListener, Observer
{


    var roomsList = ArrayList<Room>()
    var roomAdapter = MultiHubRoomAdapter(roomsList,this,activity?.applicationContext)


    val appWarpController = AppWarpController
    val fireBaseController = FirebaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.multiplayer_hub_fragment, container,false)

        appWarpController.addObserver(this)
        fireBaseController.addObserver(this)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val roomAdapter = MultiHubRoomAdapter(roomsList,this,activity?.applicationContext)



        multiHub_recyclerView.adapter = roomAdapter
        multiHub_recyclerView.layoutManager =  LinearLayoutManager(context)


        multiHub_hostGame.setOnClickListener()
        {
            appWarpController.hostNewRoom("MobileRoom")
        }

        multiHub_refresh.setOnClickListener()
        {
            appWarpController.getRoomList()
        }

    }

    override fun update(p0: Observable?, p1: Any?)
    {
        val msgNotif = p1 as NotifyMsg

        if(msgNotif.targetClass.equals("roomList-hub"))
        {
            val listOfRoom = msgNotif.content as ArrayList<Room>

            roomsList.clear()
            roomAdapter.notifyDataSetChanged()

            if(listOfRoom.size > 0)
            {
                for(room in listOfRoom)
                {
                    Log.d("Simon","${room} Was Added")
                    roomsList.add(room)
                    roomAdapter.notifyDataSetChanged()
                }
            }

        }

        if(msgNotif.targetClass.equals("multi-hub-createRoom"))
        {
            val roomId = msgNotif.content as String
            Log.d("Simon","Room Created")
            appWarpController.joinRoomWithId(roomId)
        }

        if(msgNotif.targetClass.equals("multi-hub-joinRoom"))
        {
            val result : Integer = msgNotif.content as Integer

            if(result.equals(1))
            {
                Log.d("Simon","room joined")

            }else
            {
                Log.d("Simon","room joined error")
            }
        }

    }


    override fun onClick(p0: View?)
    {


    }

    inner class refreshAdapter : AsyncTask<ArrayList<Room>, Int, Int>()
    {
        override fun doInBackground(vararg listOfRooms: ArrayList<Room>?): Int
        {

            roomsList.clear()

            return 1
        }



        override fun onProgressUpdate(vararg values: Int?)
        {
            roomAdapter.notifyDataSetChanged()
        }

        override fun onPostExecute(result: Int?)
        {

        }
    }

}