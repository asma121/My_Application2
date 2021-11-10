package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var rv:RecyclerView
    lateinit var etNumber:EditText
    lateinit var buGetPost:Button
    lateinit var textView: TextView
    lateinit var posts: Post
    lateinit var post: PostItem
    lateinit var post1: PostItem
    lateinit var newPost: PostItem
    lateinit var newPost1: PostItem
    lateinit var newPostRv:ArrayList<PostItem>
    lateinit var postsrv:ArrayList<PostItem>
    var postById: PostItem? =null
    val apiInterface= APIClient().getClinet()?.create(APIInterface::class.java)
    val call:Call<Post>? = apiInterface?.getPosts()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv=findViewById(R.id.rv)
        etNumber=findViewById(R.id.etNumber)
        buGetPost=findViewById(R.id.buGetPost)
        textView=findViewById(R.id.textView)

        postsrv= arrayListOf()
        newPostRv= arrayListOf()
        newPost= PostItem("hello this is me",101,"new post",1)
        newPost1= PostItem("hello this is meِ Asmaa",101,"new post",1)

       // getPosts()
        //addPost(newPost)


        buGetPost.setOnClickListener {
            val id =etNumber.text.toString().toInt()
            //deletPost(id)
            //updatePost(id,newPost1)
           // textView.text=post1.body
           GlobalScope.launch {
               //getPostById(id)
               // getPostsByPostId(id)
               postsrv= getPostsByPostId(id)

                withContext(Dispatchers.Main){
                 rv.adapter=RecyclerViewAdapter(postsrv)
                 rv.layoutManager=LinearLayoutManager(this@MainActivity)
                rv.adapter?.notifyDataSetChanged()
                }
            }
            Toast.makeText(this,"show ",Toast.LENGTH_LONG).show()
        }

    }

    fun getPosts(){
       call?.enqueue(object : Callback<Post>{
           override fun onResponse(call: Call<Post>, response: Response<Post>) {
                posts= response.body()!!
               for (p in posts){
                  postsrv.add(p)
               }
               rv.adapter=RecyclerViewAdapter(postsrv)
               rv.layoutManager=LinearLayoutManager(this@MainActivity)
               rv.adapter?.notifyDataSetChanged()
           }

           override fun onFailure(call: Call<Post>, t: Throwable) {
               Log.d("MainActivity","$t")
           }
       })
    }

     suspend fun getPostById(id:Int){
        val call:Call<PostItem>? = apiInterface?.getPostById(id)
        call?.enqueue(object : Callback<PostItem>{
            override fun onResponse(call: Call<PostItem>, response: Response<PostItem>) {
                postById=response!!.body()
                //textView.text= postById?.title?.toString()
            }

            override fun onFailure(call: Call<PostItem>, t: Throwable) {
                Log.d("MainActivity","$t")
            }
        })
    }

      fun getPostsByPostId(id:Int): ArrayList<PostItem> {
          val call: Call<Post>? = apiInterface?.getPostsByPostId(id)
          call?.enqueue(object : Callback<Post> {
              override fun onResponse(call: Call<Post>, response: Response<Post>) {
                  posts = response.body()!!
                  for (p in posts) {
                      postsrv.add(p)
                  }
              }

              override fun onFailure(call: Call<Post>, t: Throwable) {
                  Log.d("MainActivity", "$t")
              }

          })

          return postsrv
      }

    fun addPost(postItem: PostItem){
        val call: Call<PostItem>? = apiInterface?.addPost(postItem)
        call?.enqueue(object : Callback<PostItem>{
            override fun onResponse(call: Call<PostItem>, response: Response<PostItem>) {
                post=response.body()!!
                val title=post?.body
                textView.text=title
            }

            override fun onFailure(call: Call<PostItem>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updatePost( id: Int ,postItem: PostItem){
        val call: Call<PostItem>? = apiInterface?.updatePost(id,postItem)
        call?.enqueue(object : Callback<PostItem>{
            override fun onResponse(call: Call<PostItem>, response: Response<PostItem>) {
                post1=response.body()!!
            }

            override fun onFailure(call: Call<PostItem>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deletPost(id: Int){
        val call: Call<Void>? = apiInterface?.deletePost(id)
        call?.enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(applicationContext,"Post Deleted", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}