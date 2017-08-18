package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	// Return home HTML view
	@RequestMapping("/")
	public String welcome() {
		return "todolist";
	}
}