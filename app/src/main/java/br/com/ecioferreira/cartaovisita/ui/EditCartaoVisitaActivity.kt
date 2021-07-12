package br.com.ecioferreira.cartaovisita.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.ecioferreira.cartaovisita.App
import br.com.ecioferreira.cartaovisita.R
import br.com.ecioferreira.cartaovisita.data.CartaoVisita
import br.com.ecioferreira.cartaovisita.databinding.ActivityEditCartaoVisitaBinding
import kotlinx.android.synthetic.main.activity_add_cartao_visita.*

class EditCartaoVisitaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditCartaoVisitaBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private var idUser:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Obter valor do Intent que veio pelo botão Editar
        val email: String = intent.getStringExtra("email").toString()
        getUserCartaoVisita(email)
        updateListeners()
    }

    private fun updateListeners() {
        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            val cartaoVisita = CartaoVisita(
                id = idUser,
                nome = binding.tilNome.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                linkedin = binding.tilLinkedin.editText?.text.toString(),
                instagram = binding.tilInstagram.editText?.text.toString(),
                youtube = binding.tilYoutube.editText?.text.toString(),
                whatsapp = binding.tilWhatsapp.editText?.text.toString(),
                fundoPersonalizado = "#CCCCCC"
            )
            mainViewModel.update(cartaoVisita)
            Toast.makeText(this, R.string.label_show_edit_sucess, Toast.LENGTH_SHORT).show()
        }

        binding.btnColor.setOnClickListener {
            val intent =
                Intent(this@EditCartaoVisitaActivity, ColorCartaoVisitaActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getUserCartaoVisita(email: String) {
        mainViewModel.getUser(email).observe(this, { cartaoVisita ->
            cartaoVisita.forEach {
                idUser = it.id
                binding.tilNome.editText?.setText(it.nome)
                binding.tilTelefone.editText?.setText(it.telefone)
                binding.tilEmail.editText?.setText(it.email)
                binding.tilEmpresa.editText?.setText(it.empresa)
                binding.tilLinkedin.editText?.setText(it.linkedin)
                binding.tilInstagram.editText?.setText(it.instagram)
                binding.tilYoutube.editText?.setText(it.youtube)
                binding.tilWhatsapp.editText?.setText(it.whatsapp)
            }
        })
    }
}
