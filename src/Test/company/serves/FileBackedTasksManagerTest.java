package test.company.serves;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    FileBackedTasksManager manager1;


    Path path;
    File file;

    @BeforeEach
    public void beforeEach() {
        path = Path.of("taskData.csv");
        file = new File(String.valueOf(path));
        manager = new FileBackedTasksManager(file);


    }


    @Test
    void loadFromFile() {
        epic = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic);
        manager.getEpicID(epicID1);
        manager1 = manager.loadFromFile(file);
        assertEquals(1, manager1.getHistoryManager().getHistory().size(), "После загрузки, количество задач в истории не совпадает");
        assertEquals(1, manager1.getEpics().size(), "После загрузки, кол-во Epic не совпадает ");
        Epic epic2 = new Epic("Задача 4", "Сходить в магазин");
        int epicID2 = manager.addEpic(epic2);
        Subtask subtask1 = new Subtask("Подзадача 1", "Купить сахар", Status.NEW, Duration.ofMinutes(45), LocalDateTime.now(), epicID2);
        manager.addSubtask(subtask1);
        task = new Task("Задача 1", "Помыть посуду", Status.NEW, Duration.ofMinutes(45), LocalDateTime.now());
        manager.addTask(task);
        manager1 = manager.loadFromFile(file);
        assertEquals(4, manager1.getSubtasks().size() + manager1.getTasks().size() + manager1.getEpics().size(), "После загрузки, общее количество задач в истории не совпадает");


    }

    @Test
    void save() {
        assertDoesNotThrow(() -> manager.save(), "Сохранение менеджера с пустым списком задач");
        epic = new Epic("Задача 3", "Сделать уроки");
        manager.addEpic(epic);
        manager1 = manager.loadFromFile(file);
        assertEquals(1, manager1.getEpics().size(), "Количество Epic менеджера после восстановления не совпало!");
        assertEquals(0, manager1.getHistory().size(), "Количество задач в истории не совпадает");
    }


}