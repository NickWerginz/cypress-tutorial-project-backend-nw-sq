package com.example.cypresstutorialbackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000") // Erlaubt Anfragen vom Frontend
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // Alle Todos abrufen
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // Ein neues Todo erstellen
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // Ein Todo aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo nicht gefunden mit ID: " + id));

        todo.setTitle(todoDetails.getTitle());
        todo.setPriority(todoDetails.getPriority());
        todo.setCompleted(todoDetails.isCompleted());
        todo.setCategory(todoDetails.getCategory());
        todo.setDueDate(todoDetails.getDueDate());

        Todo updatedTodo = todoRepository.save(todo);
        return ResponseEntity.ok(updatedTodo);
    }

    // Ein Todo löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo nicht gefunden mit ID: " + id));

        todoRepository.delete(todo);
        return ResponseEntity.noContent().build();
    }
}
