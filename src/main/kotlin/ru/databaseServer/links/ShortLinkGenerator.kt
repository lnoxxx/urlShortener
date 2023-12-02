package ru.databaseServer.links

import kotlin.random.Random

class ShortLinkGenerator {
    fun generateToken(size: Int): String{
        val symbolsPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        var token = String()
        for (k in 0..size){
            token += symbolsPool[Random.nextInt(0, symbolsPool.size)]
        }
        return token
    }
    fun generateLink(token : String) : String{
        return "http://192.168.0.11:5051/$token"
    }
}