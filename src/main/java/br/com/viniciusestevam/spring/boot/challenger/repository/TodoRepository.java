package br.com.viniciusestevam.spring.boot.challenger.repository;

import br.com.viniciusestevam.spring.boot.challenger.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
