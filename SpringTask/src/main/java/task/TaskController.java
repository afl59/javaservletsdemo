package task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@RestController
public class TaskController {
	
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	
	private TaskRepository repository;
	
	@Autowired
	public void setProductRepository(TaskRepository repository) {
	    this.repository = repository;
	}	
	
	@RequestMapping("/task/POST")
    public void addTask(
    	@RequestParam(value="task-name") String taskName,
    	@RequestParam(value="first-name") String firstName,
    	@RequestParam(value="last-name") String lastName) {
		
		// Insert new Java object into repository
		repository.save(new Task(taskName, firstName, lastName));
	}
	
	@RequestMapping("/task/GET")
	public Iterable <Task> fetchTasks() {
		// Retrieve all Task objects
		return repository.findAll();
	}
	
	@RequestMapping("/task/DEL")
	public void delTask(@RequestParam(value="id") Long id) {
		// Delete matching Task with id
		repository.delete(id);
	}
}