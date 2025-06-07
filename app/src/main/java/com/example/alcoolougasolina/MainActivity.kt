package com.example.alcoolougasolina

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    var percentual:Double = 0.7
    var resultado:String = "-----"

    private fun mostrarAlerta(mensagem: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Aviso")
        alertDialogBuilder.setMessage(mensagem)

        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putDouble("percentual", percentual)
        outState.putString("resultado", resultado)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val campoGasolina: EditText = findViewById(R.id.edGasolina)
        val campoAlcool: EditText = findViewById(R.id.edAlcool)
        val switchPercentual: Switch = findViewById(R.id.swPercentual)
        val btCalc: Button = findViewById(R.id.btCalcular)
        val textViewResultado: TextView = findViewById(R.id.tvResultado)

        if (savedInstanceState != null) {
            percentual=savedInstanceState.getDouble("percentual")
            resultado=savedInstanceState.getString("resultado", "-----")
        }
        Log.d("PDM23","No onCreate, $percentual, $resultado")

        textViewResultado.text = resultado

        btCalc.setOnClickListener(View.OnClickListener {
            if(campoGasolina.text.toString() == "" || campoAlcool.text.toString() == ""){
                mostrarAlerta("Preencha Todos os Campos!")
                textViewResultado.text = "-----"

            }else{
                val campoGasolinaValor = campoGasolina.text.toString().toDouble()
                val campoAlcoolValor = campoAlcool.text.toString().toDouble()

                Log.d("PDM23","No btCalcular, $percentual")
                Log.d("PDM23","Campo Gasolina, $campoGasolinaValor")
                Log.d("PDM23","Campo Álcool, $campoAlcoolValor")


                if(campoAlcoolValor <= campoGasolinaValor * percentual){
                    Log.d("PDM23","Alcool Vale a pena")
                    resultado = "Álcool"
                }else if (campoAlcoolValor > campoGasolinaValor * percentual){
                    Log.d("PDM23","Gasolina vale a pena")
                    resultado = "Gasolina"
                }
                textViewResultado.text = resultado
            }
        })

        switchPercentual.setOnClickListener{
            if(percentual == 0.7) {
                percentual = 0.75
            }else{
                percentual = 0.7
             }
        }
    }
override fun onResume(){
    super.onResume()
    Log.d("PDM23","No onResume, $percentual")
}
override fun onStart(){
    super.onStart()
    Log.d("PDM23","No onStart")
}
override fun onPause(){
    super.onPause()
    Log.d("PDM23","No onPause")
}
override fun onStop(){
    super.onStop()
    Log.d("PDM23","No onStop")
}
override fun onDestroy(){
    super.onDestroy()
    Log.d("PDM23","No onDestroy")
}
}