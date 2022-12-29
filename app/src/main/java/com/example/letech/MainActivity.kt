package com.example.letech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Calendar.getInstance

class MainActivity : AppCompatActivity(){

    private lateinit var blankFragment: BlankFragment
    private lateinit var frameLayout: BlankFragment2
    private lateinit var buttonPageImc: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Método usado para passar Fragment -> View.OnClickListener <-
        //buttonPageImc = findViewById(R.id.calcularImc)
        //buttonPageImc.setOnClickListener(this)

        blankFragment = BlankFragment()
        frameLayout = BlankFragment2()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.voltarPage -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.calcularImc -> {
                setFragment(blankFragment)
                supportFragmentManager.popBackStackImmediate()
                supportFragmentManager.executePendingTransactions()
                return true
            }
            R.id.teste1 -> {
                setFragment(frameLayout)
                supportFragmentManager.popBackStackImmediate()
                supportFragmentManager.executePendingTransactions()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.pageInicial, fragment)
        fragmentTransaction.commit()
        supportFragmentManager.executePendingTransactions()
        supportFragmentManager.popBackStackImmediate()
    }

    /*override fun onClick(v: View) {
        when (v.id) {
            R.id.calcularImc -> {
                setFragment(blankFragment)
                supportFragmentManager.popBackStackImmediate()
                supportFragmentManager.executePendingTransactions()
            }
        }
    }*/

    /*fun voltar(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }*/


    fun resultado(view: View) {
        try {
            var textResultado: TextView = findViewById(R.id.resultImc)
            var textPeso: EditText = findViewById(R.id.inputTextPeso)
            var textAltura: EditText = findViewById(R.id.inputTextAltura)
            var resultadoImc: TextView = findViewById(R.id.descriptionResult)

            var result = textPeso.text.toString().toDouble() / (textAltura.text.toString()
                .toDouble() * textAltura.text.toString().toDouble())
            val textFormat = DecimalFormat("#.##")
            textFormat.roundingMode = RoundingMode.CEILING

            textResultado.text = textFormat.format(result)

            if (result <= 18.5) {
                textResultado.text = textFormat.format(result)
                var text =
                    "De acordo com a Organização Mundial da Saúde, seu IMC é considerado Magreza para a sua altura. " +
                            "Procure um médico. Algumas pessoas têm um baixo peso por características do seu organismo e tudo bem. " +
                            "Outras podem estar enfrentando problemas, como a desnutrição. É preciso saber qual é o caso."
                resultadoImc.text = text

                /*val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()*/
            } else if ((result >= 18.5) && (result <= 24.9)) {
                textResultado.text = textFormat.format(result)
                var text =
                    "De acordo com a Organização Mundial da Saúde, seu IMC é considerado normal (Eutofria) para a sua altura. " +
                            " Que bom que você está com o peso normal! " +
                            "E o melhor jeito de continuar assim é mantendo um estilo de vida ativo e uma alimentação equilibrada."
                resultadoImc.text = text
            } else if ((result >= 25.0) && (result <= 29.9)) {
                textResultado.text = textFormat.format(result)
                var text =
                    "De acordo com a Organização Mundial da Saúde, seu IMC é considerado Sobrepeso para a sua altura. " +
                            "Ele é, na verdade, uma pré-obesidade e muitas pessoas nessa faixa já apresentam doenças associadas, como diabetes e hipertensão. " +
                            "Importante rever hábitos e buscar ajuda antes de, por uma série de fatores, entrar na faixa da obesidade pra valer."
                resultadoImc.text = text
            } else if ((result >= 30) && (result <= 34.9)) {
                textResultado.text = textFormat.format(result)
                var text =
                    "De acordo com a Organização Mundial da Saúde, seu IMC é considerado Obesidade grau 1 para a sua altura. " +
                            "Sinal de alerta! Chegou na hora de se cuidar, mesmo que seus exames sejam normais. " +
                            "Vamos dar início a mudanças hoje! Cuide de sua alimentação. Você precisa iniciar um acompanhamento com nutricionista e/ou endocrinologista."
                resultadoImc.text = text

            } else if ((result >= 35.0) && (result <= 39.9)) {
                textResultado.text = textFormat.format(result)
                var text =
                    "De acordo com a Organização Mundial da Saúde, seu IMC é considerado Obesidade grau 2 para a sua altura. " +
                            "Mesmo que seus exames aparentem estar normais, é hora de se cuidar, iniciando mudanças no estilo de " +
                            "vida com o acompanhamento próximo de profissionais de saúde."
                resultadoImc.text = text

            } else if (result >= 40) {
                textResultado.text = textFormat.format(result)
                var text =
                    "De acordo com a Organização Mundial da Saúde, seu IMC é considerado Obesidade grau 3 para a sua altura. " +
                            "Aqui o sinal é vermelho, com forte probabilidade de já existirem doenças muito graves associadas. " +
                            "O tratamento deve ser ainda mais urgente."
                resultadoImc.text = text

            }

        } catch (e: Exception) {
            var textResultado: TextView = findViewById(R.id.descriptionResult)
            var textPeso: EditText = findViewById(R.id.inputTextPeso)
            var textAltura: EditText = findViewById(R.id.inputTextAltura)
            var resultadoImc: TextView = findViewById(R.id.resultImc)
            val alert = AlertDialog.Builder(this)
            e.printStackTrace()

            if (textPeso.text.toString() == "" && textAltura.text.toString() == "") {
                alert.setTitle("Ocorreu um erro !!!")
                alert.setMessage("Por favor, digite seu PESO e sua Altura!!!")
                alert.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = alert.create()
                dialog.show()
                textResultado.text = ""
                resultadoImc.text = ""
            } else if (textPeso.text.toString() == "") {
                alert.setTitle("Ocorreu um erro !!!")
                alert.setMessage("Por favor, digite seu PESO")
                alert.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = alert.create()
                dialog.show()
                textResultado.text = ""
                resultadoImc.text = ""
            } else if (textAltura.text.toString() == "") {
                alert.setTitle("Ocorreu um erro !!!")
                alert.setMessage("Por favor, digite sua Altura!!!")
                alert.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = alert.create()
                dialog.show()
                textResultado.text = ""
                resultadoImc.text = ""
            }
        }
    }
}











