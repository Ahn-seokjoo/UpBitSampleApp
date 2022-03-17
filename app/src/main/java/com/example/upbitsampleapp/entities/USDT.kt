package com.example.upbitsampleapp.entities

enum class USDT(val kor: String, val eng: String, val market: String) {
    BITCOIN("비트코인", "Bitcoin", "USDT-BTC"),
    ETHEREUM("이더리움", "Ethereum", "USDT-ETH"),
    LITECOIN("라이트코인", "Litecoin", "USDT-LTC"),
    OMISEGO("오미세고", "OmiseGo", "USDT-OMG"),
    ADA("에이다", "Ada", "USDT-ADA"),
    TRUEUSD("트루USD", "TrueUSD", "USDT-TUSD"),
    SIACOIN("시아코인", "Siacoin", "USDT-SC"),
    TRON("트론", "TRON", "USDT-TRX"),
    BITCOINCASH("비트코인캐시", "Bitcoin Cash", "USDT-BCH"),
    DIGIBYTE("디지바이트", "DigiByte", "USDT-DGB"),
    DOGECOIN("도지코인", "Dogecoin", "USDT-DOGE"),
    ZEROXPROTOCOL("제로엑스", "0x Protocol", "USDT-ZRX"),
    RAVENCOIN("레이븐코인", "Ravencoin", "USDT-RVN"),
    BASICATTENTIONTOKEN("베이직어텐션토큰", "Basic Attention Token", "USDT-BAT")
    ;

    companion object {
        fun getMarket(market: String): USDT {
            return when (market) {
                BITCOIN.market -> BITCOIN
                ETHEREUM.market -> ETHEREUM
                LITECOIN.market -> LITECOIN
                OMISEGO.market -> OMISEGO
                ADA.market -> ADA
                TRUEUSD.market -> TRUEUSD
                SIACOIN.market -> SIACOIN
                TRON.market -> TRON
                BITCOINCASH.market -> BITCOINCASH
                DIGIBYTE.market -> DIGIBYTE
                DOGECOIN.market -> DOGECOIN
                ZEROXPROTOCOL.market -> ZEROXPROTOCOL
                RAVENCOIN.market -> RAVENCOIN
                else -> BASICATTENTIONTOKEN
            }
        }
    }
}
