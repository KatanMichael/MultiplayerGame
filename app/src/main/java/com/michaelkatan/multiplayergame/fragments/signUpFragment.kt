package com.michaelkatan.multiplayergame.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.michaelkatan.multiplayergame.R
import com.michaelkatan.multiplayergame.controllers.AppWarpController
import com.michaelkatan.multiplayergame.controllers.FirebaseController
import com.michaelkatan.multiplayergame.util.NotifyMsg
import com.michaelkatan.multiplayergame.util.Util
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.view.*
import java.util.*


class signUpFragment : Fragment(), Observer
{

    val fireAuth = FirebaseAuth.getInstance()
    val appWarpController = AppWarpController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        val firebaseController = FirebaseController

        appWarpController.addObserver(this)
        firebaseController.addObserver(this)

        var fullName : String
        var userName : String
        var password : String
        var email : String

        view.signUp_submitBtn.setOnClickListener()
        {
            fullName = signUp_fullName.text.toString()
            userName = signUp_userName.text.toString()
            email = signUp_email.text.toString()
            password = signUp_password.text.toString()


            firebaseController.signUp(email,password)

        }

        return view
    }

    override fun update(p0: Observable?, p1: Any?)
    {
        Log.d("MyApp","in the update method")

        var respond : NotifyMsg

        respond = p1 as NotifyMsg

        if(respond.targetClass.equals("signUp"))
        {
            if(respond.equals("signUp-false"))
            {
                Util.makeToast( context,"Registration Failed")
                Log.d("MyApp","Registration Failed")

            }
            if(respond.equals("signUp-true"))
            {
                appWarpController.loginWithEmail(signUp_email.text.toString())
            }
        }


        if(respond.targetClass.equals("signIn"))
        {
            if(respond.content.equals("onConnectDone-true"))
            {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_placeHolder,MainScreen())?.commit()

            }

            if(respond.content.equals("onConnectDone-false"))
            {
                Util.makeToast(context , "appWarp Fail")
            }
        }


    }

}


