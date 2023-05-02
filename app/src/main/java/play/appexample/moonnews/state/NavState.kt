package play.appexample.moonnews.state

const val WEB_SCREEN_KEY = "urlString"
const val ARGUMENT_KEY = "argument_key"
const val WEB_SCREEN = "web_screen"
const val MAIN_SCREEN = "main_screen"

sealed class NavState(val route: String) {
    object MainScreen : NavState(route = MAIN_SCREEN)

    object WebScreen : NavState(route = "$WEB_SCREEN/{${WEB_SCREEN_KEY}}") {
//        fun passData(string: String = "Saint"): String {
//            return this.route.replace(WEB_SCREEN_KEY, string)
//        }

//        fun passTwoData(
//            string1: String,
//            string2: String,
//        ): String {
//            return "$WEB_SCREEN/$string1/$string2"
//        }
    }
}