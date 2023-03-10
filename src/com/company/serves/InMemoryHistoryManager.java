package com.company.serves;

import com.company.module.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    public HashMap<Integer, Node<Task>> keyHistoryTask = new HashMap<>();

    CustomLinkedList<Task> linkedList = new CustomLinkedList<>();


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
        Node<Task> taskNode = linkedList.linkLast(task);
        if (keyHistoryTask.containsKey(i)) {
            linkedList.removeNode(keyHistoryTask.get(i));
        }
        keyHistoryTask.put(i, taskNode);

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
            while (nodeTask != null) {
                tasksList.add(nodeTask.data);
                nodeTask = nodeTask.next;
            }
            return tasksList;

        }

        public void removeNode(Node<Task> node) {
            if (node != null) {
                Node<Task> nodeRight = node.next;
                Node<Task> nodeLeft = node.prev;
                if (nodeLeft == null && nodeRight == null) {
                    head = nodeLeft;
                    tail = nodeRight;
                } else if (nodeRight == null) {
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
            } else {
                System.out.println("???????????? ???????????????????? ??????????????");
            }
        }

    }
}
