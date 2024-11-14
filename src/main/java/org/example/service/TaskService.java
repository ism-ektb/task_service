package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private ModelMapper mapper;


    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        return taskRepository.save(task);
    }

    public Task getTask(long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void delete(long id) {
        taskRepository.deleteById(id);
    }
}
