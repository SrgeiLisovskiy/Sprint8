package Test.company.module;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.serves.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;
    Subtask subtask;
    Subtask subtask1;
    Subtask subtask2;
    InMemoryTaskManager manager;
    int id;


    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
        epic = new Epic("Эпик 1", "Сделать уроки");
        id = manager.addEpic(epic);

    }

    @Test
    public void checkingTheListOfSubtasksIsEmpty() {              // Проверка когда: пустой список подзадач.
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void checkingTheListOfSubtasksWithStatusNew() {         // Проверка когда: Все подзадачи со статусом NEW.
        subtask = new Subtask(id, "Сделать физику", "Решить задачи", Status.NEW);
        subtask1 = new Subtask(id, "Сделать Математику", "Решить уравнения", Status.NEW);
        subtask2 = new Subtask(id, "Сделать Русский", "Выучить правила", Status.NEW);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void checkingTheListOfSubtasksWithStatusDone() {      // Проверка когда: Все подзадачи со статусом DONE.
        subtask = new Subtask(id, "Сделать физику", "Решить задачи", Status.DONE);
        subtask1 = new Subtask(id, "Сделать Математику", "Решить уравнения", Status.DONE);
        subtask2 = new Subtask(id, "Сделать Русский", "Выучить правила", Status.DONE);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void checkingTheListOfSubtasksWithStatusNewAndDone() {      // Проверка когда: Подзадачи со статусами NEW и DONE.
        subtask = new Subtask(id, "Сделать физику", "Решить задачи", Status.DONE);
        subtask1 = new Subtask(id, "Сделать Математику", "Решить уравнения", Status.NEW);
        subtask2 = new Subtask(id, "Сделать Русский", "Выучить правила", Status.DONE);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void checkingTheListOfSubtasksWithStatusInProgress() {         // Проверка когда: Подзадачи со статусом IN_PROGRESS.
        subtask = new Subtask(id, "Сделать физику", "Решить задачи", Status.IN_PROGRESS);
        subtask1 = new Subtask(id, "Сделать Математику", "Решить уравнения", Status.IN_PROGRESS);
        subtask2 = new Subtask(id, "Сделать Русский", "Выучить правила", Status.IN_PROGRESS);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

}

