package play.appexample.moonnews.apimanager

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import play.appexample.moonnews.model.NewsRequest
import play.appexample.moonnews.utils.Constant

class ApiServiceImpl(private val client:HttpClient) : ApiService {
    override suspend fun getNews(): NewsRequest {
        return try {
            client.get(urlString = Constant.API_BASE_URL)
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            Constant.debugMessage("Error: ${ex.response.status.description}")
            NewsRequest(status = "error", totalResults = 0, articles = emptyList())
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            Constant.debugMessage("Error: ${ex.response.status.description}")
            NewsRequest(status = "error", totalResults = 0, articles = emptyList())
        } catch (ex: ServerResponseException) {
            // 5xx - response
            Constant.debugMessage("Error: ${ex.response.status.description}")
            NewsRequest(status = "error", totalResults = 0, articles = emptyList())
        }
    }

}
