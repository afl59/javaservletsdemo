package task;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findByid(@Param("id") long id);
	List<Task> findBytaskName(@Param("taskName") String lastName);
	List<Task> findByfirstName(@Param("firstName") String firstName);
	List<Task> findBylastName(@Param("lastName") String lastName);
}