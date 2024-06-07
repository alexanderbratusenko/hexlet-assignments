package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TasksController(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    @GetMapping
    List<TaskDTO> getTasks() {
        return taskRepository.findAll().stream().map(taskMapper::map).toList();
    }

    @GetMapping("/{id}")
    TaskDTO getTask(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return taskMapper.map(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        Task task = taskMapper.map(taskCreateDTO);
        Task saved = taskRepository.save(task);
        return taskMapper.map(saved);
    }

    @PutMapping("/{id}")
    TaskDTO updateTask(@RequestBody TaskUpdateDTO taskUpdateDTO, @PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        User user = userRepository.findById(taskUpdateDTO.getAssigneeId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        taskMapper.update(taskUpdateDTO, task);
        task.setAssignee(user);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
