package com.example.cypresstutorialbackend;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoRepository todoRepository;

    private Todo todo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Testaufgabe");
        todo.setPriority("high");
        todo.setCompleted(false);
    }

    @Test
    void testGetAllTodos() {
        // Mock-Daten
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);

        when(todoRepository.findAll()).thenReturn(todos);

        // Aufruf der Methode
        List<Todo> result = todoController.getAllTodos();

        // Überprüfung
        assertEquals(1, result.size());
        assertEquals("Testaufgabe", result.get(0).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testCreateTodo() {
        // Mock-Daten
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Aufruf der Methode
        Todo newTodo = new Todo();
        newTodo.setTitle("Neue Aufgabe");
        newTodo.setPriority("medium");
        newTodo.setCompleted(false);

        Todo result = todoController.createTodo(newTodo);

        // Überprüfung
        assertNotNull(result);
        assertEquals("Testaufgabe", result.getTitle());
        verify(todoRepository, times(1)).save(newTodo);
    }

    @Test
    void testUpdateTodo() {
        // Mock-Daten
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Aufruf der Methode
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Aktualisierte Aufgabe");
        updatedTodo.setPriority("low");
        updatedTodo.setCompleted(true);

        Todo result = todoController.updateTodo(1L, updatedTodo).getBody();

        // Überprüfung
        assertNotNull(result);
        assertEquals("Aktualisierte Aufgabe", result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void testDeleteTodo() {
        // Mock-Daten
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);

        // Aufruf der Methode
        todoController.deleteTodo(1L);

        // Überprüfung
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).delete(todo);
    }
}
