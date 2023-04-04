package com.example.myloginpage

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myloginpage.api.RetrofitCLient
import com.example.myloginpage.databinding.FragmentSignInBinding
import com.example.myloginpage.models.LoginRequest
import com.example.myloginpage.models.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null
    private var progressBar: ProgressBar?=null

    private val passwordPattern: Pattern = Pattern.compile(
        "^" +  //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
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
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding?.forgotPass?.setOnClickListener{
            val third = ForgotPasswordFragment()
            val transaction = fragmentManager?.beginTransaction()?.addToBackStack(third.tag)
            transaction?.replace(R.id.main,third)?.commit()
        }

        binding?.register?.setOnClickListener{
            val third = SignUpFragment()
            val transaction = fragmentManager?.beginTransaction()?.addToBackStack(third.tag)
            transaction?.replace(R.id.main,third)?.commit()
        }

        binding?.ellipse1?.setOnClickListener {
            if (validateEmail() and validatePass()) {
                val apiService= RetrofitCLient.apiInterface

                val callback = object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {

                        if (response.isSuccessful) {
                            if(response.body()?.code.toString().equals("1")){
                             Toast.makeText(activity,response.body()?.message,Toast.LENGTH_SHORT).show()
                                return
                            }

                            try {
                                val sharedPreference = activity?.getSharedPreferences("Token1", Context.MODE_PRIVATE)
                                val editor = sharedPreference?.edit()
                                editor.apply { editor?.putString("TokenKey", response.body()?.data?.Token)
                                }?.apply()
                                response.body()?.data?.Token?.let { it1 -> Log.d("Token1", it1) }

                                val token = sharedPreference?.getString("token","")
                                Log.e("val of token",response.body()?.data?.Token.toString())

                            }catch (e: java.lang.Exception){ }

                            Log.e("val of token2", response.body()?.data?.Token.toString())


                            val second = RecyclerViewFragment()
                            val transaction = fragmentManager?.beginTransaction()?.addToBackStack(second.tag)
                            transaction?.replace(R.id.main, second)?.commit()

                        } else {
                            Toast.makeText(this@SignInFragment.context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                            response.errorBody()?.string()?.let {
                                Log.d("Values", it) }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(this@SignInFragment.context,t.message,Toast.LENGTH_LONG).show()
                    }
                }

                val userRegistration = LoginRequest(email = binding?.email?.text.toString(), password = binding?.pass?.text.toString())
                apiService.signin(userRegistration).enqueue(callback)
            }
        }
        return  binding?.root
}

    private fun  validateEmail():Boolean {
        val user = binding?.email?.text.toString().trim()

        if (user.isEmpty()) {
            binding?.email?.error = "Field can't be empty"
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches() ) {
            binding?.email?.error="Please enter a valid email address"
            return false
        } else {
            return true
        }
    }


    private fun validatePass() :Boolean{
        val pass = binding?.pass?.text.toString().trim()

        if(pass.isEmpty()){
            binding?.pass?.error="Field can't be empty"
            return false

        }
        else if(!passwordPattern.matcher(pass).matches()){
            binding?.pass?.error="Please enter a valid password"
            return false
        }
        else{
            return true
        }
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

    private fun getToken() : String? {
        val sharedPreferences = activity?.getSharedPreferences("userDetails", Context.MODE_PRIVATE)

        //input = sharedPreferences.getString("LoginCred", null).toString()
        return  sharedPreferences?.getString("TokenKey", null)
    }
    }

