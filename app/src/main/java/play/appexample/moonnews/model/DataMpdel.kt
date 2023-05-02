package play.appexample.moonnews.model

@kotlinx.serialization.Serializable
data class NewsRequest(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticles>,
)

@kotlinx.serialization.Serializable
data class NewsArticles(
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)