package br.com.ecioferreira.cartaovisita.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartaoVisitaRepository(private val dao: CartaoVisitaDAO) {

    fun insert(cartaoVisita: CartaoVisita) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(cartaoVisita)
        }
    }

    fun update(cartaoVisita: CartaoVisita) = runBlocking {
        launch(Dispatchers.IO) {
            dao.update(
                cartaoVisita.id,
                cartaoVisita.nome,
                cartaoVisita.telefone,
                cartaoVisita.email,
                cartaoVisita.empresa,
                cartaoVisita.linkedin,
                cartaoVisita.instagram,
                cartaoVisita.youtube,
                cartaoVisita.whatsapp,
                cartaoVisita.fundoPersonalizado
            )
        }
    }

    fun getAll() = dao.getAll()

    fun getUser(userEmail: String): LiveData<List<CartaoVisita>> = dao.getUser(userEmail)

    fun deleteByUserId(idUser: Int): Unit = dao.deleteByUserId(idUser)
}