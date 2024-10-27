package com.example.taskboard.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.taskboard.exception.TaskListNotFoundException;
import com.example.taskboard.exception.TaskNotFoundException;
import com.example.taskboard.model.Task;
import com.example.taskboard.model.TaskList;
import com.example.taskboard.repository.TaskListRepository;
import com.example.taskboard.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.logging.Logger;

/**
 * Unit tests for the TaskService class.
 */
public class TaskServiceTest {

    private static final Logger logger = Logger.getLogger(TaskServiceTest.class.getName());

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for retrieving all task lists.
     */
    @Test
    void testGetAllTaskLists() {
        logger.info("Running testGetAllTaskLists");

        // Arrange
        TaskList taskList1 = new TaskList();
        TaskList taskList2 = new TaskList();
        when(taskListRepository.findAll()).thenReturn(Arrays.asList(taskList1, taskList2));

        // Act
        List<TaskList> taskLists = taskService.getAllTaskLists();

        // Assert
        assertEquals(2, taskLists.size());
        verify(taskListRepository, times(1)).findAll();
    }

    /**
     * Test for creating a new task list.
     */
    @Test
    void testCreateTaskList() {
        logger.info("Running testCreateTaskList");

        // Arrange
        TaskList taskList = new TaskList();
        taskList.setName("Test List");
        when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);

        // Act
        TaskList createdTaskList = taskService.createTaskList("Test List");

        // Assert
        assertEquals("Test List", createdTaskList.getName());
        verify(taskListRepository, times(1)).save(any(TaskList.class));
    }

    /**
     * Test for adding a new task to an existing task list.
     */
    @Test
    void testAddTaskToList() {
        logger.info("Running testAddTaskToList");

        // Arrange
        Long listId = 1L;
        TaskList taskList = new TaskList();
        when(taskListRepository.findById(listId)).thenReturn(Optional.of(taskList));

        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task createdTask = taskService.addTaskToList(listId, "Test Task", "Test Description");

        // Assert
        assertEquals("Test Task", createdTask.getName());
        assertEquals("Test Description", createdTask.getDescription());
        verify(taskListRepository, times(1)).findById(listId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    /**
     * Test for handling the case where the task list is not found when adding a task.
     */
    @Test
    void testAddTaskToListNotFound() {
        logger.info("Running testAddTaskToListNotFound");

        // Arrange
        Long listId = 1L;
        when(taskListRepository.findById(listId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(TaskListNotFoundException.class, () -> {
            taskService.addTaskToList(listId, "Test Task", "Test Description");
        });

        String expectedMessage = "Task list with ID " + listId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for updating an existing task.
     */
    @Test
    void testUpdateTask() {
        logger.info("Running testUpdateTask");

        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Act
        Task result = taskService.updateTask(taskId, "Updated Task", "Updated Description");

        // Assert
        assertEquals("Updated Task", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    /**
     * Test for handling the case where the task is not found when updating.
     */
    @Test
    void testUpdateTaskNotFound() {
        logger.info("Running testUpdateTaskNotFound");

        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(taskId, "Updated Task", "Updated Description");
        });

        String expectedMessage = "Task with ID " + taskId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for deleting an existing task.
     */
    @Test
    void testDeleteTask() {
        logger.info("Running testDeleteTask");

        // Arrange
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    /**
     * Test for handling the case where the task is not found when deleting.
     */
    @Test
    void testDeleteTaskNotFound() {
        logger.info("Running testDeleteTaskNotFound");

        // Arrange
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(taskId);
        });

        String expectedMessage = "Task with ID " + taskId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for deleting an existing task list.
     */
    @Test
    void testDeleteTaskList() {
        logger.info("Running testDeleteTaskList");

        // Arrange
        Long listId = 1L;
        when(taskListRepository.existsById(listId)).thenReturn(true);

        // Act
        taskService.deleteTaskList(listId);

        // Assert
        verify(taskListRepository, times(1)).deleteById(listId);
    }

    /**
     * Test for handling the case where the task list is not found when deleting.
     */
    @Test
    void testDeleteTaskListNotFound() {
        logger.info("Running testDeleteTaskListNotFound");

        // Arrange
        Long listId = 1L;
        when(taskListRepository.existsById(listId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(TaskListNotFoundException.class, () -> {
            taskService.deleteTaskList(listId);
        });

        String expectedMessage = "Task list with ID " + listId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for moving a task to a different task list.
     */
    @Test
    void testMoveTaskToList() {
        logger.info("Running testMoveTaskToList");

        // Arrange
        Long taskId = 1L;
        Long newListId = 2L;
        Task task = new Task();
        TaskList newTaskList = new TaskList();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskListRepository.findById(newListId)).thenReturn(Optional.of(newTaskList));

        Task movedTask = new Task();
        movedTask.setTaskList(newTaskList);
        when(taskRepository.save(any(Task.class))).thenReturn(movedTask);

        // Act
        Task result = taskService.moveTaskToList(taskId, newListId);

        // Assert
        assertEquals(newTaskList, result.getTaskList());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskListRepository, times(1)).findById(newListId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    /**
     * Test for handling the case where the task is not found when moving.
     */
    @Test
    void testMoveTaskToListTaskNotFound() {
        logger.info("Running testMoveTaskToListTaskNotFound");

        // Arrange
        Long taskId = 1L;
        Long newListId = 2L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.moveTaskToList(taskId, newListId);
        });

        String expectedMessage = "Task with ID " + taskId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


/**
 * Test for handling the case where the task
 * Test for handling the case where the task list is not found when moving.
 */
@Test
void testMoveTaskToListTaskListNotFound() {
    logger.info("Running testMoveTaskToListTaskListNotFound");

    // Arrange
    Long taskId = 1L;
    Long newListId = 2L;
    Task task = new Task();
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
    when(taskListRepository.findById(newListId)).thenReturn(Optional.empty());

    // Act & Assert
    Exception exception = assertThrows(TaskListNotFoundException.class, () -> {
        taskService.moveTaskToList(taskId, newListId);
    });

    String expectedMessage = "Task list with ID " + newListId + " not found";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
}

    /**
     * Test for retrieving a specific task by ID.
     */
    @Test
    void testGetTaskById() {
        logger.info("Running testGetTaskById");

        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        Task result = taskService.getTaskById(taskId);

        // Assert
        assertEquals(taskId, result.getId());
        verify(taskRepository, times(1)).findById(taskId);
    }

    /**
     * Test for handling the case where the task is not found when retrieving by ID.
     */
    @Test
    void testGetTaskByIdNotFound() {
        logger.info("Running testGetTaskByIdNotFound");

        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(taskId);
        });

        String expectedMessage = "Task with ID " + taskId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
