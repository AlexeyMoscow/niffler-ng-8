package guru.qa.niffler.utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class RandomDataUtils {

    public static final Faker faker = new Faker();

    public static final Random rand = new Random();


    public static String randomUserName() {
        return faker.name().username() + "_" + rand.nextInt(1000);
    }

    public static String randomName() {
        return faker.name().name() + "_" + rand.nextInt(1000);
    }

    public static String randomSurname() {
        return faker.name().lastName() + "_" + rand.nextInt(1000);
    }

    public static String randomPassword() {
        return faker.internet().password(6,10);
    }

    public static String randomCategoryName() {
        return "Category_" + rand.nextInt(10000);
    }

    public static String randomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }
}
