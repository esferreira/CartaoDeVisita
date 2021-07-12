package br.com.ecioferreira.cartaovisita.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.ecioferreira.cartaovisita.App
import br.com.ecioferreira.cartaovisita.R
import br.com.ecioferreira.cartaovisita.data.CartaoVisita
import br.com.ecioferreira.cartaovisita.databinding.ActivityAddCartaoVisitaBinding

class AddCartaoVisitaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddCartaoVisitaBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
    }

    private fun insertListeners() {
        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            val cartaoVisita = CartaoVisita(
                nome = binding.tilNome.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                linkedin = binding.tilLinkedin.editText?.text.toString(),
                instagram = binding.tilInstagram.editText?.text.toString(),
                youtube = binding.tilYoutube.editText?.text.toString(),
                whatsapp = binding.tilWhatsapp.editText?.text.toString(),
                fundoPersonalizado = "#CCCDDD"
            )

            if (!cartaoVisita.email.isNullOrEmpty()) {
                try {
                    if (cartaoVisita.whatsapp.length <= 9) {
                        binding.tilWhatsapp.editText?.setText("+55DDD"+cartaoVisita.whatsapp)
                        Toast.makeText(
                            this,
                            "Atenção! Corrigir DDD do Whatsapp.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        mainViewModel.insert(cartaoVisita)
                        Toast.makeText(this, R.string.label_show_sucess, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    getLocalizeEmailCartaoVisita(cartaoVisita.email)
                }

            } else {
                binding.tilEmail.editText?.setText("Email Obrigatório!")
            }

        }

        binding.btnColor.setOnClickListener {
            val intent = Intent(this@AddCartaoVisitaActivity, ColorCartaoVisitaActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getLocalizeEmailCartaoVisita(email: String) {
        mainViewModel.getUser(email).observe(this, { cartaoVisita ->
            cartaoVisita.forEach {
                if (binding.tilEmail.editText?.text.toString() == it.email) {
                    Toast.makeText(
                        this,
                        it.email + " já foi cadastrado em outro Cartão de Visita. Verifique!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}