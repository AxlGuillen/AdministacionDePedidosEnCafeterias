package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.signOut
import kotlinx.android.synthetic.main.activity_home.textView
import kotlinx.android.synthetic.main.activity_home.textView2

enum class ProviderType {BASIC}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)
        intent.extras
        val bundle = intent.extras

        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")
        //setup
        setup(email?:"",provider?:"")
    }

    private fun setup(email:String, provider: String) {
        title = "Inicio"
        textView.text = email
        textView2.text = provider

        signOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}