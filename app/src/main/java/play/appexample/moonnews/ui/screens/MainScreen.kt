package play.appexample.moonnews.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.gson.Gson
import play.appexample.moonnews.R
import play.appexample.moonnews.apimanager.ApiService
import play.appexample.moonnews.model.NewsRequest
import play.appexample.moonnews.state.NavState
import play.appexample.moonnews.utils.Constant

class MainScreen(
    private val navHostController: NavHostController,
) {
    private var loadScreenClicked by mutableStateOf(false)
    private val apiService by lazy { ApiService.create() }
    private lateinit var newsRequest: State<NewsRequest>

    @Composable
    fun MainScreenPage() {
        newsRequest = produceState(
            initialValue = NewsRequest(status = "error", totalResults = 0, articles = emptyList()),
            producer = {
                value = apiService.getNews()
            }
        )
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            if (loadScreenClicked.not() && newsRequest.value.status.lowercase() != "error") {
                DisplayScreen()
            } else {
                DisplayNewsScreen()
            }
        }
    }

    @Composable
    private fun DisplayScreen() {
        Constant.showToast(LocalContext.current, "Could not find any news")
        Image(painter = painterResource(id = R.drawable.pngegg), contentDescription = null)
        Box(modifier = Modifier
            .size(300.dp)
            .clip(RoundedCornerShape(100))
            .background(Color.Red)
            .clickable {
                loadScreenClicked = !loadScreenClicked
            }, contentAlignment = Alignment.Center) {
            Box(modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(100))
                .background(
                    Color.Black))
            Box(modifier = Modifier
                .size(230.dp)
                .clip(RoundedCornerShape(100))
                .background(
                    Color.Green), contentAlignment = Alignment.Center) {
                Text(text = "LOAD NEWS",
                    style = MaterialTheme.typography.h2,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center)
            }
        }
    }

    @Composable
    private fun DisplayNewsScreen() {
        val nr = newsRequest.value
        if (nr.status.lowercase() == "ok") {
            //loadScreenClicked = false
            LazyColumn {
                items(items = nr.articles.shuffled()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(Color.LightGray)
                            .clickable {
                                val url = it.url.replace("/", "$$$")
                                val pass = "web_screen/$url"
                                navHostController.navigate(route = pass)
                            },
                        elevation = 5.dp) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            //set the image url
                            val painter =
                                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                                    .data(data = it.urlToImage)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        error(R.drawable.ic_launcher_background)
                                    }).build())

                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Coil Image",
                                painter = painter
                            )
                            Text(
                                text = it.title,
                                fontSize = 18.sp
                            )
                            Text(
                                text = it.description,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        } else {
            DisplayScreen()
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
    }

}