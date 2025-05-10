package prueba

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

import io.ktor.client.call.*
import io.ktor.client.statement.bodyAsText

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testGetEntidades() = testApplication {

        application {
            module()
            }

        val response = client.get("/entidades")
        assertEquals(200, response.status.value)

        println("Response status: ${response.status.value}")
    }

    @Test
    fun testGetEntidadesId() = testApplication {

        application {

            module()
        }

        //val response2 = client.get("/entidades/${1}")
        //assertEquals(200, response2.status.value)

        //println("Response status: ${response2.status.value}")
    }

}
