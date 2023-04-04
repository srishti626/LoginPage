package com.example.myloginpage

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myloginpage.api.Api
import com.example.myloginpage.api.RetrofitCLient
import com.example.myloginpage.databinding.FragmentSignUpBinding
import com.example.myloginpage.models.RegisterRequest
import com.example.myloginpage.models.RegisterResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern
import kotlin.math.log

class SignUpFragment : Fragment() {
    private var binding: FragmentSignUpBinding? = null
    private var progressBar:ProgressBar?=null


    private val passwordPattern: Pattern = Pattern.compile(
        "^" +  "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{4,}" +  //at least 4 characters
                "$"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding?.byClicking?.text = Html.fromHtml(
            "<font color=${Color.parseColor("#676767")}>By clicking the </font>" +
                    "<font color=${Color.RED}>Register </font>" +
                    "<font color=${Color.parseColor("#676767")}>button, you agree to the public offer </font>"
        )

        binding?.btnRegister?.setOnClickListener {
            if (validateFullName() and validateEmail() and validatePass() and validatePasswordConfirmation() and confirmPass()) {

                val callback = object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {

                            Log.d("hi", "onResponse: ${response.body()}")
                            val preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            preferences.edit().putBoolean("isLoggedIn", true).apply()
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            transaction?.replace(R.id.main, SignInFragment())
                            transaction?.addToBackStack(null)
                            transaction?.commit()
                        } else {
                            Toast.makeText(this@SignUpFragment.context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                            response.errorBody()?.string()?.let {
                                Log.d("Values", it) }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(this@SignUpFragment.context,t.message,Toast.LENGTH_LONG).show()
                        t.message?.let { it1 -> Log.d("val", it1) }
                    }
                }

                val userRegistration = RegisterRequest(name = binding?.username?.text.toString(), email = binding?.email?.text.toString(), password = binding?.pass?.text.toString())
                RetrofitCLient.apiInterface.signup(userRegistration).enqueue(callback)
            }
        }

        return binding?.root
    }


    private fun validateFullName(): Boolean {
        val user = binding?.username?.text.toString()

        if (user.isEmpty()) {
            binding?.username?.error = "field can't be empty"
            return false
        }
        return true
    }

    private fun confirmPass(): Boolean {
        val confirm = binding?.confirmPass?.text.toString()

        if (confirm.isEmpty()) {
            binding?.confirmPass?.error = "Field can't be empty"
            return false
        } else if (validatePasswordConfirmation()) {
            return true
        } else if (!passwordPattern.matcher(confirm).matches()) {
            binding?.confirmPass?.error = "Please enter a valid password"
            return false
        } else if (confirm.length < 6) {
            binding?.confirmPass?.error = "must be atleast 6 characters"
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val user = binding?.email?.text.toString().trim()

        if (user.isEmpty()) {
            binding?.email?.error = "Field can't be empty"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            binding?.email?.error = "Please enter a valid email address"
            return false
        }
        return true
    }

    private fun validatePass(): Boolean {
        val pass = binding?.pass?.text.toString().trim()

        if (pass.isEmpty()) {
            binding?.pass?.error = "Field can't be empty"
            return false

        } else if (!passwordPattern.matcher(pass).matches()) {
            binding?.pass?.error = "Please enter a valid password"
            return false
        } else if (pass.length < 6) {
            binding?.pass?.error = "Password should be atleast 6 characters"
        }
        return true
    }

    private fun validatePasswordConfirmation(): Boolean {
        val pass = binding?.pass?.text.toString()
        val confirm = binding?.confirmPass?.text.toString()

        if (pass != confirm) {
            // error("Entered password doesn't match")
        }
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showInvalidCredentialsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ALERT")
        builder.setMessage("Confirm Password")
        builder.setPositiveButton("OK", null)
        builder.setNegativeButton("Cancel",null)
        builder.show()
    }
}
