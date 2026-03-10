package ogr.exapmle.asseptensetest;

import org.example.bank2.dto.UserRequest;

public class UserTemplates {

    public static UserRequest getUserTemplate(String template) {
        return switch (template) {
            case "по-умолчанию" -> defaultUser();
            case "обычный пользователь" -> standardUser();
            case "администратор" -> administrator();
            case "не администратор" -> notAdmin();
            default -> throw new IllegalArgumentException("Для '" + template + "' нет шаблона");
        };
    }

    public static UserRequest defaultUser() {
        return new UserRequest(
                "user_default",
                "12",
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
                "12",
                "Админ",
                "Администраторов",
                "Админович",
                "+79009999999",
                true
        );
    }

    public static UserRequest notAdmin() {
        return new UserRequest(
                "noAdmin@ru",
                "12",
                "Не админ",
                "Не администраторов",
                "Не админович",
                "+7900000000",
                false
        );
    }

    public static UserRequest standardUser() {
        return new UserRequest(
                "standart@ru",
                "12",
                "Обычный",
                "Обычный",
                "Обычный",
                "+7900000000",
                false
        );
    }
}
