package ru.netology;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;


@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Form {
        public static FormInfo generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new FormInfo(faker.address().cityName(),
                    faker.name().lastName() + " " + faker.name().firstName(),
                    faker.numerify("+79#########"));
        }

    }

}




