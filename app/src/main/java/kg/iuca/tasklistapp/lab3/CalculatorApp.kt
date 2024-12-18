package kg.iuca.tasklistapp.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CalculatorApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    // Состояния для текста ввода и результата
    var inputText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("0") }

    // Функция для вычисления выражения
    fun evalExpression(expression: String): String {
        // Разделяем выражение на компоненты: операнды и оператор
        val tokens = expression.split(" ").filter { it.isNotBlank() }
            .takeIf { it.size == 3 } ?: expression.split("(?=[-+*/])|(?<=[-+*/])".toRegex())

        // Если выражение имеет неверный формат, возвращаем сообщение об ошибке
        if (tokens.size != 3) {
            return "Invalid expression format"
        }

        // Преобразуем операнды в числа
        val a = tokens[0].toDouble()
        val operator = tokens[1]  // Оператор (+, -, *, /)
        val b = tokens[2].toDouble()

        // Выполняем вычисление в зависимости от оператора
        return when (operator) {
            "+" -> (a + b).toString()  // Сложение
            "-" -> (a - b).toString()  // Вычитание
            "*" -> (a * b).toString()  // Умножение
            "/" -> {
                // Обрабатываем деление на ноль
                if (b == 0.0) {
                    return "Division by zero"
                }
                (a / b).toString()  // Деление
            }
            else -> "Invalid operator"  // Некорректный оператор
        }
    }

    // Функция для обработки нажатия на кнопки
    fun onButtonClick(value: String) {
        when (value) {
            "=" -> {
                // Вычисляем результат при нажатии на "="
                resultText = evalExpression(inputText)
            }
            "C" -> {
                // Очищаем экран при нажатии на "C"
                inputText = ""
                resultText = "0"
            }
            else -> {
                // Добавляем нажатую цифру или оператор в строку ввода
                inputText += value
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Блок для ввода и отображения результата
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Отображаем текст ввода
            Text(
                text = inputText,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Отображаем результат вычисления
            Text(
                text = resultText,
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Кнопки калькулятора
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        Column( // Колонка с кнопками
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Для каждой строки кнопок
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Для каждой кнопки в строке
                    row.forEach { label ->
                        // Отображаем кнопку
                        CalculatorButton(label = label, onClick = { onButtonClick(label) }, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// Компонент кнопки калькулятора
@Composable
fun CalculatorButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(64.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

// Превью для калькулятора
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}
