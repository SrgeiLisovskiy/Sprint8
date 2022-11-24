package module;

public class Task {
    String name;
    String description;
    int id;
    Status status;
    static int counter = 1;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = counter++;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = counter++;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "\nTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
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
}

