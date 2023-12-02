package ru.databaseServer.database

import ru.databaseServer.dataclasses.LinkItem
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class DatabaseServerManager {
    private val databaseServerAddress = "localhost"
    private val databaseServerPort = 6379

    fun getLongLink(shortLink: String): String{
        val socket = Socket(databaseServerAddress, databaseServerPort)
        val output = PrintWriter(socket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val request = "--file LinkHashMap.txt --type hashmap --query HGET $shortLink"
        output.println(request)
        val longLink = input.readLine()
        socket.close()
        return longLink ?: "Error: Link not found"
    }

    fun addToDatabase(link : LinkItem){
        val socket = Socket(databaseServerAddress, databaseServerPort)
        val output = PrintWriter(socket.getOutputStream(), true)
        val request =
            "--file LinkHashMap.txt --type hashmap --query HSET ${link.token} ${link.longLink}"
        output.println(request)
        socket.close()
    }

    fun checkTokenInDatabase(link : LinkItem): Boolean{
        val socket = Socket(databaseServerAddress, databaseServerPort)
        val output = PrintWriter(socket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))

        val request =
            "--file LinkHashMap.txt --type hashmap --query HGET ${link.token}"
        output.println(request)

        val result = input.readLine()
        socket.close()
        return result == "not found"
    }
}