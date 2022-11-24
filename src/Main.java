import module.Epic;
import module.Status;
import module.Subtask;
import module.Task;
import serves.Manager;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW);
        List<Subtask> subtasks = new ArrayList<>();
        Subtask subtask1 = new Subtask("Подзадача 1", "Сделать физику", Status.NEW);
        Subtask subtask2 = new Subtask("Подзадача 2", "Сделать информатику", Status.NEW);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки", subtasks);
        List<Subtask> subtasks1 = new ArrayList<>();
        Subtask subtask3 = new Subtask("Подзадача 1", "Купить хлеб", Status.NEW);
        subtasks1.add(subtask3);
        Epic epic2 = new Epic("Задача 4", "Сходить в магазин", subtasks1);
        manager.add(task1);
        manager.add(task2);
        manager.add(epic1);
        manager.add(epic2);
        System.out.println(task1.toString() + task2.toString() + epic1.toString() + epic2.toString());
        Task task3 = new Task(task1.getName(), task1.getDescription(), Status.DONE);
        task3.setId(task1.getId());
        manager.updateTaskStatus(task3);
        System.out.println(manager.collectionTask.get(task1.getId()));
        manager.removeID(1);
        System.out.println(manager.collectionTask.get(task1.getId()));
        Subtask subtask4 = new Subtask(subtask2.getName(), subtask2.getDescription(), Status.DONE);
        subtask4.setId(subtask2.getId());
        epic1.add(subtask4);
        System.out.println(epic1);
    }
}