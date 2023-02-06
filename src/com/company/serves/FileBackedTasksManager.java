package com.company.serves;


import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    static Path path = Path.of("taskData.csv");
    static private File file = new File(String.valueOf(path));
    private static final String HEAD = "id,type,name,status,description,startTime,duration,endTime,epic";

    public static void main(String[] args) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        FileBackedTasksManager manager1;
        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW,Duration.ofMinutes(30),LocalDateTime.now());
        int taskID1 = manager.addTask(task1);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW,Duration.ofMinutes(50),LocalDateTime.now());
        int taskID2 = manager.addTask(task2);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки",Duration.ofMinutes(45),LocalDateTime.now());
        int epicID1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW, Duration.ofMinutes(25),LocalDateTime.now());
        Subtask subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW, Duration.ofMinutes(35),LocalDateTime.now());
        Subtask subtask3 = new Subtask(epicID1, "Подзадача 3", "Сделать математику", Status.NEW, Duration.ofMinutes(43),LocalDateTime.now());
        int subtaskID1 = manager.addSubtask(subtask1);
        int subtaskID2 = manager.addSubtask(subtask2);
        int subtaskID3 = manager.addSubtask(subtask3);
        manager.getTaskID(taskID2);
        manager.getSubtaskID(subtaskID2);
        manager.getTaskID(taskID1);
        manager.getSubtaskID(subtaskID1);
        manager.getEpicID(epicID1);
        manager.removeSubtaskID(subtaskID1);
        manager1 = loadFromFile(file);
        Epic epic2 = new Epic("Задача 4", "Сходить в магазин");
        int epicID2 = manager1.addEpic(epic2);
        manager1.getEpicID(epicID2);

    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager file1 = new FileBackedTasksManager(file);
        List<String> allLine = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

                while (bufferedReader.ready()) {
                    String line = bufferedReader.readLine();
                    allLine.add(line);
                }
                int id = 0;
                for (int i = 1; i < (allLine.size() - 1); i++) {
                    if (allLine.get(i).isBlank()) {
                        allLine.remove(i);
                    } else {
                        Task task = file1.fromString(allLine.get(i));
                        if (task.getId() > id) {
                            id = task.getId();
                        }
                        if (task instanceof Epic) {
                            Epic epic = (Epic) task;
                            file1.collectionEpic.put(epic.getId(), epic);
                        } else if (task instanceof Subtask) {
                            Subtask subtask = (Subtask) task;
                            file1.collectionSubtask.put(subtask.getId(), subtask);
                        } else if (task instanceof Task) {
                            Task task1 = task;
                            file1.collectionTask.put(task1.getId(), task1);
                        }           //else {
//                            historyFromString(allLine.get(allLine.size() - 1));
//                        }
                        }
                }

                file1.setId(id + 1);
            } catch (IOException e) {
                throw new ManagerSaveException("Не удалось считать данные из файла.");
            }
        } else {
            throw new ManagerSaveException("Файл не найден");
        }                                                       // Не совсем понял должна ли востанавливаться история просмотров...
        ArrayList<Integer> historyTasksID = new ArrayList<>(historyFromString(allLine.get(allLine.size() - 1)));
        for (int tasksID : historyTasksID) {
            if (file1.collectionTask.containsKey(tasksID)) {
                file1.getTaskID(tasksID);
            } else if (file1.collectionEpic.containsKey(tasksID)) {
                file1.getEpicID(tasksID);
            } else if (file1.collectionSubtask.containsKey(tasksID)) {
                file1.getSubtaskID(tasksID);
            }
        }
        return file1;
    }


    @Override
    public int addTask(Task task) {
        super.addTask(task);
        save();
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
        return subtask.getId();
    }

    @Override
    public void clearTask() {
        super.clearTask();
        save();
    }

    @Override
    public void clearEpic() {
        super.clearEpic();
        save();
    }

    @Override
    public void clearSubtask() {
        super.clearSubtask();
        save();
    }

    @Override
    public Task getTaskID(int id) {
        Task task = super.getTaskID(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicID(int id) {
        Epic epic = super.getEpicID(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskID(int id) {
        Subtask subtask = super.getSubtaskID(id);
        save();
        return subtask;
    }

    @Override
    public void removeTaskID(int id) {
        super.removeTaskID(id);
        save();
    }

    @Override
    public void removeEpicID(int id) {
        super.removeEpicID(id);
        save();
    }

    @Override
    public void removeSubtaskID(int id) {
        super.removeSubtaskID(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    public void save() {
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось найти файл для записи данных");
        }
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEAD);
            for (Task task : getTasks()) {
                writer.write(toString(task));
            }
            for (Epic epic : getEpics()) {
                writer.write(toString(epic));
            }
            for (Subtask subtask : getSubtasks()) {
                writer.write(toString(subtask));
            }

            writer.write("\n");
            writer.write("\n" + historyToString(getHistoryManager()));

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл");
        }


    }

    public String toString(Task task) {

        String type = null;
        int id = task.getId();
        String name = task.getName();
        Status status = task.getStatus();
        String description =task.getDescription();
        String duration = String.valueOf(task.getDuration());
        String startTime = String.valueOf(task.getStartTime());
        String endTime = String.valueOf(task.getEndTime());
        if (task instanceof Task) {
            type = String.valueOf(task.getType());
        } else if (task instanceof Epic) {
            type = String.valueOf(task.getType());
        }
        String s = String.valueOf(task.getClass());
        if (task instanceof Subtask) {
            int epicID = ((Subtask) task).getEpicID();
            String.valueOf(task.getType());
            return "\n" + id + "," + type + "," +
                    name + "," + status + ","  +
                    description + "," + duration+ "," + startTime +
                    "," + endTime + "," + epicID;
        }
        return "\n" + id + "," + type + "," +
                name + "," + status + "," +
                description + "," + duration + "," + startTime +
                "," + endTime ;
    }

    public Task fromString(String value) {
        String[] splitTask = value.split(",");
        int id = Integer.parseInt(splitTask[0]);
        if (splitTask[1].equals("SUBTASK")) {
            Subtask subtask = new Subtask(splitTask[2], splitTask[4], id, Status.valueOf(splitTask[3].toUpperCase()));
            subtask.setEpicID(Integer.parseInt(splitTask[8]));
            subtask.setType(TaskType.SUBTASK);
            subtask.setDuration(Duration.parse(splitTask[5]));
                subtask.setStartTime(LocalDateTime.parse(splitTask[6]));
                subtask.setEndTime(LocalDateTime.parse(splitTask[7]));
            return subtask;
        } else if (splitTask[1].equals("EPIC")) {
           Epic epic = new Epic(splitTask[2], splitTask[4], id);
            epic.setStatus(Status.valueOf(splitTask[3].toUpperCase()));
            epic.setType(TaskType.EPIC);
                epic.setDuration("null".equals(splitTask[5]) ? null : Duration.parse(splitTask[5]));
                epic.setStartTime("null".equals(splitTask[6]) ? null : LocalDateTime.parse(splitTask[6]));
                epic.setEndTime("null".equals(splitTask[7]) ? null : LocalDateTime.parse(splitTask[7]));
            return epic;
        } else {
            Task task = new Task(splitTask[2], splitTask[4], id, Status.valueOf(splitTask[3].toUpperCase()));
            task.setType(TaskType.TASK);
            task.setDuration("null".equals(splitTask[5]) ? null : Duration.parse(splitTask[5]));
            task.setStartTime("null".equals(splitTask[6]) ? null : LocalDateTime.parse(splitTask[6]));
            task.setEndTime("null".equals(splitTask[7]) ? null : LocalDateTime.parse(splitTask[7]));
            return task;

        }
    }

    static List<Integer> historyFromString(String value) {

        List<Integer> historyTaskID = new ArrayList<>();
        if (!value.isBlank()) {
            String[] taskID = value.split(",");

            for (String id : taskID) {
                historyTaskID.add(Integer.parseInt(id));
            }
            return historyTaskID;
        } else {
            return historyTaskID;
        }
    }

    static String historyToString(HistoryManager manager) {
        List<Task> taskHistory = manager.getHistory();

        StringBuilder sb = new StringBuilder();
        if (taskHistory.isEmpty()) {
            return "";
        }

        for (Task task : taskHistory) {
            sb.append(task.getId() + ",");

        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }


}
