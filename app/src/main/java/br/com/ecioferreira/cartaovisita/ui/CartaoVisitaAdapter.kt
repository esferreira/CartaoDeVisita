package br.com.ecioferreira.cartaovisita.ui;

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.ecioferreira.cartaovisita.data.CartaoVisita
import br.com.ecioferreira.cartaovisita.databinding.ItemCartaoVisitaBinding

class CartaoVisitaAdapter :
    ListAdapter<CartaoVisita, CartaoVisitaAdapter.ViewHolder>(DiffCallback()) {

    var listenerShare: (View) -> Unit = {}
    var listenerShareLinkedin: (View) -> Unit = {}
    var listenerShareInstagram: (View) -> Unit = {}
    var listenerShareYoutube: (View) -> Unit = {}
    var listenerShareWhatsApp: (View) -> Unit = {}
    // Clique OnLongClickListener
    var listener_Share: (View) -> Unit = {}
    var listenerShare_Linkedin: (View) -> Unit = {}
    var listenerShare_Instagram: (View) -> Unit = {}
    var listenerShare_Youtube: (View) -> Unit = {}
    var listenerShare_WhatsApp: (View) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartaoVisitaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCartaoVisitaBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartaoVisita) {
            binding.tvNome.text = item.nome
            binding.tvTelefone.text = item.telefone
            binding.tvEmail.text = item.email
            binding.tvEmpresa.text = item.empresa
            binding.imgItemLinkedin.tag = item.linkedin
            binding.imgItemInstagram.tag = item.instagram
            binding.imgItemYoutube.tag = item.youtube
            binding.imgItemWhatsapp.tag = item.whatsapp
            //binding.imgItemFoto.setImageBitmap(item.imagem)
            binding.mcvContent.setCardBackgroundColor(Color.parseColor(item.fundoPersonalizado))

            // Clique
            binding.mcvContent.setOnClickListener { listenerShare(it) }
            binding.imgItemLinkedin.setOnClickListener { listenerShareLinkedin(it) }
            binding.imgItemInstagram.setOnClickListener { listenerShareInstagram(it) }
            binding.imgItemYoutube.setOnClickListener { listenerShareYoutube(it) }
            binding.imgItemWhatsapp.setOnClickListener { listenerShareWhatsApp(it) }
            // Clique Longo
            binding.mcvContent.setOnLongClickListener (View.OnLongClickListener {
                listener_Share(it)
                true
            })
            binding.imgItemLinkedin.setOnLongClickListener(View.OnLongClickListener {
                listenerShare_Linkedin(it)
                true
            })
            binding.imgItemInstagram.setOnLongClickListener(View.OnLongClickListener {
                listenerShare_Instagram(it)
                true
            })
            binding.imgItemYoutube.setOnLongClickListener(View.OnLongClickListener {
                listenerShare_Youtube(it)
                true
            })
            binding.imgItemWhatsapp.setOnLongClickListener(View.OnLongClickListener {
                listenerShare_WhatsApp(it)
                true
            })
        }
    }
}


class DiffCallback : DiffUtil.ItemCallback<CartaoVisita>() {
    override fun areItemsTheSame(oldItem: CartaoVisita, newItem: CartaoVisita) = oldItem == newItem
    override fun areContentsTheSame(oldItem: CartaoVisita, newItem: CartaoVisita) =
        oldItem.id == newItem.id

}