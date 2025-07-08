import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import utils.RandomFloat;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ClassBunTests {

    private static final Faker faker = new Faker();

    private final String testCase;
    private final String name;
    private final float price;

    public ClassBunTests(String testCase, String name, float price) {
        this.testCase = testCase;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Object[][] testData() {
        return new Object[][]{
                {"Обычная булочка", faker.food().ingredient(), RandomFloat.generatePositivePrice()},
                {"Наименование = null", null, RandomFloat.generatePositivePrice()},
                {"Наименование = пустая строка", "", RandomFloat.generatePositivePrice()},
                {"Цена - отрицательное значение", faker.food().ingredient(), RandomFloat.generateNegativePrice()},
                {"Цена = 0.0", faker.food().ingredient(), 0.0f},
        };
    }

    @Test
    public void testBunCases() {
        Bun bun = new Bun(name, price);

        // Проверяем соответствие переданных аргументов
        assertEquals(testCase, name, bun.getName());
        assertEquals(testCase, price, bun.getPrice(), 0.0001);
    }

}
