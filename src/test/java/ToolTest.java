import model.User;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.slf4j.LoggerFactory.getLogger;

public class ToolTest {

    public Tool tool = new Tool();
    //--- для консолі
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream(); //дані, які підсталяємо замість введення в консоль
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog(); //результатні дані, які виведені в консоль

    //--- для виведення часу виковання тесту
    private static final Logger logger = getLogger(ToolTest.class);
    private static StringBuilder results = new StringBuilder();


    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result + " ms");
            logger.info(result + " ms" + "\n------------------------------------");
        }
    };

    @AfterClass
    public static void printResult() {
        logger.info("\n------------------------------------" +
                "\nTest                        Duration" +
                "\n------------------------------------" +
                results +
                "\n------------------------------------");
    }

    // Використання ExpectedException замість @Test(expected = ArithmeticException.class)
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addition() throws Exception {
        int addition = tool.addition(6, 3);
        assertEquals("test_tool", 2, addition);
    }

    @Test(expected = ArithmeticException.class)
    public void additionEx() throws Exception {
        int addition = tool.addition(10, 0);
    }

    @Test
    public void printEx() throws Exception {
        thrown.expectMessage("message");
        thrown.expect(NullPointerException.class);
        tool.printEx();
    }

    @Test
    public void maxInt() throws Exception {

        systemInMock.provideText("2\n6\n1\n3\nstop");
        tool.maxInt();
        assertEquals("{2, 6, 1, 3}, max = 6", "Найбільше введене число 6", log.getLog().trim());
        log.clear();

        systemInMock.provideText("-2\n-6\n-1\n-3\nstop");
        tool.maxInt();
        assertEquals("{-2, -6, -1, -3}, max = -1", "Найбільше введене число -1", log.getLog().trim());
        log.clear();

        systemInMock.provideText("2\n6\n-1\n-3\nstop");
        tool.maxInt();
        assertEquals("{2, 6, -1, -3}, max = 6", "Найбільше введене число 6", log.getLog().trim());
        log.clear();
    }

    @Test
    public void addAndSortUser() throws Exception {
        LocalDate date = LocalDate.of(2017, 07, 11);
        User u1 = new User("ANDRIY", date);
        User u2 = new User("ROMAN", date);
        User u3 = new User("YANA", date);

        List<User> actual = Arrays.asList(u1, u2, u3);
        List<User> expected = tool.addAndSortUser(date, "YANA", "ANDRIY", "ROMAN");

        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected); //порівнює два списки крім поля "id" в елементі User
    }

}