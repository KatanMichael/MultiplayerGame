package com.michaelkatan.multiplayergame.controllers

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.michaelkatan.multiplayergame.util.NotifyMsg
import java.util.*

object FirebaseController: Observable()
{
    val fireAuth = FirebaseAuth.getInstance()

    var currantUser: FirebaseUser? = null

    fun login(email: String, password: String)
    {
        var user : FirebaseUser? = null
         fireAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult>
                {
                    override fun onComplete(task: Task<AuthResult>)
                    {
                        if(task.isSuccessful)
                        {
                            user = task.result.user
                            setChanged()
                            notifyObservers(NotifyMsg("signIn","true"))
                            currantUser = user

                        }else
                        {
                            setChanged()
                            notifyObservers(NotifyMsg("signIn","false"))

                        }
                    }

                })

    }

    fun signUp(email: String, password: String)
    {


        var user : FirebaseUser?

            fireAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(object : OnCompleteListener<AuthResult>
                    {
                        override fun onComplete(task: Task<AuthResult>)
                        {
                            if(task.isSuccessful)
                            {
                                user = task.result.user
                                setChanged()
                                notifyObservers(NotifyMsg("signUp","signUp-true"))
                                currantUser = user

                            }else
                            {
                                Log.d("MyApp",task.result.toString())
                                Log.d("MyApp",task.exception.toString())

                                setChanged()
                                notifyObservers(NotifyMsg("signUp","signUp-false"))
                            }
                        }

                    })

    }


}
