package com.example.memesandmemes

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    private var currImg:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val next:Button=findViewById(R.id.next)
        val share:Button=findViewById(R.id.share)
        memeLoader()
        next.setOnClickListener(View.OnClickListener { view->
           memeLoader()
        })
        share.setOnClickListener(View.OnClickListener { view->
            shareMeme()
        })


    }

    private fun memeLoader(){

        val progressBar:ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->

                currImg = response.getString("url")
                Glide.with(this).load(currImg).into(findViewById(R.id.memeview))
                progressBar.visibility = View.GONE
            },
            { error ->
                // TODO: Handle error
                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
            }
        )


// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    private fun shareMeme(){
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey check this out $currImg")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "share with...."))
    }


}