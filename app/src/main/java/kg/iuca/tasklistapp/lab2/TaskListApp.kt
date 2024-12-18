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


class TaskListApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

// Основной компонент приложения для списка задач
@Composable
fun TodoApp() {
    // Состояние для текста в поле ввода задачи
    var taskText by remember { mutableStateOf(TextFieldValue("")) }
    // Список задач, который будет динамически отображаться
    val tasks = remember { mutableStateListOf<String>() }

    // Колонка для вертикального расположения элементов интерфейса
    Column(
        modifier = Modifier
            .fillMaxSize() // Заполняет весь экран
            .padding(16.dp) // Добавляет отступы вокруг всей колонки
            .background(Color(0xFFF7F7F7)) // Задает фон для приложения
    ) {
        // Заголовок приложения
        Text(
            text = "Список задач", // Текст заголовка
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Black, // Цвет текста
                fontWeight = FontWeight.Bold // Жирный текст
            ),
            modifier = Modifier
                .padding(bottom = 16.dp) // Отступ снизу
                .align(Alignment.CenterHorizontally) // Центрирует заголовок по горизонтали
        )

        // Ряд для поля ввода задачи и кнопки "Добавить"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Добавляет отступы между элементами
        ) {
            // BasicTextField для ввода новой задачи
            BasicTextField(
                value = taskText, // Текст, введённый пользователем
                onValueChange = { taskText = it }, // Обновляет состояние taskText при вводе
                modifier = Modifier
                    .weight(1f) // Занимает оставшееся пространство в ряду
                    .height(70.dp) // Задает высоту поля ввода
                    .padding(12.dp) // Добавляет отступы вокруг поля ввода
                    .background(Color.White, RoundedCornerShape(8.dp)) // Белый фон с закругленными углами
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), // Граница для поля ввода
                textStyle = TextStyle(
                    color = Color.Black, // Цвет текста
                    fontWeight = FontWeight.Normal, // Обычный стиль текста
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize // Размер шрифта для текста
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp) // Добавляет отступы внутри поля
                    ) {
                        innerTextField() // Отображает введённый текст
                    }
                }
            )

            // Кнопка для добавления новой задачи
            Button(
                onClick = {
                    if (taskText.text.isNotBlank()) { // Проверка, что текст не пустой
                        tasks.add(taskText.text) // Добавляет новую задачу в список
                        taskText = TextFieldValue("") // Очищает поле ввода
                    }
                },
                modifier = Modifier
                    .height(50.dp) // Высота кнопки
                    .padding(start = 8.dp), // Отступ между кнопкой и полем ввода
                shape = RoundedCornerShape(8.dp), // Закругленные углы у кнопки
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Цвет кнопки
            ) {
                Text("Добавить", color = Color.White, fontWeight = FontWeight.Bold) // Текст на кнопке
            }
        }

        // Пробел между полем ввода и списком задач
        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn для отображения списка задач
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp), // Отступы между задачами
            contentPadding = PaddingValues(bottom = 16.dp) // Отступ снизу у списка
        ) {
            items(tasks) { task -> // Итерация по списку задач
                TaskItem(
                    task = task, // Каждая задача передается в компонент TaskItem
                    onDelete = { tasks.remove(task) }, // Удаляет задачу из списка
                    onEdit = { newTask ->
                        val index = tasks.indexOf(task) // Находим индекс задачи
                        if (index >= 0) {
                            tasks[index] = newTask // Обновляем задачу новым текстом
                        }
                    }
                )
            }
        }
    }
}

// Компонент для отображения отдельной задачи с функциями редактирования и удаления
@Composable
fun TaskItem(
    task: String,
    onDelete: () -> Unit, // Коллбэк для удаления задачи
    onEdit: (String) -> Unit // Коллбэк для редактирования задачи
) {
    // Состояние для определения, редактируется ли задача
    var isEditing by remember { mutableStateOf(false) }
    // Состояние для хранения текста редактируемой задачи
    var editText by remember { mutableStateOf(TextFieldValue(task)) }

    // Ряд для отображения задачи
    Row(
        modifier = Modifier
            .fillMaxWidth() // Занимает всю ширину экрана
            .padding(12.dp) // Добавляет внешние отступы
            .background(Color.White, RoundedCornerShape(12.dp)) // Белый фон с закругленными углами
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)) // Граница вокруг задачи
            .padding(16.dp), // Внутренние отступы
        verticalAlignment = Alignment.CenterVertically, // Центрирует элементы по вертикали
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Отступы между элементами в ряду
    ) {
        if (isEditing) {
            // Если задача редактируется, показываем поле для ввода
            BasicTextField(
                value = editText, // Текст редактируемой задачи
                onValueChange = { editText = it }, // Обновляем текст при вводе
                modifier = Modifier
                    .weight(1f) // Занимает оставшееся пространство
                    .padding(end = 8.dp) // Отступ справа
                    .background(Color.White, RoundedCornerShape(8.dp)) // Фон с закругленными углами
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // Граница вокруг поля
                    .padding(8.dp), // Внутренние отступы
                textStyle = TextStyle(
                    color = Color.Black, // Цвет текста
                    fontWeight = FontWeight.Normal, // Обычный стиль текста
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize // Размер шрифта
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp) // Отступы внутри поля
                    ) {
                        innerTextField() // Отображает введённый текст
                    }
                }
            )

            // Кнопка для сохранения изменений
            IconButton(
                onClick = {
                    onEdit(editText.text) // Сохраняем изменения
                    isEditing = false // Выход из режима редактирования
                },
                modifier = Modifier.size(36.dp) // Размер кнопки
            ) {
                Icon(Icons.Default.Check, contentDescription = "Сохранить", tint = Color(0xFF6200EE)) // Иконка сохранения
            }
        } else {
            // Если задача не редактируется, отображаем текст задачи
            Text(
                text = task, // Текст задачи
                modifier = Modifier.weight(1f), // Занимает оставшееся пространство
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold) // Жирный стиль текста
            )

            // Кнопка для перехода в режим редактирования
            IconButton(
                onClick = { isEditing = true }, // Активируем режим редактирования
                modifier = Modifier.size(36.dp) // Размер кнопки
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать", tint = Color(0xFF6200EE)) // Иконка редактирования
            }
        }

        // Кнопка для удаления задачи
        IconButton(
            onClick = onDelete, // Удаляет задачу при клике
            modifier = Modifier.size(36.dp) // Размер кнопки
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color(0xFF6200EE)) // Иконка удаления
        }
    }
}

// Превью компонента TodoApp для визуального отображения
@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    TodoApp()
}
