package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.Estado
import com.MostrarTiketAdapter
import com.TicketsResponse
import com.Tiket
import com.example.recyclerview.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {
    val token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYjdiODAwNDM5ZmRiYjZhMDYyMzJiMDY0MDZmNTMzOTYwN2JlOTQ5OThmNmQ4MjI3YmFlYzBlYTVlZDRiYTI5YWViMjI4NTU0YTZjNDM1MTEiLCJpYXQiOjE2MzA1NDUxNjEsIm5iZiI6MTYzMDU0NTE2MSwiZXhwIjoxNjYyMDgxMTYwLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.A-FX4VhqhItrtls6HdkY0GmSRhNYwCUZtFLnvLxu0xUZgp009qOlXvtVLGM7war3-s1_7dGaxf9JjXIzR8-UB5XOXq63QzVUVo5aY9fzrCeyEJlL7Jl_3Y_ujYRA0vMNdg0_m5WXI3g6EW368VMVyM09fl69Z93Q5pRD8odIVC3d6H7yDbUYXPiQ5m12CGEjDP_Xb8M1QeKzwM8URuCjmXPMz6v2GJSSQioeVsAHOFNeCLy1i-T3bToG13K_CpqIUEQz9wd8VfSkz0XT-nuShxMOgCEG2vPNsa6FxPaoTIVO2zR-BnUFtllx1atqTvCUGw469jF2eULLYD3gYyphYa-154_45-fJ0uQc-veQnAHTObUnXDJ9RZ2sctt8uuD-VvmuqipckQ2Qm7aO001ScZ32L_c1PrXv3StIAYzu1Glkjg35IvQfvueMwDp62qD66BAxumBtDFvEf2Mw1B7_sNiarpqPYjX9Ou7I4rG_B10hnREZ-2M7w7ABjGJ8XO6G_ejSTbgMrib5PunDDi9xiFldTigMY9Z3gS0Lm5_VtLOxw9HBWDZjf5L4Z_DQJctY98Pwt_EtNpOP5kSLyGjTvl6eli9XNjogXNUYol9Grzu6HKOEdSogUWWlb6SuL7sMKeOHmDk7QsIwLr-NaQJ3K5S11nv0WlfsVFfkE-Vt4sA"


    var tikets: List<Tiket> = listOf(
        Tiket("001", "01/02/2021", 34.5, "compra", Estado.APROBADO),
        Tiket("002", "02/02/2021", 35.5, "compra", Estado.PENDIENTE),
        Tiket("003", "03/02/2021", 36.5, "compra", Estado.CANCELADO),
        Tiket("004", "04/02/2021", 37.5, "compra", Estado.PENDIENTE),
        Tiket("005", "05/02/2021", 38.5, "compra", Estado.CANCELADO)

    )
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
    }

    fun initRecycler() {
        val response = getComercios("http://canaco.demos.mx/api/tikets/2/show", token)
        val ticketsList= Gson().fromJson(response?.get(1), TicketsResponse::class.java)
        binding.rvTikets.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch {

            val adapter = MostrarTiketAdapter(ticketsList?.ticketsResponse)
            binding.rvTikets.adapter = adapter
        }
    }



    fun getComercios(urlMethod: String?, tokenString: String): Array<String>? {

        val responseArray = arrayOf("", "") // Indice 0 : Código de respuesta
        // indice 1: Mensaje
        var responseFromAPI = "" // Aquí se almacenarán los comercios
        try {
            val url = URL(urlMethod)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.setRequestProperty(
                "Authorization",
                "Bearer $tokenString"
            ) // Token del usuario logeado para que la consulta funcione...
            if (con.responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                BufferedReader(
                    InputStreamReader(
                        con.errorStream,
                        StandardCharsets.UTF_8
                    )
                ).use { error ->
                    val errorResponse = StringBuilder()
                    var errorResponseLine: String? = null
                    while (error.readLine().also { errorResponseLine = it } != null) {
                        errorResponse.append(errorResponseLine!!.trim { it <= ' ' })
                    }
                    responseArray[0] = Integer.toString(con.responseCode)
                    responseArray[1] = errorResponse.toString()
                    println(errorResponse)
                    return responseArray
                }
            }
            BufferedReader(
                InputStreamReader(
                    con.inputStream,
                    StandardCharsets.UTF_8
                )
            ).use { br ->  // Leer respuesta
                val response = StringBuilder()
                var responseLine: String? = null
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine!!.trim { it <= ' ' })
                }
                responseArray[0] = Integer.toString(con.responseCode)
                responseFromAPI = response.toString() // Se lee la respuesta
                con.disconnect() // Se cierra la conexión
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        responseArray[1] = responseFromAPI // Se almacena la respuesta en el indice 1
        return responseArray
    }
}