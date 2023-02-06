package com.company.Test.Serves;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.InMemoryTaskManager;
import com.company.serves.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest<T extends TaskManager> {
   TaskManager manager;
    Task task;
    Epic epic;
    Subtask subtask1;
    Subtask subtask2;


    @Test
    void addTask() {
        task = new Task("Задача 1", "Помыть посуду", Status.NEW);
        final int taskID = manager.addTask(task);
        final Task saveTask = manager.getTaskID(taskID);
        assertNotNull(saveTask, "Задача не найдена.");
        assertEquals(task, saveTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpic() {
        epic = new Epic("Задача 3", "Сделать уроки");
        final int epicID = manager.addEpic(epic);
        final Epic saveEpic = manager.getEpicID(epicID);
        assertNotNull(saveEpic, "Задача не найдена.");
        assertEquals(epic, saveEpic, "Задачи не совпадают.");

        final List<Epic> epics = manager.getEpics();
        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void addSubtask() {
        epic = new Epic("Задача 3", "Сделать уроки");
        final int epicID1 = manager.addEpic(epic);
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        final int subtaskID = manager.addSubtask(subtask1);
        final Subtask saveSubtask = manager.getSubtaskID(subtaskID);
        assertNotNull(saveSubtask, "Задача не найдена.");
        assertEquals(subtask1, saveSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = manager.getSubtasks();

        assertNotNull(subtasks, "Задачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask1, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void getTasks() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.getTasks(), "Список задач пуст");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", Status.NEW);
        manager.addTask(task);
        assertEquals(1, manager.getTasks().size(), "Количество задач не соответствует!");
    }

    @Test
    void getSubtasks() {
        // С пустым списком
        epic = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic);
        assertDoesNotThrow(() -> manager.getSubtasks(), "Список подзадач пуст");
        // Со стандартным поведением
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        manager.addSubtask(subtask1);
        assertEquals(1, manager.getSubtasks().size(), "Количество подзадач не соответствует!");
    }

    @Test
    void getEpics() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.getEpics(), "Список Epic пуст");
        // Со стандартным поведением
        epic = new Epic("Задача 3", "Сделать уроки");
        manager.addEpic(epic);
        assertEquals(1, manager.getEpics().size(), "Количество Epic не соответствует!");
    }

    @Test
    void clearTask() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.clearTask(), "Попытка очистки пустого списка задач");
        // Со стандартным поведением
        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.clearTask();
        assertEquals(0, manager.getTasks().size(), "После очистки всех задач, список не пустой");
    }

    @Test
    void clearEpic() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.clearEpic(), "Попытка очистки пустого списка Epic");
        // Со стандартным поведением
        epic = new Epic("Задача 3", "Сделать уроки");
        manager.addEpic(epic);
        manager.clearEpic();
        assertEquals(0, manager.getEpics().size(), "Список Epic не пуст после очистки");
        assertEquals(0, manager.getSubtasks().size(), "Список подзадач не пуст после очистки списка Epic");
    }

    @Test
    void clearSubtask() {
        epic = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic);
        // С пустым списком
        assertDoesNotThrow(() -> manager.clearSubtask(), "Попытка очистки пустого списка подзадач");
        // Со стандартным поведением
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW);
        manager.clearSubtask();
        assertEquals(0, manager.getSubtasks().size(), "После очистки подзадач, список не пуст");

    }

    @Test
    void getTaskID() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.getTaskID(1), "Запрос несуществующей задачи");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", Status.NEW);
        manager.addTask(task);
        assertEquals(task, manager.getTaskID(1));
    }

    @Test
    void getEpicID() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.getEpicID(1), "Запрос несуществующего Epic");
        // Со стандартным поведением
        epic = new Epic("Задача 3", "Сделать уроки");
        manager.addEpic(epic);
        assertEquals(epic, manager.getEpicID(1));

    }

    @Test
    void getSubtaskID() {
        epic = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic);
        // С пустым списком
        assertDoesNotThrow(() -> manager.getSubtaskID(1), "Запрос несуществующей подзадачи");
        // Со стандартным поведением
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        manager.addSubtask(subtask1);
        assertEquals(subtask1, manager.getSubtaskID(2));
    }

    @Test
    void removeTaskID() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.removeTaskID(1), "Попытка удаления несуществующей задачи");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", Status.NEW);
        manager.addTask(task);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW);
        manager.addTask(task2);
        assertEquals(2, manager.getTasks().size(), "После добавления задач, количество - не соответствует");
        manager.removeTaskID(1);
        assertEquals(1, manager.getTasks().size(), "После удаление задачи, количество - не соответствует");
    }

    @Test
    void removeEpicID() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.removeEpicID(1), "Попытка удаления несуществующей Epic");
        // Со стандартным поведением
        epic = new Epic("Задача 4", "Сходить в магазин");
        manager.addEpic(epic);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки");
        manager.addEpic(epic1);
        assertEquals(2, manager.getEpics().size(), "После добавления Epic, количество - не соответствует");
        manager.removeEpicID(1);
        assertEquals(1, manager.getEpics().size(), "После удаление Epic, количество - не соответствует");


    }

    @Test
    void removeSubtaskID() {
        epic = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic);
        // С пустым списком
        assertDoesNotThrow(() -> manager.removeSubtaskID(1), "Попытка удаления несуществующей подзадачи");
        // Со стандартным поведением
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        manager.addSubtask(subtask1);
        subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW);
        manager.addSubtask(subtask2);
        assertEquals(2, manager.getSubtasks().size(), "После добавления подзадач, количество - не соответствует");
        manager.removeSubtaskID(2);
        assertEquals(1, manager.getSubtasks().size(), "После удаление подзадач, количество - не соответствует");
    }

    @Test
    void updateTask() {
        // С неверным идентификатором задачи (пустой и/или несуществующий идентификатор)
        task = new Task("Задача 1", "Помыть посуду", Status.NEW);
        assertDoesNotThrow(() -> manager.updateTask(task),
                "Попытка обновления не существующей задачи");
        // Со стандартным поведением
        manager.addTask(task);
        Task task1 = new Task("Задача 1", "Помыть посуду", 1, Status.IN_PROGRESS);
        manager.updateTask(task1);
        assertEquals(Status.IN_PROGRESS, manager.getTaskID(1).getStatus());
    }

    @Test
    void updateEpic() {
// С неверным идентификатором задачи (пустой и/или несуществующий идентификатор)
        epic = new Epic("Задача 3", "Сделать уроки");
        assertDoesNotThrow(() -> manager.updateEpic(epic), "Попытка обновления не существующей Epic");
        // Со стандартным поведением
        manager.addEpic(epic);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки после уборки", 1);
        manager.updateEpic(epic1);
        assertEquals("Сделать уроки после уборки", manager.getEpicID(1).getDescription());
    }

    @Test
    void updateSubtask() {
        epic = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic);
        // С неверным идентификатором задачи (пустой и/или несуществующий идентификатор)
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        assertDoesNotThrow(() -> manager.updateSubtask(subtask1), "Попытка обновления не существующей подзадачи");
        // Со стандартным поведением
        manager.addSubtask(subtask1);
        subtask2 = new Subtask("Подзадача 1", "Сделать физику", 2, Status.DONE, epicID1);
        manager.updateSubtask(subtask2);
        assertEquals(Status.DONE, manager.getSubtaskID(2).getStatus());

    }

    @Test
    void getHistory() {
        // С пустым списком
        assertDoesNotThrow(() -> manager.getHistory(), "Запрос пустой истории");
        assertNotNull(manager.getHistory(), "История не пуста");
        // Со стандартным поведением
        task = new Task("Задача 1", "Помыть посуду", Status.NEW);
        manager.addTask(task);
        manager.getTaskID(1);
        assertEquals(1, manager.getHistory().size());
    }
    @Test
    void getPrioritizedTasks(){
        assertNotNull(manager. getSortedTaskSet(), "Список задач не пустой!");
        task = new Task(1,"Задача 1", "Помыть посуду", Status.NEW,Duration.ofMinutes(40),LocalDateTime.now());
        manager.addTask(task);
        Task task2 = new Task(1, "Задача 2", "Сделать ТЗ", Status.NEW,Duration.ofMinutes(32),LocalDateTime.now());
        manager.addTask(task2);
        assertEquals(2,manager.getSortedTaskSet().size(),"После добавления задач - кол-во задач не соответствует");
        epic = new Epic("Задача 3", "Сделать уроки", Duration.ofMinutes(45), LocalDateTime.now());
       int epicID1= manager.addEpic(epic);
        subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        manager.addSubtask(subtask1);
        subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW);
        manager.addSubtask(subtask2);
        assertEquals(4,manager.getSortedTaskSet().size(),"После добавления задач - кол-во задач не соответствует");
        manager.removeSubtaskID(subtask1.getId());
        assertEquals(3,manager.getSortedTaskSet().size(), "После удаления задачи - кол-во задач не соответствует");


    }

}