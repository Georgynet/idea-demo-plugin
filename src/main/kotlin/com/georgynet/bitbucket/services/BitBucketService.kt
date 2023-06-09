package com.georgynet.bitbucket.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import kong.unirest.json.JSONObject

@Service(Service.Level.PROJECT)
class BitBucketService(project: Project) {
    fun getRandomNumber() = (1..100).random()

    fun getPullRequestList(workspaceName:String, repositoryName:String): JSONObject? {
        try {
            val apiToken = ConfigReader().getToken()

            val response: HttpResponse<JsonNode> = Unirest.get("https://api.bitbucket.org/2.0/repositories/$workspaceName/$repositoryName/pullrequests")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer $apiToken")
                .asJson()

            return response.getBody().getObject()
        } catch (e:Error) {
            println("Error:")
            println(e)
        }

        return null
    }
}