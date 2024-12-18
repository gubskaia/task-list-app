package kg.iuca.tasklistapp.lab2

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

class ToDoApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThemeSwitcherApp()
        }
    }
}

@Composable
fun ThemeSwitcherApp() {
    var isDarkTheme by remember { mutableStateOf(false) }

    // Используем MaterialTheme, меняя темы вручную
    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            // Используем Box для центрирования всех элементов
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), // Убедитесь, что Column занимает всю ширину
                    horizontalAlignment = Alignment.CenterHorizontally, // Центрируем содержимое по горизонтали
                    verticalArrangement = Arrangement.Center // Центрируем содержимое по вертикали
                ) {
                    // Текст, который меняет свой цвет в зависимости от темы
                    Text(
                        text = "Переключатель темы",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )

                    // Кнопка для переключения темы
                    Button(
                        onClick = { isDarkTheme = !isDarkTheme },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Переключить тему")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ThemeSwitcherApp()
}