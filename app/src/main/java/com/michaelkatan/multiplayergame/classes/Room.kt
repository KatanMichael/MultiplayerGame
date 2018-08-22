package com.michaelkatan.multiplayergame.classes

class Room(var hostName: String, var numberOfPlayers: Int, var roomId: String)
{

    override fun toString(): String {
        return "Room(hostName='$hostName', numberOfPlayers=$numberOfPlayers, roomId='$roomId')"
    }
}