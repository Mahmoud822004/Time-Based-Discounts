package test;

import org.jfree.data.time.Year;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class YearTest {
    Year year;

    private void arrange() {
        year = new Year();
    }
    @Test
    public void testYearDefaultCtor() {
        arrange();
        assertEquals(2025, year.getYear());
    }

    @Test
    public void testYearSingleYearCtor(){
        year = new Year(2024);
        assertEquals(2024,year.getYear());
    }

    @Test
    public void  testYearParticularTimeCtor(){
        Date time = new Date(124, 10, 30);
        year = new Year(time);
        assertEquals(2024, year.getYear());
    }

    @Test
    public void testYearWithTimeZoneCtor(){
        Date time = new Date(99, 5, 15);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        year = new Year(time, cal);
        assertEquals(1999, year.getYear());
    }

    @Test
    public void testGetYear(){
        year = new Year(2024);
        assertEquals(2024,year.getYear());
    }

    @Test
    public void testPreviousYearAfter1900(){
        year = new Year(2018);
        Year prev = (Year) year.previous();
        assertEquals(2017, prev.getYear());
    }

    @Test
    public void testPreviousYearBefore1900(){
        year = new Year(1900);
        Year prev = (Year) year.previous();
        assertNull(prev);
    }

    @Test
    public void testNext(){
        year = new Year(2024);
        Year next = (Year) year.next();
        assertEquals(2025, next.getYear());
    }

    @Test
    public void testNextLimitReached(){
        year = new Year(9999);
        Year next = (Year) year.next();
        assertNull(next);
    }

    @Test
    public void testGetSerialIndex(){
        year = new Year(2024);
        assertEquals(2024L,year.getSerialIndex());
    }

    @Test
    public void testGetFirstMillisecond(){
        year = new Year(2024);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long millis = year.getFirstMillisecond(calendar);

        calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(calendar.getTimeInMillis(), millis);
    }

    @Test
    public void testGetLastMillisecond(){
        year = new Year(2022);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long millis = year.getLastMillisecond(calendar);

        calendar.set(2022, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        assertEquals(calendar.getTimeInMillis(), millis);
    }

    @Test
    public void testEqual(){
        year = new Year(2016);
        Year year2 = new Year(2016);
        assertTrue(year.equals(year2));
    }

    @Test
    public void testNotEqual(){
        year = new Year(2024);
        Year year2 = new Year(2022);
        assertFalse(year.equals(year2));
    }

    @Test
    public void testHashCodeConsistency() {
        year = new Year(2012);
        Year y2 = new Year(2012);
        assertEquals(year.hashCode(), y2.hashCode());
    }

    @Test
    public void testCompareToBefore(){
        year = new Year(2024);
        Year year2 = new Year(2023);
        assertTrue(year.compareTo(year2)>0);
    }

    @Test
    public void testCompareToEqual(){
        year = new Year(2014);
        Year year2 = new Year(2014);
        assertTrue(year.compareTo(year2)==0);
    }

    @Test
    public void testCompareToAfter(){
        year = new Year(2017);
        Year year2 = new Year(2018);
        assertTrue(year.compareTo(year2)<0);
    }

    @Test
    public void testToString(){
        year = new Year(2022);
        assertEquals("2022",year.toString());
    }

    @Test
    public void testParseYear(){
        Year parsedYear = Year.parseYear("2022");
        assertEquals(new Year(2022),parsedYear);
    }

    @Test
    public void testParseYearInvalidString(){
        Year parsedYear = Year.parseYear("ABCDEFG");
        assertNull(parsedYear);
    }
}