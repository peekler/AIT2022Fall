package hu.bme.aut.aitforumdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.aitforumdemo.CreatePostActivity
import hu.bme.aut.aitforumdemo.data.Post
import hu.bme.aut.aitforumdemo.databinding.PostRowBinding

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    lateinit var context: Context
    lateinit var currentUid: String
    var  postsList = mutableListOf<Post>()
    var  postKeys = mutableListOf<String>()

    constructor(context: Context, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = postsList.get(holder.adapterPosition)
        holder.bind(post)
    }

    fun addPost(post: Post, key: String) {
        postsList.add(post)
        postKeys.add(key)
        //notifyDataSetChanged()
        notifyItemInserted(postsList.lastIndex)
    }

    // when I remove the post object
    private fun removePost(index: Int) {
        FirebaseFirestore.getInstance().collection(
            CreatePostActivity.COLLECTION_POSTS).document(
            postKeys[index]
        ).delete()

        postsList.removeAt(index)
        postKeys.removeAt(index)
        notifyItemRemoved(index)
    }

    // when somebody else removes an object
    fun removePostByKey(key: String) {
        val index = postKeys.indexOf(key)
        if (index != -1) {
            postsList.removeAt(index)
            postKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }



    inner class ViewHolder(val binding: PostRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post) {
            binding.tvAuthor.text = post.author
            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body

            if (currentUid == post.uid) {
                binding.btnDelete.visibility = View.VISIBLE
            } else {
                binding.btnDelete.visibility = View.GONE
            }

            binding.btnDelete.setOnClickListener {
                FirebaseFirestore.getInstance().collection(
                    CreatePostActivity.COLLECTION_POSTS
                ).document(
                    postKeys[adapterPosition]
                ).delete()
            }

        }
    }

}