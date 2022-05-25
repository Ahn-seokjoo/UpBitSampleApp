package com.example.upbitsampleapp.util

import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import com.example.upbitsampleapp.repository.UpBitWebSocketListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class UpBitWebSocket @Inject constructor(
    private val client: OkHttpClient,
    private val request: Request,
    private val channel: Channel<WebSocketTickerResult.WebSocketTickerResultItem>
) {
    private lateinit var webSocket: WebSocket

    // 안에서 보내거나 자체를 subscribe
    fun start(type: String) {
        val listener = UpBitWebSocketListener(type, ::webResult)
        webSocket = client.newWebSocket(request, listener)
        client.dispatcher().executorService().shutdown()
    }

    fun stop() {
        webSocket.close(UpBitWebSocketListener.NORMAL_CLOSURE_STATUS, null)
        webSocket.cancel()
    }

    private fun webResult(result: WebSocketTickerResult.WebSocketTickerResultItem) =
        CoroutineScope(Job()).launch {
            channel.send(result)
        }
}
