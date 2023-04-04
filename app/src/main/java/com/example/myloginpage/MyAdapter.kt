import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import com.example.myloginpage.databinding.RecyclerViewBinding


class MyAdapter(private val namelist : Array<String?>? ,
                private val  emailList : Array<String?>?,
                private val imageList : Array<String?>?) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(binding: RecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName =binding.name
        val  tvEmail = binding.email1
        val image = binding.profilepicture
    }
    private lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

 return ViewHolder(RecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = namelist?.get(position)
        holder.tvEmail.text = emailList?.get(position)
        Glide.with(context).load(imageList?.get(position)).centerCrop().into(holder.image)

        }

    override fun getItemCount(): Int {
        return namelist?.size!!
    }
    }



