-- Insert sample data into TaskList table
INSERT INTO task_list (name) VALUES ('Personal');
INSERT INTO task_list (name) VALUES ('Work');
INSERT INTO task_list (name) VALUES ('Shopping');

-- Insert sample data into Task table
INSERT INTO task (name, description, task_list_id) VALUES ('Buy groceries', 'Milk, Bread, Eggs', 3);
INSERT INTO task (name, description, task_list_id) VALUES ('Finish report', 'Complete the quarterly report', 2);
INSERT INTO task (name, description, task_list_id) VALUES ('Call plumber', 'Fix the kitchen sink', 1);
INSERT INTO task (name, description, task_list_id) VALUES ('Schedule meeting', 'Team meeting at 10 AM', 2);
INSERT INTO task (name, description, task_list_id) VALUES ('Buy birthday gift', 'Gift for wife', 1);
