package kr.or.connect.todo.persistence;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.domain.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoDaoTest {
	
	@Autowired
	private TodoDao dao;
	
	@Test
	public void Insert()
	{
		Todo todo = new Todo("밥먹기");
		dao.insert(todo);
	}
	
	@Test
	public void shouldSelectAll()
	{
		List<Todo> allTodo = dao.selectAll();
		
	}
	
	@Test
	public void delete()
	{
		int deleteid = 5;
		dao.deleteById(deleteid);
	}
	
	@Test
	public void update()
	{
		int id = 5,completed = 1;
		dao.update(completed, id);
	}
	
	@Test
	
	public void deleteByCompleted()
	{
		int completed = 1;
		dao.deleteByCompleted(completed);
	}
}
