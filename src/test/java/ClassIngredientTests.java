import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Ingredient;
import praktikum.IngredientType;
import utils.RandomFloat;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ClassIngredientTests {

    private static final Faker faker = new Faker();

    private final String testCase;
    private final IngredientType type;
    private final String name;
    private final float price;

    public ClassIngredientTests(String testCase, IngredientType type, String name, float price) {
        this.testCase = testCase;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Object[][] testData() {
        return new Object[][]{
                {"Модель ингредиента: соус", IngredientType.SAUCE, faker.food().ingredient(), RandomFloat.generatePositivePrice()},
                {"Модель ингредиента: начинка", IngredientType.FILLING, faker.food().ingredient(), RandomFloat.generatePositivePrice()}
        };
    }

    @Test
    public void testBunCases() {
        Ingredient ingredient = new Ingredient(type, name, price);

        // Проверяем соответствие переданных аргументов
        assertEquals(testCase, type, ingredient.getType());
        assertEquals(testCase, name, ingredient.getName());
        assertEquals(testCase, price, ingredient.getPrice(), 0.0001);
    }
}
