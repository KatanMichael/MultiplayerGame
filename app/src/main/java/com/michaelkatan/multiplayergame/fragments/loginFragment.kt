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
import com.michaelkatan.multiplayergame.controllers.FirebaseController
import kotlinx.android.synthetic.main.login_fragment.view.*
import java.util.*


class loginFragment : Fragment(), Observer
{

    val firebaseController = FirebaseController

    val fireAuth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.login_fragment, container,false)

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

        var respond : String?

        respond = p1 as String?

        if(respond.equals("signIn-false"))
            {
                makeToast("Login Failed")
                Log.d("MyApp","Login Failed")

            }else if(respond.equals("signIn-true"))
            {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_placeHolder,MainScreen())?.commit()
                Log.d("MyApp","userFound")

            }
    }

    fun makeToast(string: String)
    {
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
    }


}