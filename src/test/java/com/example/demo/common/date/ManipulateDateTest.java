package com.example.demo.common.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ManipulateDateTest {

    @Test
	public void testGetMonthWithinHalfYearAsStringYYYYMM() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

        // 期待値
        List<String> expected = new ArrayList<>();
        expected.add("2022/08");
        expected.add("2022/09");
        expected.add("2022/10");
        expected.add("2022/11");
        expected.add("2022/12");
        expected.add("2023/01");

        List<String> actual = ManipulateDate.getMonthWithinHalfYearAsStringYYYYMM(Calendar.getInstance());

        System.out.println(actual.toString());
        assertEquals(actual,expected);
	}
}
