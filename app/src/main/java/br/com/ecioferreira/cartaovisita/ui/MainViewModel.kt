package br.com.ecioferreira.cartaovisita.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.ecioferreira.cartaovisita.data.CartaoVisita
import br.com.ecioferreira.cartaovisita.data.CartaoVisitaRepository

class MainViewModel(private val cartaoVisitaRepository: CartaoVisitaRepository) : ViewModel() {

    fun insert(cartaoVisita: CartaoVisita) {
        cartaoVisitaRepository.insert(cartaoVisita)
    }

    fun update(cartaoVisita: CartaoVisita) {
        cartaoVisitaRepository.update(cartaoVisita)
    }

    fun getAll(): LiveData<List<CartaoVisita>> {
        return cartaoVisitaRepository.getAll()
    }

    fun getUser(userEmail: String): LiveData<List<CartaoVisita>> {
        return cartaoVisitaRepository.getUser(userEmail)
    }

    fun deleteByUserId(idUser: Int) {
        return cartaoVisitaRepository.deleteByUserId(idUser)
    }

}

class MainViewModelFactory(private val repository: CartaoVisitaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknow ViewModel class")
    }
}