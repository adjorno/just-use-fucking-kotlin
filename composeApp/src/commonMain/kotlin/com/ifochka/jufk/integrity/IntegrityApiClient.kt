package com.ifochka.jufk.integrity

import com.ifochka.jufk.createHttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class IntegrityApiClient {
    suspend fun verify(token: String, packageName: String): IntegrityVerdict? = runCatching {
        val response = createHttpClient().post("https://api.m14n.com/integrity/verify") {
            contentType(ContentType.Application.Json)
            setBody("""{"integrityToken":"$token","packageName":"$packageName"}""")
        }
        val body = response.bodyAsText()
        println("IntegrityApiClient: status=${response.status} body=$body")
        json.decodeFromString<IntegrityVerdict>(body)
    }.onFailure { e ->
        println("IntegrityApiClient: request failed — ${e::class.simpleName}: ${e.message}")
    }.getOrNull()

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
    }
}
