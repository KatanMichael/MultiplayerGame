package com.michaelkatan.multiplayergame.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.controllers.AppWarpController
import com.michaelkatan.multiplayergame.controllers.FirebaseController
import com.michaelkatan.multiplayergame.util.NotifyMsg
import com.michaelkatan.multiplayergame.util.Util
import kotlinx.android.synthetic.main.login_fragment.view.*
import java.util.*


class loginFragment : Fragment(), Observer
{

    val firebaseController = FirebaseController
    val appWarpController = AppWarpController


    val fireAuth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.login_fragment, container,false)

        appWarpController.init()
        appWarpController.addObserver(this)
        firebaseController.addObserver(this)

        view.login_signInBtn.setOnClickListener()
        {
            var userName: String
            var password : String
            userName = view.login_userNameET.text.toString()
            password = view.login_passwordET.text.toString()


            firebaseController.login(userName,password)
        }

        view.login_signUpTV.setOnClickListener()
        {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_fragment_placeHolder, signUpFragment())
                    ?.addToBackStack("signUpFragment")?.commit()
        }


        return view
    }

    override fun update(p0: Observable?, p1: Any?)
    {
        Log.d("MyApp","in the update method")

        var respond : NotifyMsg

        respond = p1 as NotifyMsg

        if(respond.targetClass.equals("signIn"))
        {
            if(respond.content.equals("false"))
            {
                Util.makeToast(context,"Login Failed")
                Log.d("MyApp","Login Failed")

            }
            if(respond.content.equals("true"))
            {
                appWarpController.loginWithEmail(view?.login_userNameET?.text.toString())
            }

            if(respond.content.equals("onConnectDone-true"))
            {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_placeHolder,MainScreen())?.commit()

            }

            if(respond.content.equals("onConnectDone-false"))
            {
                //Util.makeToast(context, "appWarp Fail")
            }
        }


    }



}

//TODO 1) get current User If still logged in!