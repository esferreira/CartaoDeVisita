package br.com.ecioferreira.cartaovisita.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CartaoVisitaDAO {
    @Query("SELECT * FROM CartaoVisita")
    fun getAll(): LiveData<List<CartaoVisita>>

    @Query("SELECT * FROM CartaoVisita WHERE email = :userEmail")
    fun getUser(userEmail: String): LiveData<List<CartaoVisita>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(cartaoVisita: CartaoVisita)

    @Query("UPDATE CartaoVisita SET nome = :nome, email = :email,  telefone = :telefone, empresa = :empresa, linkedin = :linkedin, instagram = :instagram, youtube = :youtube, whatsapp = :whatsapp, fundoPersonalizado = :fundoPersonalizado WHERE id = :id")
    suspend fun update(
        id: Int,
        nome: String,
        telefone: String,
        email: String,
        empresa: String,
        linkedin: String,
        instagram: String,
        youtube: String,
        whatsapp: String,
        fundoPersonalizado: String
    )

    @Query("DELETE FROM CartaoVisita WHERE id = :idUser")
    fun deleteByUserId(idUser: Int)
}