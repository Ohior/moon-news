package play.appexample.moonnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import play.appexample.moonnews.state.ARGUMENT_KEY
import play.appexample.moonnews.state.NavState
import play.appexample.moonnews.state.WEB_SCREEN
import play.appexample.moonnews.state.WEB_SCREEN_KEY
import play.appexample.moonnews.ui.screens.MainScreen
import play.appexample.moonnews.ui.screens.WebViewScreen
import play.appexample.moonnews.ui.theme.MoonNewsTheme
import play.appexample.moonnews.utils.Constant

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var coroutineScope: CoroutineScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoonNewsTheme {
                navHostController = rememberNavController()
                scaffoldState = rememberScaffoldState()
                coroutineScope = rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    Scaffold {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(it)) {
                            NavigationController()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationController() {
        NavHost(navController = navHostController, startDestination = NavState.MainScreen.route) {
            composable(NavState.MainScreen.route) {
                MainScreen(navHostController).also {ms-> ms.MainScreenPage() }
            }
//            composable(NavState.WebScreen.route,
            composable("web_screen/{$WEB_SCREEN_KEY}",
                arguments = listOf(navArgument(WEB_SCREEN_KEY){type = NavType.StringType})
            ) {
                val data = it.arguments?.getString(WEB_SCREEN_KEY).toString().replace("$$$", "/")
                WebViewScreen(data).apply { WebViewPage() }
            }
        }
    }
}
