package module;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public void changeStatus(Status status) {   // обновляем статус подзадачи
        this.status = status;
        this.epic.update();
    }

    public Status getStatus() {
        return this.status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {

        return "\n Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status;
    }
}