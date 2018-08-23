package com.michaelkatan.multiplayergame.controllers

import android.util.Log
import com.michaelkatan.multiplayergame.classes.Room
import com.michaelkatan.multiplayergame.util.NotifyMsg
import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode
import com.shephertz.app42.gaming.multiplayer.client.events.*
import com.shephertz.app42.gaming.multiplayer.client.listener.*
import java.util.*
import kotlin.collections.ArrayList

object AppWarpController : Observable(), ConnectionRequestListener, RoomRequestListener,
        ZoneRequestListener, LobbyRequestListener, UpdateRequestListener,
        ChatRequestListener, NotifyListener, TurnBasedRoomListener {

    private val apiKey = "ec45835d8fd58440eabc5e954e6f206e28e51789af60b86873ee6b22ff02a57b"
    private val secretKey = "f0c8085432fc9bf6e4f8589553c576645aa77992c1f2c6668ffb8ee092f10062"
    var warpClient : WarpClient? = null


    fun init()
    {
        WarpClient.initialize(apiKey, secretKey)
        warpClient = WarpClient.getInstance()

        setUp()
    }
    private fun setUp()
    {
        warpClient?.addLobbyRequestListener(this)
        warpClient?.addChatRequestListener(this)
        warpClient?.addZoneRequestListener(this)
        warpClient?.addRoomRequestListener(this)
        warpClient?.addConnectionRequestListener(this)
        warpClient?.addNotificationListener(this)
        warpClient?.addTurnBasedRoomListener(this)
        warpClient?.addUpdateRequestListener(this)
    }

    fun loginWithEmail(userName: String)
    {
        warpClient!!.connectWithUserName(userName)
    }

    fun getRoomList()
    {
        warpClient?.getRoomInRange(1,5)
    }

    fun hostNewRoom(roomName: String)
    {
        warpClient?.createRoom(roomName,roomName,2,null)
    }

    fun joinRoomWithId(roomId: String)
    {
        warpClient?.joinRoom(roomId)
    }


    //-----ConnectionRequestListener---////
    override fun onDisconnectDone(p0: ConnectEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectDone(event: ConnectEvent?)
    {
        if (event != null)
        {
            if(event.result == WarpResponseResultCode.SUCCESS)
            {

                warpClient?.joinLobby()

            }else
            {
                setChanged()
                notifyObservers(NotifyMsg("signIn","onConnectDone-false"))
            }


        }

    }

    override fun onInitUDPDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //-----ConnectionRequestListener---////


    ///---RoomRequestListener-----////
    override fun onUnSubscribeRoomDone(p0: RoomEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetLiveRoomInfoDone(p0: LiveRoomInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLockPropertiesDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUnlockPropertiesDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLeaveAndUnsubscribeRoomDone(p0: RoomEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdatePropertyDone(p0: LiveRoomInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLeaveRoomDone(p0: RoomEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onJoinAndSubscribeRoomDone(p0: RoomEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubscribeRoomDone(p0: RoomEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onJoinRoomDone(joinRoomEvent: RoomEvent?)
    {
        if(joinRoomEvent != null)
        {
            warpClient?.startGame()
        }
    }

    override fun onSetCustomRoomDataDone(p0: LiveRoomInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    ///---RoomRequestListener-----////


    ///-----------ZoneRequestListener---------------///
    override fun onGetUserStatusDone(p0: LiveUserInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetLiveUserInfoDone(p0: LiveUserInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetAllRoomsCountDone(p0: AllRoomsEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteRoomDone(p0: RoomEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetAllRoomsDone(roomEvent: AllRoomsEvent?)
    {
        if(roomEvent != null)
        {
            val listOfRooms =  ArrayList<Room>(roomEvent.roomsCount)
            val roomIdArray = roomEvent.roomIds

            Log.d("Simon","Number of Rooms(Controller) : ${roomEvent.roomsCount}")

            if(roomEvent.roomsCount == 0)
            {
                setChanged()
                notifyObservers(NotifyMsg("roomList-hub", listOfRooms))

            }else
            {
                for(i in 0..roomIdArray.size)
                {
                    listOfRooms[i].roomId = roomIdArray[i]
                }

                setChanged()
                notifyObservers(NotifyMsg("roomList-hub", listOfRooms))
            }
        }

    }

    override fun onGetOnlineUsersCountDone(p0: AllUsersEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetMatchedRoomsDone(roomEvent: MatchedRoomsEvent?)
    {

        if(roomEvent != null)
        {
            var listOfRooms =  ArrayList<Room>()


            Log.d("Simon","Number of Rooms(Controller) : ${roomEvent.roomsData.size}")

            if(roomEvent.roomsData.size == 0)
            {
                setChanged()
                notifyObservers(NotifyMsg("roomList-hub", listOfRooms))

            }else
            {
                val size = roomEvent.roomsData.size
                Log.d("Simon","Size (Controller): $size")


                for(i in 0..size-1)
                {
                    val hostName = roomEvent.roomsData[i].id

                    listOfRooms.add(Room(hostName,1,hostName))
                }

                setChanged()
                notifyObservers(NotifyMsg("roomList-hub", listOfRooms))
            }
        }
    }

    override fun onGetOnlineUsersDone(p0: AllUsersEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateRoomDone(createRoomEvent: RoomEvent?)
    {
        if(createRoomEvent != null)
        {
            setChanged()
            notifyObservers(NotifyMsg("multi-hub-createRoom",createRoomEvent.data.id))
        }

    }

    override fun onSetCustomUserDataDone(p0: LiveUserInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    ///-----------ZoneRequestListener---------------///


    /////-------------LobbyRequestListener------------////////
    override fun onJoinLobbyDone(p0: LobbyEvent?)
    {
        setChanged()
        notifyObservers(NotifyMsg("signIn","onConnectDone-true"))

    }

    override fun onSubscribeLobbyDone(p0: LobbyEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLeaveLobbyDone(p0: LobbyEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUnSubscribeLobbyDone(p0: LobbyEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetLiveLobbyInfoDone(p0: LiveRoomInfoEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /////-------------LobbyRequestListener------------////////

    ////---------UpdateRequestListener--------------////////
    override fun onSendUpdateDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSendPrivateUpdateDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    ////---------UpdateRequestListener--------------////////

    ////--------------ChatRequestListener----//////////
    override fun onSendChatDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSendPrivateChatDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetChatHistoryDone(p0: Byte, p1: ArrayList<ChatEvent>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    ////--------------ChatRequestListener----//////////


    /////------NotifyListener----///////////
    override fun onRoomDestroyed(p0: RoomData?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGameStopped(p0: String?, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserJoinedRoom(roomData: RoomData?, userName: String?)
    {

    }

    override fun onPrivateChatReceived(p0: String?, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserResumed(p0: String?, p1: Boolean, p2: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrivateUpdateReceived(p0: String?, p1: ByteArray?, p2: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRoomCreated(p0: RoomData?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNextTurnRequest(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserLeftRoom(p0: RoomData?, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGameStarted(p0: String?, p1: String?, p2: String?)
    {
        setChanged()
        notifyObservers(NotifyMsg("multi-hub-joinRoom",Integer(1)))
    }

    override fun onChatReceived(p0: ChatEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdatePeersReceived(p0: UpdateEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserChangeRoomProperty(p0: RoomData?, p1: String?, p2: HashMap<String, Any>?, p3: HashMap<String, String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserLeftLobby(p0: LobbyData?, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMoveCompleted(p0: MoveEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserPaused(p0: String?, p1: Boolean, p2: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserJoinedLobby(p0: LobbyData?, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /////------NotifyListener----///////////

    //-------TurnBasedRoomListener-------///////
    override fun onStartGameDone(p0: Byte)
    {
        setChanged()
        notifyObservers(NotifyMsg("multi-hub-joinRoom",Integer(1)))
    }

    override fun onGetGameStatusDone(p0: Byte, p1: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSetNextTurnDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSendMoveDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopGameDone(p0: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetMoveHistoryDone(p0: Byte, p1: Array<out MoveEvent>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //-------TurnBasedRoomListener-------///////


}