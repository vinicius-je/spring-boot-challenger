package br.com.viniciusestevam.spring.boot.challenger;

import br.com.viniciusestevam.spring.boot.challenger.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCreateTodoSuccess(){
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		webTestClient
				.post()
				.uri("todos")
				.bodyValue(todo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].nome").isEqualTo(todo.getNome())
				.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());
	}
	@Test
	void testCreateTodoFailure (){
		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(
						new Todo("", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void testGetTodos(){
		webTestClient
				.get()
				.uri("/todos")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray();
	}

	@Test
	void testUpdateTodoSuccess(){
		var updateTodo = new Todo(14L, "todo 2", "desc todo 2", true, 1);
		webTestClient
				.put()
				.uri("/todos")
				.bodyValue(updateTodo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$[0].id").isEqualTo(updateTodo.getId())
				.jsonPath("$[0].nome").isEqualTo(updateTodo.getNome())
				.jsonPath("$[0].descricao").isEqualTo(updateTodo.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(updateTodo.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(updateTodo.getPrioridade());
	}

	@Test
	void testUpdateTodoFailure(){
		var updateTodo = new Todo(0L,"", "", true, 1);
		webTestClient
				.put()
				.uri("/todos")
				.bodyValue(new Todo(0L,"", "", true, 1))
				.exchange()
				.expectStatus().is5xxServerError();
	}

	@Test
	public void testDeleteTodoSuccess() {
		webTestClient
				.delete()
				.uri("/todos/" + 14)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray();
	}

	@Test
	public void testDeleteTodoFailure() {
		webTestClient
				.delete()
				.uri("/todos/" + 100)
				.exchange()
				.expectStatus().isBadRequest();
	}




}
