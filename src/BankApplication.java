import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BankApplication {
    private Map<String, String> accountDatabase;
    private String currentUser;

    public BankApplication() {
        accountDatabase = new HashMap<>();
    }

    public static void main(String[] args) {
        BankApplication bankApp = new BankApplication();
        bankApp.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Witaj w aplikacji bankowej!");
            System.out.println("1. Zaloguj się");
            System.out.println("2. Zarejestruj się");
            System.out.println("0. Wyjdź");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 0:
                    System.out.println("Dziękujemy za skorzystanie z aplikacji bankowej. Do widzenia!");
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Podaj numer ID klienta (9 cyfr):");
        String customerId = scanner.nextLine();
        System.out.println("Podaj PIN (4 cyfry):");
        String pin = scanner.nextLine();

        if (validateLogin(customerId, pin)) {
            currentUser = customerId;
            showMainMenu(scanner);
        } else {
            System.out.println("Nieprawidłowy numer ID klienta lub PIN.");
        }
    }

    private boolean validateLogin(String customerId, String pin) {
        String storedPin = accountDatabase.get(customerId);
        return storedPin != null && storedPin.equals(pin);
    }

    private void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("Witaj, " + currentUser + "!");
            System.out.println("1. Sprawdź saldo");
            System.out.println("2. Wypłać środki");
            System.out.println("3. Wpłać środki");
            System.out.println("4. Zmień PIN");
            System.out.println("0. Wyloguj się");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    changePin(scanner);
                    return;
                case 0:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private void checkBalance() {
        System.out.println("Saldo na koncie: " + getBalance(currentUser) + " zł");
    }

    private double getBalance(String customerId) {
        return 1000.0;
    }

    private void withdraw(Scanner scanner) {
        System.out.println("Podaj kwotę do wypłaty:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline character

        double currentBalance = getBalance(currentUser);
        if (amount > currentBalance) {
            System.out.println("Niewystarczające środki na koncie.");
        } else {
            currentBalance -= amount;
            updateBalance(currentUser, currentBalance);
            System.out.println("Wypłacono " + amount + " zł. Pozostałe saldo: " + currentBalance + " zł");
        }
    }

    private void deposit(Scanner scanner) {
        System.out.println("Podaj kwotę do wpłaty:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline character

        double currentBalance = getBalance(currentUser);
        currentBalance += amount;
        updateBalance(currentUser, currentBalance);
        System.out.println("Wpłacono " + amount + " zł. Aktualne saldo: " + currentBalance + " zł");
    }

    private void changePin(Scanner scanner) {
        System.out.println("Podaj obecny PIN:");
        String currentPin = scanner.nextLine();

        if (validateLogin(currentUser, currentPin)) {
            String newPin = generateRandomPin();
            updatePin(currentUser, newPin);
            System.out.println("Oto twój nowy PIN: " + newPin + ".");
            System.out.println("Dane zostały zaktualizowane w bazie danych. Zaloguj się ponownie.");
            currentUser = null;
        } else {
            System.out.println("Nieprawidłowy PIN.");
        }
    }

    private String generateRandomPin() {
        Random random = new Random();
        int pin = random.nextInt(9000) + 1000;
        return String.valueOf(pin);
    }

    private void register(Scanner scanner) {
        System.out.println("Podaj numer ID klienta (9 cyfr):");
        String customerId = scanner.nextLine();
        System.out.println("Podaj PIN (4 cyfry):");
        String pin = scanner.nextLine();

        if (customerId.length() == 9 && pin.length() == 4) {
            accountDatabase.put(customerId, pin);
            System.out.println("Konto zostało zarejestrowane. Możesz się teraz zalogować.");
        } else {
            System.out.println("Nieprawidłowy numer ID klienta lub PIN.");
        }
    }

    private void updateBalance(String customerId, double balance) {

    }

    private void updatePin(String customerId, String newPin) {

        accountDatabase.put(customerId, newPin);
    }
}
