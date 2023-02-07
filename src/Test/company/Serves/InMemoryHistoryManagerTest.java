package Test.company.Serves;

import com.company.module.Status;
import com.company.module.Task;
import com.company.serves.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager;
    Task task;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void getHistory() {
        // С пустым списком
        assertNotNull(historyManager, "История не пустая!");
        assertEquals(0, historyManager.getHistory().size(), "Не соответствует кол-во задач в пустой истории");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", 1, Status.NEW);
        historyManager.add(task);
        task1 = new Task("Задача 2", "Помыть машину", 2, Status.NEW);
        historyManager.add(task1);
        task2 = new Task("Задача 3", "Сделать ТЗ", 3, Status.NEW);
        historyManager.add(task2);
        task3 = new Task("Задача 4", "Сделать уроки", 4, Status.NEW);
        historyManager.add(task3);
        assertEquals(4, historyManager.getHistory().size(), " После добавления -не соответствует кол-во задач в истории");
        // Дублирование
        historyManager.add(task);
        assertEquals(4, historyManager.getHistory().size(), " После дублирования -не соответствует кол-во задач в истории");
        //После удаления
        historyManager.remove(2);
        assertEquals(3, historyManager.getHistory().size(), " После удаления из средины списка -не соответствует кол-во задач в истории");
        historyManager.remove(4);
        assertEquals(2, historyManager.getHistory().size(), " После удаления с конца -не соответствует кол-во задач в истории");
        historyManager.remove(1);
        assertEquals(1, historyManager.getHistory().size(), " После удаления с начала списка -не соответствует кол-во задач в истории");

    }

    @Test
    void remove() {
        // Пустая история задач.
        assertDoesNotThrow(() -> historyManager.remove(1), "Удаление из пустой истории");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", 1, Status.NEW);
        historyManager.add(task);
        task1 = new Task("Задача 2", "Помыть машину", 2, Status.NEW);
        historyManager.add(task1);
        task2 = new Task("Задача 3", "Сделать ТЗ", 3, Status.NEW);
        historyManager.add(task2);
        task3 = new Task("Задача 4", "Сделать уроки", 4, Status.NEW);
        historyManager.add(task3);
        assertEquals(4, historyManager.getHistory().size(), " После добавления -не соответствует кол-во задач в истории");
        //После удаления
        historyManager.remove(2);
        assertEquals(3, historyManager.getHistory().size(), " После удаления из средины списка -не соответствует кол-во задач в истории");
        historyManager.remove(4);
        assertEquals(2, historyManager.getHistory().size(), " После удаления с конца -не соответствует кол-во задач в истории");
        historyManager.remove(1);
        assertEquals(1, historyManager.getHistory().size(), " После удаления с начала списка -не соответствует кол-во задач в истории");

    }

    @Test
    void add() {
        // С пустым списком
        assertNotNull(historyManager, "История не пустая!");
        assertEquals(0, historyManager.getHistory().size(), "Не соответствует кол-во задач в пустой истории");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", 1, Status.NEW);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), " После добавления -не соответствует кол-во задач в истории");
        // Дублирование
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), " После дублирования -не соответствует кол-во задач в истории");
    }


}