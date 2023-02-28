package test.company.serves;

import com.company.http.HttpTaskServer;
import com.company.http.KVServer;
import com.company.module.Task;
import com.company.serves.HttpTaskManager;
import com.company.serves.Managers;
import com.company.serves.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HttpTaskManagerTest extends  FileBackedTasksManagerTest{
    TaskManager taskManager;
    HttpTaskManager httpTaskManager;
    Task task;
    @Override
    public void beforeEach() throws IOException {
        taskManager = Managers.getDefault();
        new KVServer().start();
        new HttpTaskServer(taskManager).start();
    }

    @Test
    void save(){

    }
}
