-- Create TaskList table
CREATE TABLE task_list (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Task table
CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    task_list_id BIGINT,
    FOREIGN KEY (task_list_id) REFERENCES task_list(id)
);
