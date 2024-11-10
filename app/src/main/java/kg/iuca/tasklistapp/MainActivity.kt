import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

// Основной компонент приложения "Список дел"
@Composable
fun TodoApp() {
    // Состояние для хранения текущего текста новой задачи
    var taskText by remember { mutableStateOf(TextFieldValue("")) }
    // Список задач
    val tasks = remember { mutableStateListOf<String>() }

    // Вертикальная колонка для расположения элементов интерфейса
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF7F7F7))
    ) {
        // Горизонтальная строка для поля ввода и кнопки добавления
        Row(
            verticalAlignment = Alignment.CenterVertically, // Центрирование элементов по вертикали
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Отступы между элементами
        ) {
            // Поле ввода текста
            BasicTextField(
                value = taskText,
                onValueChange = { taskText = it }, // Обновление текста при вводе
                modifier = Modifier
                    .weight(1f) // Поле занимает всю оставшуюся ширину строки
                    .height(70.dp) // Высота поля
                    .padding(12.dp) // Внутренние отступы
                    .background(Color.White, RoundedCornerShape(8.dp)) // Фон и скругление углов
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), // Рамка вокруг поля
                textStyle = TextStyle(
                    color = Color.Black, // Цвет текста
                    fontWeight = FontWeight.Normal, // Стиль текста
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize // Размер текста
                ),
                decorationBox = { innerTextField ->
                    // Контейнер для размещения текста внутри поля
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        innerTextField() // Отображение содержимого поля
                    }
                }
            )

            // Кнопка для добавления новой задачи
            Button(
                onClick = {
                    if (taskText.text.isNotBlank()) { // Проверка, что текст не пустой
                        tasks.add(taskText.text) // Добавление новой задачи в список
                        taskText = TextFieldValue("") // Очистка поля ввода
                    }
                },
                modifier = Modifier
                    .height(50.dp) // Высота кнопки
                    .padding(start = 8.dp), // Отступ слева
                shape = RoundedCornerShape(8.dp), // Скругленные углы кнопки
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Цвет кнопки
            ) {
                Text("Добавить", color = Color.White, fontWeight = FontWeight.Bold) // Текст на кнопке
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Отступ между элементами

        // Список задач
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp), // Отступы между задачами
            contentPadding = PaddingValues(bottom = 16.dp) // Внутренние отступы
        ) {
            // Перебор задач для отображения
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onDelete = { tasks.remove(task) }, // Удаление задачи
                    onEdit = { newTask -> // Редактирование задачи
                        val index = tasks.indexOf(task)
                        if (index >= 0) {
                            tasks[index] = newTask
                        }
                    }
                )
            }
        }
    }
}

// Компонент для отображения отдельной задачи
@Composable
fun TaskItem(
    task: String,
    onDelete: () -> Unit, // Коллбэк для удаления задачи
    onEdit: (String) -> Unit // Коллбэк для редактирования задачи
) {
    // Состояние, определяющее, редактируется ли задача
    var isEditing by remember { mutableStateOf(false) }
    // Состояние для текста редактируемой задачи
    var editText by remember { mutableStateOf(TextFieldValue(task)) }

    Row(
        modifier = Modifier
            .fillMaxWidth() // Занимает всю ширину экрана
            .padding(12.dp) // Внешние отступы
            .background(Color.White, RoundedCornerShape(12.dp)) // Фон и скругление углов
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)) // Рамка вокруг задачи
            .padding(16.dp), // Внутренние отступы
        verticalAlignment = Alignment.CenterVertically, // Центрирование по вертикали
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Отступы между элементами
    ) {
        if (isEditing) {
            // Если задача редактируется, отображаем поле ввода
            BasicTextField(
                value = editText,
                onValueChange = { editText = it }, // Обновление текста при вводе
                modifier = Modifier
                    .weight(1f) // Поле занимает всю оставшуюся ширину
                    .padding(end = 8.dp) // Отступ справа
                    .background(Color.White, RoundedCornerShape(8.dp)) // Фон и скругление углов
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // Рамка
                    .padding(8.dp), // Внутренние отступы
                textStyle = TextStyle(
                    color = Color.Black, // Цвет текста
                    fontWeight = FontWeight.Normal, // Стиль текста
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize // Размер текста
                ),
                decorationBox = { innerTextField ->
                    // Контейнер для отображения текста
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        innerTextField() // Вставляем текст
                    }
                }
            )

            // Кнопка для сохранения изменений
            IconButton(
                onClick = {
                    onEdit(editText.text) // Сохранение изменений
                    isEditing = false // Завершение режима редактирования
                },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Сохранить", tint = Color(0xFF6200EE))
            }
        } else {
            // Отображение текста задачи, если она не редактируется
            Text(
                text = task,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Кнопка для перехода в режим редактирования
            IconButton(
                onClick = { isEditing = true }, // Активируем режим редактирования
                modifier = Modifier.size(36.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать", tint = Color(0xFF6200EE))
            }
        }

        // Кнопка для удаления задачи
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color(0xFF6200EE))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    TodoApp()
}
