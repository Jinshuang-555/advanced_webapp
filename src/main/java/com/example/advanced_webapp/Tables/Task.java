package com.example.advanced_webapp.Tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36)")
    @Id
    private String taskId;

    @ManyToOne
    @JoinColumn(name = "listId")
    private List list;

    private String summary;
    private String Task;
    private int priority; // 1-high 2-medium 3-low
    private int status; // 1-todo 2-completed 3- overdue

    @OneToMany(mappedBy = "task")
    java.util.List<Remainder> remainderList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "task_tags",
            joinColumns = @JoinColumn(name = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<Tag> tags = new HashSet<>();

    public Task() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List getLists() {
        return list;
    }

    public void setLists(List list) {
        this.list = list;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public java.util.List<Remainder> getRemaindersList() {
        return remainderList;
    }

    public void setRemaindersList(java.util.List<Remainder> remainderList) {
        this.remainderList = remainderList;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public java.util.List<Remainder> getRemainderList() {
        return remainderList;
    }

    public void setRemainderList(java.util.List<Remainder> remainderList) {
        this.remainderList = remainderList;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
