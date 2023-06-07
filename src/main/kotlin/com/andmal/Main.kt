package com.andmal

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpResponse
import com.google.gson.Gson
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

class Main : HttpFunction {
    override fun service(request: com.google.cloud.functions.HttpRequest?, response: HttpResponse?) {
        parseCodeData()
        response?.writer?.write("parsed data")
    }
}

fun main(args: Array<String>) {
    parseCodeData()
}

fun parseCodeData() {
    val link = "https://www.codewars.com/users/leaderboard"
    val leadersFile = "src/main/resources/codewars_leaders.html"

    val userUrl = "https://www.codewars.com/api/v1/users/"

    val client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(20))
        .version(HttpClient.Version.HTTP_1_1)
        .build()

    val file = File(leadersFile)
    val doc: Document = Jsoup.parse(file)

    val usersLinks: Elements = doc.select("td>a[href*=\"/users/\"]")

    usersLinks.forEach {
        try {
            val request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("${userUrl}/${it.text()}"))
                .build()

            val resp = client.send(request, BodyHandlers.ofString())
            val body = resp.body()

            val gson = Gson().newBuilder().setPrettyPrinting().create()
            val user = gson.fromJson(body, User::class.java)

            println(user)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}