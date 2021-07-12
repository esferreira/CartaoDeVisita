package br.com.ecioferreira.cartaovisita.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["email"], unique = true)])
data class CartaoVisita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val empresa: String,
    val telefone: String,
    @ColumnInfo(name = "email")
    val email: String,
    val fundoPersonalizado: String,
    val linkedin: String,
    val instagram: String,
    val youtube: String,
    val whatsapp: String
    //val imagem: Bitmap?=null
)
