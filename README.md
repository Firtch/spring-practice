# Simple practice project (Spring Framework[NOT BOOT], Spring Security, Thymeleaf, Embedded H2DB)

It's just a simple Spring application. This application Was made to practice in work with spring framework.
I was making a practice in ControllerAdvice, validating form data, custom exception handlers, json converters, DAO objects, JdbcTemplate etc.

## Without ORM

The reason to not use ORM was to make practice in work with data manualy. Spring Boot & Hibernate make things more easy and create difficults in understending important foundamentals thinhgs.
For data manipulation is used Dao and JdbcTemplate

## It's ready to deploy
Just deploy application and it will work. There are embedded in memmory H2 database.
When application start it creates 2 tables "profile" and "activation_token"
Еable "profile" stores users and "activation_token" stores token for account activation.
By default at startup there are 6 users b "profile" table and 5 of them are active.
Important thing when creating new user, link for confirmation sended to terminal (Email simulation), just copy that link to your browser and user will be activated.


