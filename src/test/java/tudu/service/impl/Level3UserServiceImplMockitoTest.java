package tudu.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import tudu.domain.TodoList;
import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

import javax.persistence.EntityManager;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Level3UserServiceImplMockitoTest {
    User user = new User();
    TodoList todoList = new TodoList();

    @Mock
    EntityManager entityManager;
    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        user.setLogin("test_user");
        user.setFirstName("First name");
        user.setLastName("Last name");
        todoList.setName("Welcome!");
    }

    /*
    Vérifier que la liste todo a bien pour name Welcome!
    Méthode :  createNewTodoList
    */
    @Test
    public void list_name_should_correspond_list_name_rsult() {

        TodoList todoListResult = userService.createNewTodoList(user);
        assertEquals(todoList.getName(), todoListResult.getName());
    }
    /*
    Vérifier que la liste todo a bien pour name Welcome! - autre méthode
    Méthode :  createNewTodoList
    */

    @Test
    public void list_name_should_correspond_list_name_rsult_autre_methode() {

        TodoList todoListResult = userService.createNewTodoList(user);
        assertThat(todoList.getName()).isEqualTo(todoListResult.getName());
    }

}
