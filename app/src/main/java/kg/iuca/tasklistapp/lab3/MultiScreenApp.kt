package kg.iuca.tasklistapp.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*

class MultiScreenApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationApp()
        }
    }
}

@Composable
fun NavigationApp() {
    // Создаем NavController для управления навигацией между экранами
    val navController = rememberNavController()

    // Настроим навигацию: начальный экран и маршруты между экранами
    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") { FirstScreen(navController) } // Первый экран
        composable("screen2") { SecondScreen(navController) } // Второй экран
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    // Первый экран с кнопкой для перехода ко второму экрану
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Текст с заголовком для первого экрана
        Text("Первый экран", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстом и кнопкой
        Button(onClick = {
            // Переход ко второму экрану при нажатии на кнопку
            navController.navigate("screen2")
        }) {
            // Текст на кнопке
            Text("Перейти на второй экран")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    // Второй экран с кнопкой для возврата на первый экран
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Текст с заголовком для второго экрана
        Text("Второй экран", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстом и кнопкой
        Button(onClick = {
            // Возвращение на предыдущий экран при нажатии на кнопку
            navController.popBackStack()
        }) {
            Text("Вернуться")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationAppPreview() {
    NavigationApp()
}
