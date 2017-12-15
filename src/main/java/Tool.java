import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tool {

    public int addition(int a, int b) {
        return a / b;
    }

    public void maxInt() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = 0;
        int maximum = Integer.MIN_VALUE;
        String stop = "";

        while (!stop.toLowerCase().equals("stop")) {
            try {
                stop = reader.readLine();
                n = Integer.parseInt(stop);
                if (n > maximum) maximum = n;
            } catch (NumberFormatException nfe) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Найбільше введене число " + maximum);
    }

    public List<User> addAndSortUser(LocalDate dateTime, String... name) {

        List<User> users = Arrays.asList(name).stream()
                .sorted()
                .map(n -> new User(n, LocalDate.of(2017, 07, 11)))
                .collect(Collectors.toList());

        return users;
    }

    public void printEx() {
        throw new NullPointerException("message");
    }

    public static void main(String[] args) {
        Tool t = new Tool();
        LocalDate date = LocalDate.of(2017, 07, 11);
        User u1 = new User("ANDRIY", date);
        User u2 = new User("ROMAN", date);
        User u3 = new User("YANA", date);

        List<User> users = t.addAndSortUser(date, "ROMAN", "YANA", "ANDRIY");

        for (User u : users) {
            System.out.println(u);
        }

    }

}
