package com.company.serves;

import com.company.module.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public HashMap<Integer, Node<Task>> keyHistoryTask = new HashMap<>();

    CustomLinkedList<Task> linkedList = new CustomLinkedList<Task>();


    @Override
    public ArrayList<Task> getHistory() {
        return linkedList.getTasks();
    }

    @Override
    public void remove(int id) {
        linkedList.removeNode(keyHistoryTask.get(id));
        keyHistoryTask.remove(id);

    }

    @Override
    public void add(Task task) {
        int i = task.getId();
        if (!keyHistoryTask.containsKey(i)) {
            Node<Task> taskNode = linkedList.linkLast(task);
            keyHistoryTask.put(i, taskNode);
        } else if (keyHistoryTask.containsKey(i)) {
            linkedList.removeNode(keyHistoryTask.get(i));
            Node<Task> taskNodeUpdate = linkedList.linkLast(task);
            keyHistoryTask.put(i, taskNodeUpdate);
        }

    }


    public class CustomLinkedList<T> {
        Node<Task> head;
        Node<Task> tail;
        private int size = 0;


        public Node<Task> linkLast(Task task) {
            Node<Task> newNode = new Node<>(tail, task, null);
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
            }

            tail = newNode;

            size++;
            return newNode;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasksList = new ArrayList<>();
            Node<Task> nodeTask = head;

            for (int i = 0; i < size; i++) {
                    tasksList.add( nodeTask.data);
                    nodeTask = nodeTask.next;
            }
            return tasksList;

        }

        public void removeNode(Node<Task> node) {
            Node<Task> nodeRight = node.next;
            Node<Task> nodeLeft = node.prev;
            if (nodeRight == null) {
                Node<Task> newTail = tail.prev;
                newTail.next = null;
                tail = newTail;
            } else if (nodeLeft == null) {
                Node<Task> newHead = head.next;
                newHead.prev = null;
                head = newHead;
            } else {
                nodeLeft.next = nodeRight;
                nodeRight.prev = nodeLeft;
            }
            size--;
        }

    }
}
