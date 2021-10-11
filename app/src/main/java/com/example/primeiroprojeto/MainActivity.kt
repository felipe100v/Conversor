package com.example.primeiroprojeto

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var result: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        result= findViewById<TextView>(R.id.txt_resultado)

        val buttonConverter = findViewById<Button>(R.id.btn_converter)

        buttonConverter.setOnClickListener {
          converter()
        }

    }
    @SuppressLint("SetTextI18n")
    private fun converter () {

        val selectCurrency = findViewById<RadioGroup>(R.id.radio_group)

        val checked = selectCurrency.checkedRadioButtonId

      val currency = when(checked){
            R.id.radio_usd -> {
             "USD"
            }
            R.id.radio_eur ->{
                "EUR"
            }
            else -> {
            "CLP"
            }
        }
        val editField= findViewById<EditText>(R.id.edit_field)

        val value = editField.text.toString()

        if(value.isEmpty())
            return


            result.text = value

            result.visibility = View.VISIBLE

        Thread {

        val url = URL("https://free.currconv.com/api/v7/convert?q=${currency}_BRL&compact=ultra&apiKey=782daf9ce6f7d4b5c9d7")

            val conexao = url.openConnection() as HttpURLConnection


          try {


              val data = conexao.inputStream.bufferedReader().readText()

              val obj= JSONObject(data)

             runOnUiThread {

                 val res= obj.getDouble("${currency}_BRL")

                 result.text = "R$${ "%.4f".format(value.toDouble() * res)}"
                 result.visibility = View.VISIBLE
             }

          }finally {
              conexao.disconnect()
          }


        }.start()
    }




}