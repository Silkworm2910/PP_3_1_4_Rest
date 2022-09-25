package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudApplication.class, args);
	}

}
/*

	У метода addAdmin() класса DBInitializer не нужна транзакция, она должна быть в сервисном методе saveUser

	Чтобы не проверять существование пользователя и роли в базе достаточно в пропертях прописать create-drop

	При редактировании пользователя лучше использовать @PutMapping или @PatchMapping.
	Для удаления есть - @DeleteMapping. Нужно выучить и хорошо знать, что и для чего

	В модели необходимо соблюдать порядок элементов: поля - геттеры / сеттеры - equals и hashCode

	Зачем геттеры и сеттеры в сервисах?

	Транзакции только у методов, меняющих состояние БД. Перепроверь RoleServiceImpl

	Код js можно было сделать скриптом прямо в файлах html <script>две фукнции -
	удаление и обновление</script>, причем можно было не использовать циклы js

*/


