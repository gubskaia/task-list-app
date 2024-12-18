package kg.iuca.tasklistapp.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class TimeApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerApp()
        }
    }
}

@Composable
fun TimerApp() {
    var timeLeft by remember { mutableStateOf(10) }
    var isFinished by remember { mutableStateOf(false) }

    // Запуск таймера
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)  // Задержка на 1 секунду
            timeLeft -= 1  // Уменьшаем время
        } else {
            isFinished = true  // Таймер завершен
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        // Отображаем оставшееся время или сообщение о завершении
        if (isFinished) {
            Text(text = "Время вышло!", fontSize = 24.sp)
        } else {
            Text(text = "Оставшееся время: $timeLeft", fontSize = 32.sp)
        }

        // Кнопка для перезапуска таймера
        Button(onClick = {
            timeLeft = 10  // Сбросить таймер
            isFinished = false
        }) {
            Text(text = "Перезапустить таймер")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimerApp()
}
