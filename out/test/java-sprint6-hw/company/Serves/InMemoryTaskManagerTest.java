package Test.company.serves;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.serves.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest extends TaskManagerTest {

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void updateEpicStatus() {
        // С пустым списком
        epic = new Epic("Задача 3", "Сделать уроки");
        assertDoesNotThrow(() -> manager.updateEpic(epic), "Попытка обновления статуса не существующей Epic");
        // Со стандартным поведением
        int epicID = manager.addEpic(epic);
        assertEquals(Status.NEW, manager.getEpicID(1).getStatus(), "Не соответствует статус новой Epic");
        subtask1 = new Subtask(epicID, "Подзадача 1", "Сделать физику", Status.NEW);
        manager.addSubtask(subtask1);
        subtask2 = new Subtask(epicID, "Подзадача 2", "Сделать информатику", Status.IN_PROGRESS);
        manager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, manager.getEpicID(1).getStatus(), "Обновленный статус не соответствует");
    }

    @Test
    void getSubtask() {
        epic = new Epic("Задача 3", "Сделать уроки");
        // С пустым списком
        assertDoesNotThrow(() -> manager.getSubtasks(), "Попытка вывести пустой список подзадач");
        assertNotNull(manager.getSubtasks(), "Подзадачи не найдены!");
        // Со стандартным поведением
        int epicID = manager.addEpic(epic);
        subtask1 = new Subtask(epicID, "Подзадача 1", "Сделать физику", Status.NEW);
        manager.addSubtask(subtask1);
        subtask2 = new Subtask(epicID, "Подзадача 2", "Сделать информатику", Status.IN_PROGRESS);
        manager.addSubtask(subtask2);
        assertEquals(2, manager.getSubtasks().size(), "Количество подзадач не соответствует");
    }


}