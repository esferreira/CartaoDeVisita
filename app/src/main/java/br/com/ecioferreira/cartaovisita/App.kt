package br.com.ecioferreira.cartaovisita

import android.app.Application
import br.com.ecioferreira.cartaovisita.data.AppDatabase
import br.com.ecioferreira.cartaovisita.data.CartaoVisitaRepository


class App: Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {CartaoVisitaRepository(database.cartaoDao())}
}