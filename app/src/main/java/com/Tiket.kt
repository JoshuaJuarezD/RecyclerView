package com

enum class Estado{
    APROBADO, PENDIENTE, CANCELADO
}

data class Tiket (
    val folio:String,
    val fechaCompra:String,
    val monto:Double,
    val descripcion:String,
    val estadoTiket:Estado?=null
)