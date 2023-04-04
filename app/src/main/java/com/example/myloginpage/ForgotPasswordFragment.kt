package com.example.myloginpage

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myloginpage.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {

    private var binding:FragmentForgotPasswordBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container ,false)
        binding?.weWillSen?.text = Html.fromHtml("<font color=${Color.RED}>* </font>"+
                "<font color=${Color.parseColor("#676767")}> We will send you a message to set or reset  your new password </font>")

        binding?.ellipse1?.setOnClickListener {
            if(validateEmail() ){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("ALERT DIALOG")
                builder.setMessage("VALID EMAIL ADDRESS")
                builder.setPositiveButton("OK", null)
                builder.show()
            }
            else{
                val fragment=RecyclerViewFragment()
                val transaction=fragmentManager?.beginTransaction()?.addToBackStack(fragment.tag)
                transaction?.replace(R.id.main,fragment)?.commit()
            }
        }

        return  binding?.root
    }

    private fun  validateEmail():Boolean {
        val valid = binding?.validId?.text.toString().trim()

        if (valid.isEmpty()) {
            binding?.validId?.error = "Field can't be empty"
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(valid).matches() ) {
            binding?.validId?.error="Please enter a valid email address"
            return false
        } else {
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }

}