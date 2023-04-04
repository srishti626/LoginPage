package com.example.myloginpage

import MyAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myloginpage.api.RetrofitCLient
import com.example.myloginpage.databinding.FragmentRecyclerViewBinding
import com.example.myloginpage.models.GetUser
import com.example.myloginpage.models.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewFragment : Fragment() {
    private var binding: FragmentRecyclerViewBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)

        binding?.recycler?.layoutManager= LinearLayoutManager(context)

        val apiService = RetrofitCLient.apiInterface
//
        val token = getData()

        Log.d("abcd", token.toString())

        val call = apiService.getUsers("Bearer $token")

        call.enqueue(object  : Callback<GetUserResponse>{
            override fun onResponse(call: Call<GetUserResponse>, response: Response<GetUserResponse>) {


                val list : ArrayList<GetUser>? = response.body()?.users

                val id = list?.let { arrayOfNulls<String>(it.size) }
                val createdat = list?.let { arrayOfNulls<String>(it.size) }
                val email = list?.let { arrayOfNulls<String>(it.size) }
                val location = list?.let { arrayOfNulls<String>(it.size) }
                val name = list?.let { arrayOfNulls<String>(it.size) }
                val profilepicture = list?.let { arrayOfNulls<String>(it.size) }


                Log.d("fetch1", response.body()?.users.toString())
               Log.d("fetch2", response.code().toString())
                for (i in list!!.indices){
                    Log.d("fetch3", list[i].toString())
                   id?.set(i, list[i].id.toString())
                   createdat?.set(i, list[i].createdat)
                    email?.set(i, list[i].email)
                    location?.set(i, list[i].location)
                    name?.set(i, list[i].name)
                    profilepicture?.set(i, list[i].profilepicture)

               }
                val adapter = MyAdapter(id,email, profilepicture)
                binding?.recycler?.adapter = adapter
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                Toast.makeText(this@RecyclerViewFragment.context, t.message, Toast.LENGTH_LONG).show()
                Log.d("error1", t.message.toString())
            }

        })
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    private  fun getData() : String? {
        val sharedPreference = activity?.getSharedPreferences("Token1", Context.MODE_PRIVATE)
        return sharedPreference?.getString("TokenKey", null)
    }
}
