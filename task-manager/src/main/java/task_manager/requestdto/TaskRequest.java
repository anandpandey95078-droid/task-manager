package task_manager.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    private String title;
    private String description;
    private String status;
}