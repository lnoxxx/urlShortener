package ru.databaseServer

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.databaseServer.routings.*

fun main() {
    embeddedServer(Netty, port = 5051, module = Application::module).start(wait = true)
}

fun Application.module() {
    urlRouting()
}
