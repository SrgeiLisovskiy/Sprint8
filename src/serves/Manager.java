package serves;

import module.Epic;
import module.Subtask;
import module.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Manager {


    public HashMap<Integer, Task> collectionTask = new HashMap<>();
    public HashMap<Integer, Epic> collectionEpic = new HashMap<>();
    public HashMap<Integer, Subtask> collectionSubtask = new HashMap<>();


    public void add(Task task) {                     // добавляем задачи
        if (task.getClass() == Task.class) {
            collectionTask.put(task.getId(), task);
        } else if (task.getClass() == Epic.class) {
            collectionEpic.put(task.getId(), (Epic) task);
            for (Subtask subtask : ((Epic) task).getSubtasks()) {
                add(subtask);
            }
        } else if (task.getClass() == Subtask.class) {
            collectionSubtask.put(task.getId(), (Subtask) task);
        }
    }

    public List<Task> getTasks() {                               //Выводим список всех задач
        List<Task> tasks = new ArrayList<>();
        tasks.addAll((Collection<? extends Task>) collectionTask);
        tasks.addAll((Collection<? extends Task>) collectionEpic);
        tasks.addAll((Collection<? extends Task>) collectionSubtask);
        return tasks;
    }

    public void clearAllTask() {         //Удаляем все задачи
        collectionEpic.clear();
        collectionTask.clear();
        collectionSubtask.clear();
    }

    public Task getByID(int id) {            // поиск по ID
        if (collectionTask.get(id) != null) {
            return collectionTask.get(id);
        } else if (collectionEpic.get(id) != null) {
            return collectionEpic.get(id);
        } else if (collectionSubtask.get(id) != null) {
            return collectionSubtask.get(id);
        }
        return null;
    }

    public void updateTask(Task task) {    // обновление задачи
        add(task);
    }

    public void removeID(int id) {               // удаляем по ID
        if (collectionTask.get(id) != null) {
            collectionTask.remove(id);
        } else if (collectionEpic.get(id) != null) {
            collectionEpic.remove(id);
        } else if (collectionSubtask.get(id) != null) {
            collectionSubtask.remove(id);
        }
    }

    public List<Subtask> getEpicSubtask(int id) {        //получение списка подзадач по ID=>Epic
        return collectionEpic.get(id).getSubtasks();
    }

    public void updateTaskStatus(Task task) {    // обновляет статус
        add(task);
    }
}

