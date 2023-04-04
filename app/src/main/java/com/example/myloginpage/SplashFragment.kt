package com.example.myloginpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myloginpage.databinding.FragmentForgotPasswordBinding
import com.example.myloginpage.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var binding:FragmentSplashBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container ,false)
//        val fragment=SignUpFragment()
//        val transaction= fragmentManager?.beginTransaction()?.addToBackStack(fragment.tag)
//        transaction?.replace(R.id.main,fragment)?.commit()

        return binding?.root
    }

}