package br.com.ecioferreira.cartaovisita.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.ecioferreira.cartaovisita.App
import br.com.ecioferreira.cartaovisita.R
import br.com.ecioferreira.cartaovisita.databinding.ActivityMainBinding
import br.com.ecioferreira.cartaovisita.util.Image
import kotlinx.android.synthetic.main.activity_add_cartao_visita.view.*
import kotlinx.android.synthetic.main.item_cartao_visita.view.*
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }
    private val adapter by lazy { CartaoVisitaAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvCards.adapter = adapter
        getAllCartaoVisita()
        insertListener()

    }

    private fun insertListener() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCartaoVisitaActivity::class.java)
            startActivity(intent)
        }
        // Capturar print do cartão de visita
        adapter.listenerShare = { card -> Image.share(this@MainActivity, card) }

        // Acessar redes sociais do cartão de visita com um clique
        adapter.listenerShareLinkedin =
            { card -> getUrlFromIntent("https://www.linkedin.com/in/" + card.getTag()) }
        adapter.listenerShareInstagram =
            { card -> getUrlFromIntent("https://www.instagram.com/" + card.getTag()) }
        adapter.listenerShareYoutube =
            { card -> getUrlFromIntent("https://www.youtube.com/" + card.getTag()) }
        adapter.listenerShareWhatsApp =
            { card ->
                getUrlFromIntent(
                    "https://api.whatsapp.com/send?phone=" + card.getTag() + "&text=Olá, gostaria de obter mais informações sobre seus serviços!"
                )
            }

        // Compartilhar redes sociais do cartão de visita com um clique longo
        adapter.listener_Share = { card: View ->
            //SOLUÇÃO PARA ATUALZIAR UM REGISTRO
            val opcoes = listOf("Editar", "Excluir")

            val opc_editar = 0;
            val opc_excluir = 1;

            selector("Escolha uma opção:", opcoes) { dialogInterface, position ->
                when (position) {
                    opc_editar -> {
                        //buscando o item clicado
                        val item = (card.tv_email.text)
                        val intent = Intent(this, EditCartaoVisitaActivity::class.java)
                        if (item != null) {
                            intent.putExtra("email", item.toString())
                        }
                        startActivity(intent)
                    }

                    opc_excluir -> {
                        //buscando o item clicado
                        val item = (card.tv_email.text)

                        //deletando do banco de dados
                        if (item != null) {
                            getDeleteCartaoVisita(item.toString())
                            toast("Cartão de Visita deletado com sucesso!")
                            getAllCartaoVisita()
                            onResume()
                        }
                    }
                }
            }
            true
        }

        adapter.listenerShare_Linkedin =
            { card ->
                getCompartilharUrlFromIntent(
                    "https://www.linkedin.com/in/" + card.img_item_linkedin.tag
                )
            }
        adapter.listenerShare_Instagram =
            { card ->
                getCompartilharUrlFromIntent(
                    "https://www.instagram.com/" + card.getTag()
                )
            }
        adapter.listenerShare_Youtube =
            { card ->
                getCompartilharUrlFromIntent(
                    "https://www.youtube.com/" + card.getTag()
                )
            }
        adapter.listenerShare_WhatsApp =
            { card ->
                getCompartilharUrlFromIntent(
                    card.getTag().toString()
                )
            }
    }

    private fun getAllCartaoVisita() {
        mainViewModel.getAll().observe(this, { cartaoVisita ->
            adapter.submitList(cartaoVisita)
        })
    }

    // Compartilhar Imagem
    private fun getUrlFromIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    // Compartilhar texto
    private fun getCompartilharUrlFromIntent(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, resources.getText(R.string.label_share))
        startActivity(shareIntent)
    }

    private fun getDeleteCartaoVisita(email: String) {
        mainViewModel.getUser(email).observe(this, { cartaoVisita ->
            cartaoVisita.forEach {
                adapter.doAsync { deleteByUserId(it.id) }
            }
        })
    }

    private fun deleteByUserId(idUser: Int) {
            mainViewModel.deleteByUserId(idUser)
    }
}