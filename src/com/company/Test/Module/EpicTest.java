package com.company.Test.Module;

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


    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    public void changeOfStatusEpic() {
        // Проверка когда: пустой список подзадач.
        epic = new Epic("Эпик 1", "Сделать уроки");
        int id =manager.addEpic(epic);
        assertEquals(Status.NEW, epic.getStatus());
        // Проверка когда: Все подзадачи со статусом NEW.
        subtask = new Subtask(id, "Сделать физику", "Решить задачи", Status.NEW);
        subtask1 = new Subtask(id, "Сделать Математику", "Решить уравнения", Status.NEW);
        subtask2 = new Subtask(id, "Сделать Русский", "Выучить правила", Status.NEW);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.NEW, epic.getStatus());

        // Проверка когда: Все подзадачи со статусом DONE.
        subtask.setStatus(Status.DONE);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.DONE, epic.getStatus());

        // Проверка когда: Подзадачи со статусами NEW и DONE.
        subtask.setStatus(Status.DONE);
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        // Проверка когда: Подзадачи со статусом IN_PROGRESS.
        subtask.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

}

