package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    @Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MARCH, 22);  // Not week 26
        Date date = calendar.getTime();
        Week week = new Week(date);
        DiscountCalculator discountCalc = new DiscountCalculator(week);

        // Act
        boolean result = discountCalc.isTheSpecialWeek();

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsTheSpecialWeekWhenTrue() throws Exception {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 23);  // June 23, 2025 → week 26
        Date date = calendar.getTime();
        Week week = new Week(date);
        DiscountCalculator discountCalc = new DiscountCalculator(week);

        // Act
        boolean result = discountCalc.isTheSpecialWeek();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetDiscountPercentageEvenWeek() throws Exception {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 24);
        Week week = new Week(calendar.getTime());
        DiscountCalculator discountCalc = new DiscountCalculator(week);

        // Act
        int discount = discountCalc.getDiscountPercentage();

        // Assert
        assertEquals(7, discount);  // Even → 7%
    }

    @Test
    public void testGetDiscountPercentageOddWeek() throws Exception {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 17); // June 17, 2025 falls in an odd week (week 25)
        Week week = new Week(calendar.getTime());
        DiscountCalculator discountCalc = new DiscountCalculator(week);

        // Act
        int discount = discountCalc.getDiscountPercentage();

        // Assert
        assertEquals(5, discount);  // Odd → 5%
    }
}
