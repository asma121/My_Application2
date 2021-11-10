package com.example.myapplication2

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/posts/")
    fun getPosts():Call<Post>

    @GET("/posts/{id}")
    fun getPostById(@Path("id") id:Int):Call<PostItem>

    @GET("/comments/")
    fun getPostsByPostId(@Query("postId") postId:Int): Call<Post>

    @POST("/posts/")
    fun addPost(@Body post:PostItem) : Call<PostItem>

    @PUT("/posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: PostItem) : Call<PostItem>

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id:Int) : Call<Void>

}