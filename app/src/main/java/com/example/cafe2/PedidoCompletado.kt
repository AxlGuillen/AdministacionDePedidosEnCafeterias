package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class PedidoCompletado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_completado)

        val ratingBar = findViewById<RatingBar>(R.id.barCalif)
        val ratingScale = findViewById<TextView>(R.id.prueba)

        ratingBar.setOnClickListener{
            val message = ratingBar.rating.toString()
            Toast.makeText(this@PedidoCompletado, "Rating is: " + message, Toast.LENGTH_SHORT).show()
        }

        /*
        ratingBar.setOnRatingBarChangeListener{rBar, fl, b ->
            ratingScale.text = fl.toString()
            when (rBar.rating.toInt()){
                1 -> ratingScale.text = "ES UNO"
                2 -> ratingScale.text = "ES DOS"
                3 -> ratingScale.text = "ES TRES"
                4 -> ratingScale.text = "ES CUATRO"
                5 -> ratingScale.text = "ES CINCO"
                else -> ratingScale.text = ""
            }
        }*/

    }
}