package com.company.http;

import com.company.adapters.DurationTypeAdapter;
import com.company.adapters.LocalDateTimeTypeAdapter;
import com.company.module.Epic;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.HttpTaskManager;
import com.company.serves.Managers;
import com.company.serves.TaskManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private final TaskManager taskManager ;
    public static final int PORT = 8080;    //Порт для прослушивания
    private final HttpServer server;
   private final Gson gson;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefault());
    }
    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks/", this::handler);

    }

    public static void main(String[] args) throws IOException {
        final HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
    }

    public void start() {
        System.out.println("Сервер запущен на порту: " + PORT);
        server.start();
    }

    public void stop() {
        System.out.println("Сервер остановлен");
        server.stop(1);
    }


    private void handler(HttpExchange h) {
        try  {
            System.out.println("\n/tasks: " + h.getRequestURI());
            final String path = h.getRequestURI().getPath().substring(7);
            switch (path) {
                case "" : {
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("/Ждёт GET запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    final String response = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(h, response);
                }
                case "history" : {
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("/history ждёт GET запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    final String response = gson.toJson(taskManager.getHistory());
                    sendText(h, response);
                }
                case "task/" : handleTask(h);
                case "subtask/" : handleSubtask(h);
                case "subtask/epic/" : {
                    if (!h.getRequestMethod().equals("GET")) {
                        System.out.println("/subtask/epic ждёт GET запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                    }
                    final String query = h.getRequestURI().getQuery();
                    String idStr = query.substring(3);
                    final int id = Integer.parseInt(idStr);
                    final List<Subtask> subtasks = taskManager.getEpicSubtask(id);
                    final String response = gson.toJson(subtasks);
                    System.out.println("Получили подзадачи эпика с id=" + id);
                    sendText(h, response);
                }
                case "epic/" : handleEpic(h);
                default : {
                    System.out.println("Неизвестный запрос: " + h.getRequestURI());
                    h.sendResponseHeaders(404,0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            h.close();
        }
        h.close();
    }

    public void handleTask(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        final String query = h.getRequestURI().getQuery();
        switch (method) {
            case "GET" : {
                if (query == null) {
                    final List<Task> tasks = taskManager.getTasks();
                    final String response = gson.toJson(tasks);
                    System.out.println("Получили все задачи");
                    sendText(h, response);
                    return;
                }
                String idStr = query.substring(3);
                final int id = Integer.parseInt(idStr);
                final Task task = taskManager.getTaskID(id);
                final String response = gson.toJson(task);
                System.out.println("Получили задачу с id= " + id);
                sendText(h, response);
            }
            case "POST" : {
                String json = readText(h);
                if ((json.isEmpty())) {
                    System.out.println("В теле запроса указывается Body с пустой задачей!");
                    h.sendResponseHeaders(404, 0);
                    return;
                }
                final Task task = gson.fromJson(json, Task.class);
                final Integer id = task.getId();
                if (id != null) {
                    taskManager.updateTask(task);
                    System.out.println("Задача с id=" + id + " обновлена");
                    h.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addTask(task);
                    System.out.println("Создана задача с id=" + task.getId());
                    final String response = gson.toJson(task);
                    sendText(h, response);
                }
            }
            case "DELETE" : {
                if (query == null) {
                    taskManager.clearTask();
                    System.out.println("Все задачи удалены");
                    h.sendResponseHeaders(200, 0);
                }
                String idStr = query.substring(3);
                final int id = Integer.parseInt(idStr);
                taskManager.removeTaskID(id);
                System.out.println("Задача с id= " + id + ",удалена");
                h.sendResponseHeaders(200, 0);
            }
        }
    }

    public void handleSubtask(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        System.out.println("Началась обработка " + method + "  запроса от клиента.");
        final String query = h.getRequestURI().getQuery();
        System.out.println("Началась обработка " + method + "  запроса от клиента.");
        switch (method) {
            case "GET" : {
                if (query == null) {
                    final List<Subtask> subtasks = taskManager.getSubtasks();
                    final String response = gson.toJson(subtasks);
                    System.out.println("Получили все подзадачи");
                    sendText(h, response);
                    return;
                }
                String idStr = query.substring(3);
                final int id = Integer.parseInt(idStr);
                final Subtask subtask = taskManager.getSubtaskID(id);
                final String response = gson.toJson(subtask);
                System.out.println("Получили подзадачу с id= " + id);
                sendText(h, response);
            }
            case "POST" : {
                String json = readText(h);
                if ((json.isEmpty())) {
                    System.out.println("В теле запроса указывается Body с пустой подзадачей!");
                    h.sendResponseHeaders(404, 0);
                    return;
                }
                final Subtask subtask = gson.fromJson(json, Subtask.class);
                final Integer id = subtask.getId();
                if (id != null) {
                    taskManager.updateSubtask(subtask);
                    System.out.println("Подзадача с id=" + id + " обновлена");
                    h.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addSubtask(subtask);
                    System.out.println("Создана подзадача с id=" + subtask.getId());
                    final String response = gson.toJson(subtask);
                    sendText(h, response);
                }
            }
            case "DELETE" : {
                if (query == null) {
                    taskManager.clearSubtask();
                    System.out.println("Все подзадачи удалены");
                    h.sendResponseHeaders(200, 0);
                }
                String idStr = query.substring(3);
                final int id = Integer.parseInt(idStr);
                taskManager.removeSubtaskID(id);
                System.out.println("Подзадача с id= " + id + ",удалена");
                h.sendResponseHeaders(200, 0);
            }
        }
    }
    public void handleEpic(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        System.out.println("Началась обработка " + method + "  запроса от клиента.");
        final String query = h.getRequestURI().getQuery();
        System.out.println("Началась обработка " + method + "  запроса от клиента.");
        switch (method) {
            case "GET" : {
                if (query == null) {
                    final List<Epic> epics = taskManager.getEpics();
                    final String response = gson.toJson(epics);
                    System.out.println("Получили все Epic-задачи");
                    sendText(h, response);
                    return;
                }
                String idStr = query.substring(3);
                final int id = Integer.parseInt(idStr);
                final Epic epic = taskManager.getEpicID(id);
                final String response = gson.toJson(epic);
                System.out.println("Получили Epic-задачу с id= " + id);
                sendText(h, response);
            }
            case "POST" : {
                String json = readText(h);
                if ((json.isEmpty())) {
                    System.out.println("В теле запроса указывается Body с пустой Epic-задачей!");
                    h.sendResponseHeaders(404, 0);
                    return;
                }
                final Epic epic = gson.fromJson(json, Epic.class);
                final Integer id = epic.getId();
                if (id != null) {
                    taskManager.updateEpic(epic);
                    System.out.println("Epic-задача с id=" + id + " обновлена");
                    h.sendResponseHeaders(200, 0);
                } else {
                    taskManager.addEpic(epic);
                    System.out.println("Создана Epic-задача с id=" + epic.getId());
                    final String response = gson.toJson(epic);
                    sendText(h, response);
                }
            }

            case "DELETE" : {
                if (query == null) {
                    taskManager.clearEpic();
                    System.out.println("Все Epic-задачи удалены");
                    h.sendResponseHeaders(200, 0);
                }
                String idStr = query.substring(3);
                final int id = Integer.parseInt(idStr);
                taskManager.removeEpicID(id);
                System.out.println("Epic-задача с id= " + id + ",удалена");
                h.sendResponseHeaders(200, 0);
            }
        }
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
