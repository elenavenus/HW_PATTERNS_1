import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(shift);
        return localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName().replace("Ё", "Е").replace("ё", "е");
    }

    public static String generateSurname(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().lastName().replace("Ё", "Е").replace("ё", "е");
    }

    public static String generatePhone(String locale) {
        String phoneFormat = "+7 ### ### ## ##";
        Faker faker = new Faker(new Locale(locale));
        return faker.numerify(phoneFormat);
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            String city = generateCity(locale);
            String name = generateName(locale);
            String surname = generateSurname(locale);
            String phone = generatePhone(locale);
            return new UserInfo(city, name, surname, phone);
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String surname;
        String phone;
    }
}