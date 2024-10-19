package ru.netologia;

import com.github.javafaker.Faker;

import java.util.Locale;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("ru"));

        // Генерируем фейковые данные
        String name = faker.name().firstName() + " " + faker.name().lastName();
        String email = faker.internet().emailAddress();
        String phoneFormat = "+7 ### ### ## ##";
        String phone = faker.numerify(phoneFormat);

        System.out.println(name);
        System.out.println(email);
        System.out.println(phone);

        InputFormData data = new InputFormData();
        data.setCity(faker.address().city());
        System.out.println(data.getCity());
    }
}