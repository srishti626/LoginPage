package com.example.myloginpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replace(SplashFragment())

        Handler(Looper.getMainLooper()).postDelayed({
            if (getData()){
                replace(RecyclerViewFragment())
            }
            else{
                replace(SignInFragment())
            }
        },3000)
    }
    private  fun replace(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction().replace(R.id.main, fragment).addToBackStack(fragment.tag)
        transaction.commit()
    }
    private  fun getData() : Boolean {
        val preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return preferences.getBoolean("isLoggedIn",false)
    }
}