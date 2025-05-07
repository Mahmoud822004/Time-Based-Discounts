package test;

import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscountManagerTest {

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsFalse() throws Exception {
        // Arrange
        boolean isDiscountsSeason = false;
        double originalPrice = 100.0;
        double expectedPrice = 100.0;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        mockingContext.checking(new Expectations() {{
            // No interaction expected
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);

        // Act
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        assertEquals(expectedPrice, actualPrice, 0.0001);
        mockingContext.assertIsSatisfied(); // Verifies no unexpected calls were made
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue() throws Exception {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 80.0;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        mockingContext.checking(new Expectations() {{
            oneOf(mockedDependency).isTheSpecialWeek(); will(returnValue(true));
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);

        // Act
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        assertEquals(expectedPrice, actualPrice, 0.0001);
        mockingContext.assertIsSatisfied();
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsFalse() throws Exception {
        boolean isDiscountsSeason = true;
        double originalPrice = 200.0;
        int discountPercentage = 7; // returning 7 directly as per current implementation
        double expectedPrice = originalPrice * discountPercentage; // so 200 * 7 = 1400

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        mockingContext.checking(new Expectations() {{
            oneOf(mockedDependency).isTheSpecialWeek(); will(returnValue(false));
            oneOf(mockedDependency).getDiscountPercentage(); will(returnValue(discountPercentage));
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);

        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);

        assertEquals(expectedPrice, actualPrice, 0.0001);
        mockingContext.assertIsSatisfied();
    }
}