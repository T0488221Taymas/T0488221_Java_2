import java.math.BigDecimal;
import java.util.*;


abstract class User {
    protected String name;

    public String getName() {
        return name;
    }
}



class Rider extends User {
    private int passengerType;
    private int paymentMethod;
    private List<Journey> journeys = new ArrayList<>();
    private int nextId = 1;

    public void addJourney(Journey j) {
        journeys.add(j);
    }
    public boolean deleteJourney(int id) {
        return journeys.removeIf(j -> j.getId() == id);
    }
    public List<Journey> getJourneys() {
        return journeys;
    }
    public int getNextId() {
        return nextId++;
    }
}


class Admin extends User {
    private String password = "Admin";

    public boolean login(String inputPassword) {
        return password.equals(inputPassword);
    }
    public void updateConfig() {}
}

class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Rider rider = new Rider();
    private int readInt() {
        try {
            return scanner.nextInt();
        }
        catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    private int validateZone(String message) {
        System.out.print(message);
        int value = readInt();
        if (value >= 1 && value <= 5) {
            return value;
        }
        System.out.println("Invalid zone! (1-5)");
        return -1;
    }
    private String validatePassengerType() {
        System.out.println("Passenger type:");
        System.out.println("1. Adult");
        System.out.println("2. Student");
        System.out.println("3. Child");
        System.out.println("4. Senior");
        int choice = readInt();
        switch (choice) {
            case 1: return "Adult";
            case 2: return "Student";
            case 3: return "Child";
            case 4: return "Senior";
            default:
                System.out.println("Invalid choice!");
                return null;
        }
    }
    private String validateTimeBand() {
        System.out.println("Time band:");
        System.out.println("1. Peak");
        System.out.println("2. OffPeak");
        int choice = readInt();
        if (choice == 1) return "Peak";
        if (choice == 2) return "OffPeak";
        System.out.println("Invalid choice!");
        return null;
    }
    private Integer readIntSafe() {
        try {
            return scanner.nextInt();
        }
        catch (Exception e) {
            scanner.nextLine();
            return null;
        }
    }
    private Integer validateId() {
        System.out.print("Enter ID to remove: ");
        Integer id = readIntSafe();
        if (id == null) {
            System.out.println("Invalid input! Enter a number.");
            return null;
        }
        if (id <= 0) {
            System.out.println("ID must be positive!");
            return null;
        }
        return id;
    }
    public void showMainMenu() {
        boolean run = true;
        while (run) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Rider");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showRiderMenu();
                    run = false;
                    break;
                case 2:
                    showAdminMenu();
                    run = false;
                    break;
                case 3:
                    System.out.println("Exiting");
                    run = false;
                    return;
                default:
                    System.out.println("Invalid Input!");
            }
        }
    }

    public void showRiderMenu() {
        boolean run = true;
        while (run) {
            System.out.println("\n=== RIDER MENU ===");
            System.out.println("1. Add Journey");
            System.out.println("2. List Journeys");
            System.out.println("3. Filter Journeys");
            System.out.println("4. View Daily Summary");
            System.out.println("5. View Totals by Passenger Type");
            System.out.println("6. Remove Journey");
            System.out.println("7. Reset Day");
            System.out.println("8. Back");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter date: ");
                    String date = scanner.next();
                    int from = validateZone("From zone (1-5): ");
                    int to = validateZone("To zone (1-5): ");
                    String type = validatePassengerType();
                    String time = validateTimeBand();
                    if (from == -1 || to == -1 || type == null || time == null) {
                        System.out.println("Failed to add journey. Try again.");
                        break;
                    }
                    Journey j = new Journey(rider.getNextId(), date, from, to, type, time);
                    rider.addJourney(j);
                    System.out.println("Journey added!");
                    break;
                case 2:
                    List<Journey> list = rider.getJourneys();
                    if (list.isEmpty()) {
                        System.out.println("No journeys.");
                        break;
                    }
                    System.out.println("\n=== JOURNEYS ===");
                    for (Journey journey : list) {
                        System.out.println(journey);
                    }
                    break;
                case 3:
                    System.out.println("Filter Journeys");

                    break;
                case 4:
                    System.out.println("Daily Summary");

                    break;
                case 5:
                    System.out.println("Totals by Passenger Type");

                    break;
                case 6:
                    Integer id = validateId();
                    if (id == null) {
                        break;
                    }
                    boolean removed = rider.deleteJourney(id);
                    if (removed) {
                        System.out.println("Journey removed!");
                    } else {
                        System.out.println("No journey with that ID.");
                    }


                    break;

                case 7:
                    if (rider.getJourneys().isEmpty()) {
                        System.out.println("Nothing to reset.");
                        break;
                    }
                    System.out.print("Are you sure you want to reset all journeys? (y/n): ");
                    String confirm = scanner.next();
                    if (confirm.equalsIgnoreCase("y")) {
                        rider.getJourneys().clear();
                        System.out.println(" All journeys have been reset.");
                    } else {
                        System.out.println("Reset cancelled.");
                    }
                    break;

                case 8:
                    run = false;
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void showAdminMenu() {
        Admin admin = new Admin();
        System.out.print("Enter admin password: ");
        String input = scanner.next();
        if (!admin.login(input)) {
            System.out.println("Wrong password!");
            return;
        }
        boolean run = true;
        while (run) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View Config");
            System.out.println("2. Update Config");
            System.out.println("3. Back");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Viewing config");
                    run = false;
                    break;
                case 2:
                    System.out.println("Updating config");
                    run = false;
                    break;
                case 3:
                    run = false;
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}

class Journey {
    private int id;
    private String date;
    private int fromZone;
    private int toZone;
    private String passengerType;
    private String timeBand;
    private int zoneCrossed;
    private BigDecimal baseFare;
    private BigDecimal discountedFare;
    private BigDecimal chargedFare;

    public Journey(int id, String date, int fromZone, int toZone, String passengerType, String timeBand){
        this.id = id;
        this.date = date;
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.passengerType = passengerType;
        this.timeBand = timeBand;
        this.zoneCrossed = Math.abs(toZone - fromZone) + 1;
    }
    public int getId() {return id;}
    public int getFromZone() {return fromZone;}
    public int getToZone() {return toZone;}
    public int getZonesCrossed() {return zoneCrossed;}
    public BigDecimal getChargedFare() {return chargedFare;}
    @Override
    public String toString() {
        return "ID: " + id +
                " | From: " + fromZone +
                " | To: " + toZone +
                " | Zones: " + zoneCrossed +
                " | Fare: £" + chargedFare;
    }
}


//class JourneyManager {
//    public void addJourney() {}
//    public void deleteJourney() {}
//    public void filterJourneys() {}
//}
//
//
//class FareCalculator {
//    public double calculateFare() {}
//    public double applyDiscount() {}
//    public double applyCap() {}
//}
//
//
//class ReportGenerator {
//    public String generateSummary() {}
//    public void exportCSV() {}
//    public void exportText() {}
//    public void viewDailySummary() {}
//}
//
//
//class Config {
//    private Map<String, Double> baseFares;
//    private Map<String, Double> discounts;
//    private Map<String, Double> caps;
//
//    public boolean validate() {}
//}
//
//
//
//interface FileManager {
//    void save(String data);
//    String load();
//}
//
//
//class JSONFileManager implements FileManager {
//    public void save(String data) {}
//    public String load() {}
//}
//
//
//class TextFileManager implements FileManager {
//    public void save(String data) {}
//    public String load() {}
//}
//
//
//class CSVFileManager implements FileManager {
//    public void save(String data) {}
//    public String load() {}
//}

