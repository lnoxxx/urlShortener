package ru.databaseServer.links

import ru.databaseServer.database.DatabaseServerManager
import ru.databaseServer.dataclasses.LinkItem

class LinkVerification {
    fun checkAll(link : LinkItem): LinkItem {
        link.token = uniqueToken(link)
        return link
    }
    private fun uniqueToken(link : LinkItem): String{
        while (!DatabaseServerManager().checkTokenInDatabase(link)){
            link.token = ShortLinkGenerator().generateToken(6)
        }
        return link.token
    }
}