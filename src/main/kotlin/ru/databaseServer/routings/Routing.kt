package ru.databaseServer.routings

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.databaseServer.database.DatabaseServerManager
import ru.databaseServer.dataclasses.LinkItem
import ru.databaseServer.links.LinkVerification
import ru.databaseServer.links.ShortLinkGenerator

fun Application.urlRouting() {
    routing {
        get("/{shortLink}") {
            val shortLink = call.parameters["shortLink"]
            if (shortLink == null){
                call.respondText("Error: Empty link")
                return@get
            }
            val longLink = DatabaseServerManager().getLongLink(shortLink)
            if (longLink == "not found"){
                call.respondText("Error: link not found")
            }
            call.respondRedirect(longLink)
        }
        post("/"){
            val longLink = call.request.queryParameters["longLink"]
            if (longLink == null){
                call.respondText("Error: Empty link")
                return@post
            }
            println(longLink)
            val token = ShortLinkGenerator().generateToken(6)
            var link = LinkItem(longLink, token)
            link = LinkVerification().checkAll(link)
            DatabaseServerManager().addToDatabase(link)
            val shortLink = ShortLinkGenerator().generateLink(token)
            call.respondText(shortLink)
        }
    }
}
