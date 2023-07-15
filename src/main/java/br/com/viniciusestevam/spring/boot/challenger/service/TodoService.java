package br.com.viniciusestevam.spring.boot.challenger.service;

import br.com.viniciusestevam.spring.boot.challenger.entity.Todo;
import br.com.viniciusestevam.spring.boot.challenger.exception.BadRequestException;
import br.com.viniciusestevam.spring.boot.challenger.repository.TodoRepository;
import br.com.viniciusestevam.spring.boot.challenger.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.prefs.BackingStoreException;

@Service
public class TodoService {
     
    private TodoRepository todoRepository;
    
    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }
    
    public List<Todo> create(Todo todo){
        todoRepository.save(todo);
        return list();
    }
    
    public List<Todo> list(){
        Sort sort = Sort.by("prioridade").descending().and(
                Sort.by("nome").ascending()
        );
        return todoRepository.findAll(sort);
    }
    
    public List<Todo> update(Todo todo){
        todoRepository.findById(todo.getId()).ifPresentOrElse((existingTodo) -> todoRepository.save(todo) , () -> {
            throw new BadRequestException("Todo de ID: %d não existe".formatted(todo.getId()));
        });
        return list();
    }
    
    public List<Todo> delete(Long id){
        todoRepository.findById(id).ifPresentOrElse((todo) -> todoRepository.delete(todo), () -> {
            throw new BadRequestException("Todo de ID: %d não existe".formatted(id));
        });
        return list();
    }
}
