package com.michaelkatan.multiplayergame.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.michaelkatan.multiplayergame.R
import kotlinx.android.synthetic.main.login_fragment.view.*


class loginFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.login_fragment, container,false)

        var userName: String
        var password : String

        view.login_signInBtn.setOnClickListener()
        {
            userName = view.login_userNameET.text.toString()
            password = view.login_passwordET.text.toString()

            makeToast("user: $userName, pass: $password")
            //TODO add firebase login
        }

        view.login_signUpTV.setOnClickListener()
        {
            makeToast("SignUp")
            //TODO add signUp fragment
        }


        return view
    }

    fun makeToast(string: String)
    {
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
    }

}