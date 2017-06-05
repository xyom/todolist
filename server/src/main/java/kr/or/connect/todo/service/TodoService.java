package kr.or.connect.todo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	
	private TodoDao dao;
	
	
	public TodoService(TodoDao dao)
	{
		this.dao = dao;
	}
	
	public Collection<Todo> findAll()
	{
		return dao.selectAll();
	}
	
	public Collection<Todo> findByCompleted(Integer completed)
	{
		return dao.selectByCompleted(completed);
	}
	
	public Integer insert(Todo todo)
	{
		Integer id = dao.insert(todo);
		return id;
	}
	
	public boolean update(Integer completed,Integer id) {
		int affected = dao.update(completed,id);
		return affected == 1;
	}
	
	public boolean delete(Integer id) {
		int affected = dao.deleteById(id);
		return affected == 1;
	}
	
	public boolean deleteByCompleted(Integer completed)
	{
		int affected = dao.deleteByCompleted(completed);
		return affected == 1;
	}

}
