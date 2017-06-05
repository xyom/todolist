package kr.or.connect.todo.api;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	private final TodoService service;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	@Autowired
	public TodoController(TodoService service)
	{
		this.service = service;
	}
	
	private String convertToJsonP(Object o,String jsonpCallback){
        String outputmessage=null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            outputmessage=mapper.writeValueAsString(o);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(outputmessage!=null){
            outputmessage=jsonpCallback + "(" + outputmessage + ")";
        }
        return outputmessage;   
    }
	
	@GetMapping
	Object readList(@RequestParam("callback") String jsonpCallback)
	{
		return convertToJsonP(service.findAll(),jsonpCallback);
	}
	
	@GetMapping("/{completed}")
	Object read(@PathVariable Integer completed,@RequestParam("callback") String jsonpCallback)
	{
		return convertToJsonP(service.findByCompleted(completed),jsonpCallback);
	}
	
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Integer create(@RequestBody Todo todo) {
		Integer insertNumber = service.insert(todo);
		return insertNumber;
	}
	
	@CrossOrigin
	@PutMapping("/{id}/{completed}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @PathVariable Integer completed) {
		service.update(completed,id);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		service.delete(id);
	}
	
	@CrossOrigin
	@DeleteMapping("/completed/{completed}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteByCompleted(@PathVariable Integer completed) {
		service.deleteByCompleted(completed);
	}
	
	
}
