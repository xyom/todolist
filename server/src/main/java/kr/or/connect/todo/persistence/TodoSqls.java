package kr.or.connect.todo.persistence;

public class TodoSqls {
	
	
	static final String SELECT_ALL="select * from todo order by date desc";
	static final String SELECT_BY_COMPLETED = "select id,todo,completed,date FROM todo where completed = :completed order by date desc";
	
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	static final String DELETE_BY_COMPLETED=
			"DELETE FROM todo Where completed=:completed";
	static final String UPDATE =
			"UPDATE todo SET\n"
			+ "completed = :completed\n"
			+ "WHERE id = :id";
}
