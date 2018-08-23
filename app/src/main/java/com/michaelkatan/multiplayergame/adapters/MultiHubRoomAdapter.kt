package com.michaelkatan.multiplayergame.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelkatan.multiplayergame.classes.Room
import kotlinx.android.synthetic.main.multi_hub_room_row.view.*
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.controllers.AppWarpController


class MultiHubRoomAdapter(val listOfRoom: ArrayList<Room>, val clickListener: View.OnClickListener,
                          context: Context?) : RecyclerView.Adapter<MultiHubRoomAdapter.RoomViewHolder>()
{

    val appWarpController = AppWarpController
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiHubRoomAdapter.RoomViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.multi_hub_room_row,parent,false)

        return RoomViewHolder(view)
    }

    override fun getItemCount(): Int {

        return listOfRoom.size
    }

    override fun onBindViewHolder(holder: MultiHubRoomAdapter.RoomViewHolder, position: Int)
    {
        val room = listOfRoom[position]
        holder.multiHubHostName.text = room.hostName

        holder.multiHubJoinBtn.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(p0: View?)
            {
                appWarpController.joinRoomWithId(room.roomId)
            }

        })

    }

    inner class RoomViewHolder(val myView: View): RecyclerView.ViewHolder(myView)
    {
        val multiHubJoinBtn = myView.multiHub_room_joinBtn
        val multiHubHostName = myView.multiHub_room_hostName

    }

}