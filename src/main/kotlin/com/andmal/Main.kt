package com.andmal

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpResponse
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.google.gson.Gson
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Duration
import kotlin.io.path.writeText

class Main : HttpFunction {
    val projectId = "silver-adapter-307718"
    val bucketName = "codewars-data"

    @Throws(IOException::class)
    override fun service(request: com.google.cloud.functions.HttpRequest?, response: HttpResponse?) {
        val storage: Storage = StorageOptions.newBuilder().setProjectId(projectId).build().service
//        val bucket: Bucket = storage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.values()));

//        val data = bucket.get("codewars_leaders.csv")

        response?.writer?.write("parsed data")
    }


    companion object {
        fun getUsersFromWeb(): ArrayList<User> {
            var counter: Int = 0
            val users: ArrayList<User> = ArrayList()
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

            val resp = client.send(req, BodyHandlers.ofString())

//        val doc: Document = Jsoup.parse(file)
            val doc: Document = Jsoup.parse(resp.body())

            val usersLinks: Elements = doc.select("td>a[href*=\"/users/\"]")

            usersLinks.forEach {
                try {
                    val userName = it.attr("href").replace("/users/", "")

                    val request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("${userUrl}/${userName}"))
                        .build()

                    val resp = client.send(request, BodyHandlers.ofString())
                    val body = resp.body()

                    val gson = Gson().newBuilder().setPrettyPrinting().create()
                    val user = gson.fromJson(body, User::class.java)
                    users.add(user)
                    println(">> added user ${counter}")
                    counter++

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            println(">> completed parsing JOSN data")

            return users
        }
    }
}

fun main(args: Array<String>) {
    createCSV()
}

fun createCSV() {
    val fileName = "codewars_leaders.csv"
    val file = Files.createFile(Paths.get(fileName))
    val header = "id,username,name,honor,clan,position,skills,ranks,challenges\n"
    file.writeText(header, Charset.defaultCharset(), StandardOpenOption.WRITE)

    val users = Main.getUsersFromWeb()
    println(">> writing data to CSV")
    users.forEach {
        println(">> writing user ${it.username}")

        file.writeText(
            "${it.id},${it.username},${it.name},${it.honor}," +
                    "${it.clan},${it.leaderboardPosition},${it.skills},${it.ranks},${it.codeChallenges}\n"
        )
    }

    println(">> Successfully wrote data to CSV")

}