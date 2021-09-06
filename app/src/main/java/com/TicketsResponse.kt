package com

data class TicketsResponse(
    val ticketsResponse: List<TicketsResponseItem?>? = null
)

data class TicketsResponseItem(
    val descripcion: Any? = null,
    val estado: String? = null,
    val bonificacion: Any? = null,
    val createdAt: String? = null,
    val comercioId: Int? = null,
    val deletedAt: Any? = null,
    val fecha: String? = null,
    val total: String? = null,
    val pesosCanaco: Any? = null,
    val updatedAt: Any? = null,
    val folio: Int? = null,
    val id: Int? = null,
    val clienteId: Int? = null,
    val fechaCaducidad: Any? = null
)