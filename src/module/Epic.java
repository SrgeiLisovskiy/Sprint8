package module;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String name, String description, List<Subtask> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
        for (Subtask subtask : subtasks) {
            subtask.setEpic(this);
        }
        update();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void update() {              // обнавляем статус Epic на основании его подзадач
        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        for (Subtask subtask : subtasks) {
            if (subtask.status == Status.NEW) {
                hasNew = true;
            } else if (subtask.status == Status.IN_PROGRESS) {
                hasInProgress = true;
            } else if (subtask.status == Status.DONE) {
                hasDone = true;
            }
        }
        if (hasNew && !hasInProgress && !hasDone) {
            this.status = Status.NEW;
        } else if (hasDone && !hasNew && !hasInProgress) {
            this.status = Status.DONE;
        } else {
            this.status = Status.IN_PROGRESS;
        }
    }

    public void add(Subtask subtask) {       // обновляем подзадачу по её ID
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i).id == subtask.id) {
                subtasks.remove(i);
                subtasks.add(i, subtask);
            }
        }
        update();
    }

    public List<Subtask> getSubtasks() {  // список подзадач
        return this.subtasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\n Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status + ", subtasks=" + subtasks + "}";
    }
}
