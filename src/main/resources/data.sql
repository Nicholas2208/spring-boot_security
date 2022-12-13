INSERT INTO roles(name) VALUES ("ROLE_USER");
INSERT INTO roles(name) VALUES ("ROLE_ADMIN");

INSERT INTO users(email, first_name, last_name, password) VALUES ("nwhite@mail.ru", "Nicholas", "White", "$2a$10$eMMWx/ClHfHx8shzzcq5..cG0.brwg4shEYay1sr65rXPxJzek9La");
INSERT INTO users(email, first_name, last_name, password) VALUES("helen@mail.ru", "Helen", "Petrovsky", "$2a$10$Bowq4j4QpO06qxWy5oTwmesQ.YxHHx2TQurVNLwtQlkKj9IKujcGO");
INSERT INTO users(email, first_name, last_name, password) VALUES("arthur@gmail.com", "Arthur", "Peck", "$2a$10$XsOuaFFUMaZctmm1V8/sSOevKN3CxVdUuKi6Xf8iVJ7egbUkxEwNu");

INSERT INTO user_role(user_id, role_id) VALUES (1, 1);
INSERT INTO user_role(user_id, role_id) VALUES (1, 2);
INSERT INTO user_role(user_id, role_id) VALUES (2, 1);
INSERT INTO user_role(user_id, role_id) VALUES (3, 1);
