package task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import task_manager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}