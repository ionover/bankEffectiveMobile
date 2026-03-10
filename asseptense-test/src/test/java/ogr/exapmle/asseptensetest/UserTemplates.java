package ogr.exapmle.asseptensetest;

import org.example.bank2.dto.UserRequest;

public class UserTemplates {

    public static UserRequest getUserTemplate(String template) {
        return switch (template) {
            case "по-умолчанию" -> defaultUser();
            case "администратор" -> administrator();
            default -> throw new IllegalArgumentException("Для '" + template + "' нет шаблона");
        };
    }

    public static UserRequest defaultUser() {
        return new UserRequest(
                "user_default",
                "password123",
                "Иван",
                "Иванов",
                "Иванович",
                "+79001234567",
                false
        );
    }

    public static UserRequest administrator() {
        return new UserRequest(
                "admin",
                "admin123",
                "Админ",
                "Администраторов",
                "Админович",
                "+79009999999",
                true
        );
    }

    public static UserRequest userWithoutMiddleName() {
        return new UserRequest(
                "user_no_middle",
                "password123",
                "Петр",
                "Петров",
                null,
                "+79001111111",
                false
        );
    }

    public static UserRequest userWithoutPhone() {
        UserRequest user = new UserRequest();
        user.setLogin("user_no_phone");
        user.setPassword("password123");
        user.setName("Сергей");
        user.setSurname("Сергеев");
        user.setMiddleName("Сергеевич");
        user.setIsAdmin(false);
        return user;
    }

    public static UserRequest customUser(String login, String password, String name,
                                         String surname, Boolean isAdmin) {
        return new UserRequest(
                login,
                password,
                name,
                surname,
                null,
                null,
                isAdmin
        );
    }
}
