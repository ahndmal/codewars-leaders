package com.andmal

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpResponse
import com.google.gson.Gson
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

class Main : HttpFunction {
    @Throws(IOException::class)
    override fun service(request: com.google.cloud.functions.HttpRequest?, response: HttpResponse?) {
        parseCodeData()
        response?.writer?.write("parsed data")
    }


companion object {
    fun parseCodeData() {
        val leadersLink = "https://www.codewars.com/users/leaderboard"
//        val leadersFile = "resources/main/codewars_leaders.html"
//        val file = File(leadersFile)

        val userUrl = "https://www.codewars.com/api/v1/users/"

        val client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .version(HttpClient.Version.HTTP_1_1)
            .build()

        val req = HttpRequest.newBuilder()
            .GET()
            .uri(URI(leadersLink))
            .header("Content-Type", "text/html")
            .build()

        val resp =  client.send(req, BodyHandlers.ofString())

//        val doc: Document = Jsoup.parse(file)
        val doc: Document = Jsoup.parse(resp.body())

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
}
}

fun main(args: Array<String>) {
    Main.parseCodeData()
}