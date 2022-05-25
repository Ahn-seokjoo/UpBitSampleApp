package com.example.upbitsampleapp.repository

import android.util.Log
import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import com.google.gson.Gson
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.StandardCharsets

class UpBitWebSocketListener(
    private val type: String,
    private val webSocketResult: (WebSocketTickerResult.WebSocketTickerResultItem) -> Unit
) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        when (type) {
            // api 결과로 websocket 쏘기
            // 나누지말고 한번에 받기
            "KRW" -> {
                webSocket.send(
                    "[{\"ticket\":\"test\"},{\"type\":\"ticker\",\"codes\":[\"KRW-BTC\", \n" +
                            "\"KRW-ETH\",\n" + "\"KRW-NEO\",\n" + "\"KRW-MTL\",\n" + "\"KRW-LTC\",\n" + "\"KRW-XRP\",\n" + "\"KRW-ETC\",\n" +
                            "\"KRW-OMG\",\n" + "\"KRW-SNT\",\n" + "\"KRW-WAVES\",\n" + "\"KRW-NEM\",\n" + "\"KRW-QTUM\",\n" +
                            "\"KRW-LSK\",\n" + "\"KRW-STEEM\",\n" + "\"KRW-XLM\",\n" + "\"KRW-ARDR\",\n" + "\"KRW-ARK\",\n" +
                            "\"KRW-STORJ\",\n" + "\"KRW-GRS\",\n" + "\"KRW-REP\",\n" + "\"KRW-ADA\",\n" + "\"KRW-SBD\",\n" +
                            "\"KRW-POWR\",\n" + "\"KRW-BTG\",\n" + "\"KRW-ICX\",\n" + "\"KRW-EOS\",\n" + "\"KRW-TRX\",\n" +
                            "\"KRW-SC\",\n" + "\"KRW-ONT\",\n" + "\"KRW-ZIL\",\n" + "\"KRW-POLY\",\n" + "\"KRW-ZRX\",\n" +
                            "\"KRW-LOOM\",\n" + "\"KRW-BCH\",\n" + "\"KRW-BAT\",\n" + "\"KRW-IOST\",\n" + "\"KRW-RFR\",\n" +
                            "\"KRW-CVC\",\n" + "\"KRW-IQ\",\n" + "\"KRW-IOTA\",\n" + "\"KRW-MFT\",\n" + "\"KRW-ONG\",\n" +
                            "\"KRW-GAS\",\n" + "\"KRW-UPP\",\n" + "\"KRW-ELF\",\n" + "\"KRW-KNC\",\n" + "\"KRW-BSV\",\n" +
                            "\"KRW-THETA\",\n" + "\"KRW-QKC\",\n" + "\"KRW-BTT\",\n" + "\"KRW-MOC\",\n" + "\"KRW-ENJ\",\n" +
                            "\"KRW-TFUEL\",\n" + "\"KRW-MANA\",\n" + "\"KRW-ANKR\",\n" + "\"KRW-AERGO\",\n" + "\"KRW-ATOM\",\n" +
                            "\"KRW-TT\",\n" + "\"KRW-CRE\",\n" + "\"KRW-MBL\",\n" + "\"KRW-WAXP\",\n" + "\"KRW-HBAR\",\n" +
                            "\"KRW-MED\",\n" + "\"KRW-MLK\",\n" + "\"KRW-STPT\",\n" + "\"KRW-ORBS\",\n" + "\"KRW-VET\",\n" +
                            "\"KRW-CHZ\",\n" + "\"KRW-STMX\",\n" + "\"KRW-DKA\",\n" + "\"KRW-HIVE\",\n" + "\"KRW-KAVA\",\n" +
                            "\"KRW-AHT\",\n" + "\"KRW-LINK\",\n" + "\"KRW-XTZ\",\n" + "\"KRW-BORA\",\n" + "\"KRW-JUST\",\n" +
                            "\"KRW-CRO\",\n" + "\"KRW-TON\",\n" + "\"KRW-SXP\",\n" + "\"KRW-HUNT\",\n" + "\"KRW-PLA\",\n" +
                            "\"KRW-DOT\",\n" + "\"KRW-SRM\",\n" + "\"KRW-MVL\",\n" + "\"KRW-STRAX\",\n" + "\"KRW-AQT\",\n" +
                            "\"KRW-GLM\",\n" + "\"KRW-SSX\",\n" + "\"KRW-META\",\n" + "\"KRW-FCT2\",\n" + "\"KRW-CBK\",\n" +
                            "\"KRW-SAND\",\n" + "\"KRW-HUM\",\n" + "\"KRW-DOGE\",\n" + "\"KRW-STRK\",\n" + "\"KRW-PUNDIX\",\n" +
                            "\"KRW-FLOW\",\n" + "\"KRW-DAWN\",\n" + "\"KRW-AXS\",\n" + "\"KRW-STX\",\n" + "\"KRW-XEC\",\n" +
                            "\"KRW-SOL\",\n" + "\"KRW-MATIC\",\n" + "\"KRW-NU\",\n" + "\"KRW-AAVE\",\n" + "\"KRW-1INCH\",\n" +
                            "\"KRW-ALGO\",\n" + "\"KRW-NEAR\",\n" + "\"KRW-WEMIX\",\n" + "\"KRW-AVAX\",\n" + "\"KRW-T\",]}]"
                )
            }
        }
//        webSocket.close(NORMAL_CLOSURE_STATUS, null) //없을 경우 끊임없이 서버와 통신함
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("Socket", "Receiving : $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//        Log.d("Socket", "Receiving bytes : $bytes")
        val result = Gson().fromJson(bytes.string(StandardCharsets.UTF_8), WebSocketTickerResult.WebSocketTickerResultItem::class.java)
        webSocketResult(result)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("Socket", "Closing : $code / $reason")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        webSocket.cancel()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("Socket", "Error : " + t.message)
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

}
