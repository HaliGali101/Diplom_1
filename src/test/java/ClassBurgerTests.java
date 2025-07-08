import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClassBurgerTests {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Mock
    private Ingredient mockIngredient3;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBuns_ShouldSetBunCorrectly() {
        // Добавляем mock булочки
        burger.setBuns(mockBun);

        // Проверяем, что у бургера есть mock булочки
        assertEquals("Бургер должна быть с булочкой", mockBun, burger.bun);
    }

    @Test
    public void addIngredient_ShouldAddIngredientToList() {
        // Добавляем ингредиент
        burger.addIngredient(mockIngredient1);

        // Проверяем, что список содержит mock ингредиента 1
        assertTrue("Список ингредиентов должен содержать добавленный ингредиент", burger.ingredients.contains(mockIngredient1));
        assertEquals("Размер списка ингредиентов должен быть 1 после добавления", 1, burger.ingredients.size());

    }

    @Test
    public void addIngredient_ShouldAddMultipleIngredientsToList() {
        // Добавляем первый ингредиент
        burger.addIngredient(mockIngredient1);

        // Добавляем второй ингредиент
        burger.addIngredient(mockIngredient2);

        // Проверяем, что оба ингредиента находятся в списке
        assertEquals("Список должен содержать 2 ингредиента после добавления двух", 2, burger.ingredients.size());

        // Проверяем порядок добавления
        assertSame("Первый ингредиент должен быть на позиции 0", mockIngredient1, burger.ingredients.get(0));
        assertSame("Второй ингредиент должен быть на позиции 1", mockIngredient2, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredient_ShouldRemoveIngredientByIndex() {
        // Добавляем первый ингредиент
        burger.addIngredient(mockIngredient1);

        // Добавляем второй ингредиент
        burger.addIngredient(mockIngredient2);

        // Удаляем первый ингредиент
        burger.removeIngredient(0);

        // Проверяем, что первый ингредиент удален
        assertFalse("Список не должен содержать первый ингредиент после удаления", burger.ingredients.contains(mockIngredient1));
        assertEquals("Список должен содержать один ингредиент после удаления", 1, burger.ingredients.size());

        // Проверяем, что остался второй ингредиент
        assertSame("Оставшийся ингредиент должен быть вторым", mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredient_ShouldMoveIngredientToNewPosition() {
        // Добавляем три ингредиента
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        // Перемещаем второй ингредиент на место первого
        burger.moveIngredient(1, 0);

        // Проверяем новый порядок ингредиентов: [2, 1, 3]
        assertSame("Первый ингредиент после перемещения должен быть 2", mockIngredient2, burger.ingredients.get(0));
        assertSame("Второй ингредиент должен быть 1", mockIngredient1, burger.ingredients.get(1));
        assertSame("Третий ингредиент должен быть 3", mockIngredient3, burger.ingredients.get(2));
    }

    @Test
    public void getPrice_ShouldCalculatePriceWithNoIngredients() {
        // Задаем цену булочки
        when(mockBun.getPrice()).thenReturn(3.0f);

        // Добавляем mock булочки
        burger.setBuns(mockBun);

        // Ожидаемая цена: цена булочки * 2 = 6.0
        float expectedPrice = 6.0f;

        // Проверяем цену булочки
        assertEquals("Цена должна быть рассчитана только с булкой", expectedPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void getPrice_ShouldCalculatePriceWithMultipleIngredients() {
        // Задаем цену булочки
        when(mockBun.getPrice()).thenReturn(2.5f);

        // Задаем цены ингредиентов
        when(mockIngredient1.getPrice()).thenReturn(1.0f);
        when(mockIngredient2.getPrice()).thenReturn(2.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        // Ожидаемая цена: цена булочки * 2 + цена ингредиентов = 2.5*2 + 1.0 + 2.0 = 8.0
        float expectedPrice = 8.0f;

        assertEquals("Цена должна быть рассчитана с учетом всех ингредиентов", expectedPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void getReceipt_ShouldGenerateCorrectReceiptWithIngredients() {
        // Задаем булку
        when(mockBun.getName()).thenReturn("Булка");
        when(mockBun.getPrice()).thenReturn(3.0f);

        // Задаем ингредиент 1
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient1.getName()).thenReturn("Котлета");
        when(mockIngredient1.getPrice()).thenReturn(2.0f);

        // Задаем ингредиент 2
        when(mockIngredient2.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient2.getName()).thenReturn("Соус");
        when(mockIngredient2.getPrice()).thenReturn(1.5f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        // Проверяем ключевые части чека
        assertTrue("Чек должен начинаться с булки", receipt.startsWith("(==== Булка ====)"));
        assertTrue("Чек должен содержать ингредиент Котлета", receipt.contains("= filling Котлета ="));
        assertTrue("Чек должен содержать ингредиент Соус", receipt.contains("= sauce Соус ="));
        assertTrue("Чек должен заканчиваться с булкой", receipt.contains("(==== Булка ====)"));

        // Проверяем наличие итоговой цены (цена булочки * 2 + котлета + соус = 3*2+2+1.5=9.5)
        assertTrue("Чек должен содержать правильную цену", receipt.contains(String.format("Price: %.1f", 9.5f)));
    }

}
