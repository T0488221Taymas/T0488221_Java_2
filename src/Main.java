import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDate;
import java.math.RoundingMode;

abstract class User {
    protected String name;

    public String getName() {
        return name;
    }
}



class Rider extends User {

    private CityRideDataset.PassengerType mapPassengerType(String type) {
        switch (type.toLowerCase()) {
            case "adult":
                return CityRideDataset.PassengerType.ADULT;
            case "student":
                return CityRideDataset.PassengerType.STUDENT;
            case "child":
                return CityRideDataset.PassengerType.CHILD;
            case "senior":
                return CityRideDataset.PassengerType.SENIOR_CITIZEN;
            default:
                throw new IllegalArgumentException("Invalid passenger type");
        }
    }

    private String passengerType;
    private String paymentMethod;
    private List<Journey> journeys = new ArrayList<>();
    private int nextId = 1;
    public void setName(String name) {
        this.name = name;
    }
    public void setPassengerType(String type) {
        this.passengerType = type;
    }
    public void setPaymentMethod(String method) {
        this.paymentMethod = method;
    }
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

    public void applyDailyCap() {
        Map<LocalDate, BigDecimal> totalsByDate = new HashMap<>();
        for (Journey j : journeys) {
            LocalDate date = j.getDate();
            totalsByDate.putIfAbsent(date, BigDecimal.ZERO);
            totalsByDate.put(date, totalsByDate.get(date).add(j.getChargedFare()));
        }
        for (Journey j : journeys) {
            CityRideDataset.PassengerType type = mapPassengerType(j.getPassengerType());
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(type);
            BigDecimal totalForDay = totalsByDate.get(j.getDate());
            if (totalForDay.compareTo(cap) > 0) {
                j.setChargedFare(cap);
            }
        }
    }

/// GPT
    public List<Journey> filterJourneys(String timeBand, String passengerType) {
        List<Journey> result = new ArrayList<>();
        for (Journey j : journeys) {
            boolean matchTime = (timeBand == null || j.getTimeBand().equalsIgnoreCase(timeBand));
            boolean matchPassenger = (passengerType == null || j.getPassengerType().equalsIgnoreCase(passengerType));
            if (matchTime && matchPassenger) {
                result.add(j);
            }
        }
        return result;
    }//

    public Map<LocalDate, BigDecimal> getDailyTotals() {
        Map<LocalDate, BigDecimal> totals = new HashMap<>();
        for (Journey j : journeys) {
            LocalDate date = j.getDate();
            totals.putIfAbsent(date, BigDecimal.ZERO);
            totals.put(date, totals.get(date).add(j.getChargedFare()));
        }
        return totals;
    }
    public Map<LocalDate, Integer> getDailyCounts() {
        Map<LocalDate, Integer> counts = new HashMap<>();
        for (Journey j : journeys) {
            LocalDate date = j.getDate();
            counts.putIfAbsent(date, 0);
            counts.put(date, counts.get(date) + 1);
        }
        return counts;
    }
    public Map<String, Integer> getTotalsByPassengerCount() {
        Map<String, Integer> counts = new HashMap<>();
        for (Journey j : journeys) {
            String type = j.getPassengerType();
            counts.putIfAbsent(type, 0);
            counts.put(type, counts.get(type) + 1);
        }
        return counts;
    }
    public Map<String, BigDecimal> getTotalsByPassengerFare() {
        Map<String, BigDecimal> totals = new HashMap<>();
        for (Journey j : journeys) {
            String type = j.getPassengerType();
            totals.putIfAbsent(type, BigDecimal.ZERO);
            totals.put(type, totals.get(type).add(j.getChargedFare()));
        }
        return totals;
    }
    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\"name\": \"").append(name).append("\",\n");
        sb.append("\"passengerType\": \"").append(passengerType).append("\",\n");
        sb.append("\"paymentMethod\": \"").append(paymentMethod).append("\",\n");
        sb.append("\"journeys\": [\n");
        for (int i = 0; i < journeys.size(); i++) {
            Journey j = journeys.get(i);
            sb.append("{");
            sb.append("\"id\": ").append(j.getId()).append(",");
            sb.append("\"date\": \"").append(j.getDate()).append("\",");
            sb.append("\"from\": ").append(j.getFromZone()).append(",");
            sb.append("\"to\": ").append(j.getToZone()).append(",");
            sb.append("\"fare\": ").append(j.getChargedFare()).append(",");
            sb.append("\"passengerType\": \"").append(j.getPassengerType()).append("\",");
            sb.append("\"timeBand\": \"").append(j.getTimeBand()).append("\"");
            sb.append("}");

            if (i < journeys.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n}");
        return sb.toString();
    }
    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
}

class Admin extends User {
    private String password = "Admin";
    Menu menu = new Menu();
    public boolean login(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void viewConfig() {
        System.out.println("\n=== CONFIGURATION ===");
        System.out.println("\n-- Base Fares --");
        for (int from = 1; from <= 5; from++) {
            for (int to = 1; to <= 5; to++) {
                for (CityRideDataset.TimeBand band : CityRideDataset.TimeBand.values()) {
                    BigDecimal fare = CityRideDataset.getBaseFare(from, to, band);
                    System.out.println(
                            "From " + from + " To " + to + " | " + band + " | £" + fare.setScale(2, RoundingMode.HALF_UP));
                }
            }
        }
        System.out.println("\n-- Discounts --");
        for (CityRideDataset.PassengerType type : CityRideDataset.DISCOUNT_RATE.keySet()) {
            BigDecimal discount = CityRideDataset.DISCOUNT_RATE.get(type);
            System.out.println(type + " | " + discount.multiply(BigDecimal.valueOf(100)) + "%");
        }
        System.out.println("\n-- Daily Caps --");
        for (CityRideDataset.PassengerType type : CityRideDataset.DAILY_CAP.keySet()) {
            BigDecimal cap = CityRideDataset.DAILY_CAP.get(type);
            System.out.println(type + " | £" + cap.setScale(2, RoundingMode.HALF_UP));
        }
    }
    private CityRideDataset.PassengerType choosePassengerType(Scanner scanner) {
        System.out.println("Choose Passenger Type:");
        System.out.println("1. Adult");
        System.out.println("2. Student");
        System.out.println("3. Child");
        System.out.println("4. Senior");
        System.out.print("Choose option: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Enter a number!");
            scanner.next();
            return CityRideDataset.PassengerType.ADULT;
        }
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: return CityRideDataset.PassengerType.ADULT;
            case 2: return CityRideDataset.PassengerType.STUDENT;
            case 3: return CityRideDataset.PassengerType.CHILD;
            case 4: return CityRideDataset.PassengerType.SENIOR_CITIZEN;
            default:
                System.out.println("Invalid! Default = Adult");
                return CityRideDataset.PassengerType.ADULT;
        }
    }
    public void updateConfig(Scanner scanner) {
        boolean run = true;
        while (run) {
            System.out.println("\n=== UPDATE CONFIG ===");
            System.out.println("1. Update Discount");
            System.out.println("2. Update Daily Cap");
            System.out.println("3. Back");
            System.out.print("Choose option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Enter a number!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    CityRideDataset.PassengerType type1 = choosePassengerType(scanner);
                    System.out.print("Enter new discount (e.g. 0.2 for 20%): ");
                    String input = scanner.next();
                    BigDecimal discount = new BigDecimal(input);
                    if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.ONE) > 0) {
                        System.out.println("Invalid discount! Must be between 0 and 1.");
                        break;
                    }
                    CityRideDataset.updateDiscount(type1, discount);
                    System.out.println("Discount updated!");
                    Config.save();
                    break;
                case 2:
                    CityRideDataset.PassengerType type2 = choosePassengerType(scanner);
                    System.out.print("Enter new daily cap: ");
                    String input2 = scanner.next();
                    BigDecimal cap = new BigDecimal(input2);
                    if (cap.compareTo(BigDecimal.ZERO) <= 0) {
                        System.out.println("Invalid cap! Must be positive.");
                        break;
                    }
                    CityRideDataset.updateCap(type2, cap);
                    System.out.println("Daily cap updated!");
                    Config.save();
                    break;
                case 3:
                    run = false;
                    menu.showAdminMenu();
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

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
        boolean run = true;
        int value = -1;
        while (run) {
            System.out.print(message);
            Integer input = readIntSafe();
            if (input == null) {
                System.out.println("Enter a number!");
            } else if (input >= 1 && input <= 5) {
                value = input;
                run = false;
            } else {
                System.out.println("Invalid zone! (1-5)");
            }
        }
        return value;
    }
    private String choosePassengerTypeShort() {
        while (true) {
            System.out.print("Passenger type (A=Adult, Stu=Student, Ch=Child, Sen=Senior): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("A")) return "Adult";
            if (input.equalsIgnoreCase("Stu")) return "Student";
            if (input.equalsIgnoreCase("Ch")) return "Child";
            if (input.equalsIgnoreCase("Sen")) return "Senior";
            System.out.println("Invalid input! Try again.");
        }
    }
    private String choosePaymentMethod() {
        while (true) {
            System.out.print("Payment method (card/cash): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("card")) return "card";
            if (input.equalsIgnoreCase("cash")) return "cash";
            System.out.println("Invalid input! Try again.");
        }
    }
    private String validatePassengerType() {
        boolean run = true;
        String result = null;
        while (run) {
            System.out.println("Passenger type:");
            System.out.println("1. Adult");
            System.out.println("2. Student");
            System.out.println("3. Child");
            System.out.println("4. Senior");
            System.out.print("Choose option: ");
            Integer choice = readIntSafe();
            if (choice == null) {
                System.out.println("Enter a number!");
            } else {
                switch (choice) {
                    case 1:
                        result = "Adult";
                        run = false;
                        break;
                    case 2:
                        result = "Student";
                        run = false;
                        break;
                    case 3:
                        result = "Child";
                        run = false;
                        break;
                    case 4:
                        result = "Senior";
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
        return result;
    }
    private String validateTimeBand() {
        boolean run = true;
        String result = null;
        while (run) {
            System.out.println("Time band:");
            System.out.println("1. Peak");
            System.out.println("2. OffPeak");
            System.out.print("Choose option: ");
            Integer choice = readIntSafe();
            if (choice == null) {
                System.out.println("Enter a number!");
            } else if (choice == 1) {
                result = "Peak";
                run = false;
            } else if (choice == 2) {
                result = "OffPeak";
                run = false;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        return result;
    }
    private LocalDate validateDate() {
        boolean run = true;
        LocalDate date = null;
        while (run) {
            try {
                System.out.print("Enter date (yyyy-mm-dd): ");
                String input = scanner.next();
                date = LocalDate.parse(input);
                run = false;
            }
            catch (Exception e) {
                System.out.println("Invalid date format!");
            }
        }
        return date;
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
    //gpt
    private Rider parseRider(String data) {
        Rider r = new Rider();
        String name = data.split("\"name\": \"")[1].split("\"")[0];
        r.setName(name);
        String pType = data.split("\"passengerType\": \"")[1].split("\"")[0];
        r.setPassengerType(pType);
        String payment = data.split("\"paymentMethod\": \"")[1].split("\"")[0];
        r.setPaymentMethod(payment);
        String[] parts = data.split("\\{");
        int maxId = 0;
        for (String part : parts) {
            if (part.contains("\"id\"")) {
                try {
                    int id = Integer.parseInt(part.split("\"id\": ")[1].split(",")[0]);
                    String dateStr = part.split("\"date\": \"")[1].split("\"")[0];
                    int from = Integer.parseInt(part.split("\"from\": ")[1].split(",")[0]);
                    int to = Integer.parseInt(part.split("\"to\": ")[1].split(",")[0]);
                    String type = part.split("\"passengerType\": \"")[1].split("\"")[0];
                    String time = part.split("\"timeBand\": \"")[1].split("\"")[0];
                    LocalDate date = LocalDate.parse(dateStr);
                    Journey j = new Journey(id, date, from, to, type, time);
                    r.addJourney(j);
                    if (id > maxId) maxId = id;
                }
                catch (Exception e) {
                    System.out.println("Error parsing journey");
                }
            }
        }
        r.setNextId(maxId + 1);
        return r;
    }//

    boolean isLoaded = true;

    public void showMainMenu() {
        boolean run = true;
        while (run) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Rider");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Enter a number!");
                scanner.next();
                continue;
            }
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
        boolean profRun = true;
        while (profRun){
        System.out.println("\n1. Load Profile");
        System.out.println("2. New Session");
        System.out.print("Choose option: ");
        String startChoice = scanner.next();
            if (startChoice.equals("1")) {
                JSONFileManager fm = new JSONFileManager();
                System.out.print("Enter username: ");
                String username = scanner.next();
                String data = fm.load();
                if (data == null || data.isEmpty()) {
                    System.out.println("No saved data found!");
                    continue;
                }
                else if (!data.contains("\"name\": \"" + username + "\"")) {
                    System.out.println("User not found!");
                    continue;
                }
                else {
                    System.out.println("Profile loaded!");
                    rider = parseRider(data);
                    isLoaded = true;
                    profRun = false;
                }

            }
        else if (startChoice.equals("2")) {
            System.out.println("Starting new session");
            isLoaded = false;
            profRun = false;
        }
        else{
            System.out.println("Incorrect Input!");
        }
        }

        while (run) {
            System.out.println("\n=== RIDER MENU ===");
            System.out.println("1. Add Journey");
            System.out.println("2. List Journeys");
            System.out.println("3. Filter Journeys");
            System.out.println("4. View Daily Summary");
            System.out.println("5. View Totals by Passenger Type");
            System.out.println("6. Remove Journey");
            System.out.println("7. Reset Day");
            System.out.println("8. Exit");
            System.out.println("9. Export CSV Report");
            System.out.print("Choose option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Enter a number!");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:

                    LocalDate date = validateDate();
                    int from = validateZone("From zone (1-5): ");
                    int to = validateZone("To zone (1-5): ");
                    String type = validatePassengerType();
                    String time = validateTimeBand();
                    if (from == -1 || to == -1 || type == null || time == null || date == null) {
                        System.out.println("Failed to add journey. Try again.");
                        break;
                    }
                    Journey j = new Journey(rider.getNextId(), date, from, to, type, time);
                    rider.addJourney(j);
                    rider.applyDailyCap();
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
                    System.out.println("=== Filter Journeys ===");
                    System.out.println("Filter by:");
                    System.out.println("1. TimeBand");
                    System.out.println("2. PassengerType");
                    System.out.println("3. Both");
                    System.out.println("4. Cancel");
                    System.out.print("Choose option: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Enter a number!");
                        scanner.next();
                        continue;
                    }
                    int filterChoice = scanner.nextInt();
                    String filterTime = null;
                    String filterType = null;
                    switch (filterChoice) {
                        case 1:
                            filterTime = validateTimeBand();
                            break;
                        case 2:
                            filterType = validatePassengerType();
                            break;
                        case 3:
                            filterTime = validateTimeBand();
                            filterType = validatePassengerType();
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    List<Journey> filtered = rider.filterJourneys(filterTime, filterType);
                    if (filtered.isEmpty()) {
                        System.out.println("No matching journeys.");
                    } else {
                        for (Journey journey : filtered) {
                            System.out.println(journey);
                        }
                    }
                    break;
                case 4:
                    ReportGenerator.generateReport(rider);
                    break;
                case 5:
                    Map<String, Integer> countsByType = rider.getTotalsByPassengerCount();
                    Map<String, BigDecimal> totalsByType = rider.getTotalsByPassengerFare();
                    if (countsByType.isEmpty()) {
                        System.out.println("No journeys.");
                        break;
                    }
                    System.out.println("\n=== TOTALS BY PASSENGER TYPE ===");
                    for (String totaltype : countsByType.keySet()) {
                        int count = countsByType.get(totaltype);
                        BigDecimal total = totalsByType.get(totaltype).setScale(2, RoundingMode.HALF_UP);
                        System.out.println(totaltype + " | Journeys: " + count + " | Total: £" + total);
                    }

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
                    if (!isLoaded){
                    boolean go = true;
                    while (go){
                    System.out.print("Save session? (y/n): ");
                    String save = scanner.next();
                    if (save.equalsIgnoreCase("y")) {
                        System.out.print("Enter name: ");
                        rider.setName(scanner.next());
                        rider.setPassengerType(choosePassengerTypeShort());
                        rider.setPaymentMethod(choosePaymentMethod());
                        JSONFileManager fm = new JSONFileManager();
                        fm.save(rider.toJSON());
                        System.out.println("Saved!");
                        go = false;
                    }
                    else if (save.equalsIgnoreCase("n")) {

                        System.out.println("Exiting...");
                        go = false;
                        }
                    else{
                        System.out.println("Wrong answer enter y or n");

                            }
                        }
                    }
                    return;
                case 9:

                    ReportGenerator rg = new ReportGenerator(rider);
                    rg.exportCSV();
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void showAdminMenu() {
        Admin admin = new Admin();
        System.out.print("Enter admin password (Admin): ");
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
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Enter a number!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    admin.viewConfig();

                    break;
                case 2:
                    admin.updateConfig(scanner);
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
    private LocalDate date;
    private int fromZone;
    private int toZone;
    private String passengerType;
    private String timeBand;
    private int zoneCrossed;
    private BigDecimal baseFare;
    private BigDecimal discountedFare;
    private BigDecimal chargedFare;


    private CityRideDataset.PassengerType mapPassengerType(String type) {
        switch (type.toLowerCase()) {
            case "adult": return CityRideDataset.PassengerType.ADULT;
            case "student": return CityRideDataset.PassengerType.STUDENT;
            case "child": return CityRideDataset.PassengerType.CHILD;
            case "senior": return CityRideDataset.PassengerType.SENIOR_CITIZEN;
            default: throw new IllegalArgumentException("Invalid passenger type");
        }
    }
    private CityRideDataset.TimeBand mapTimeBand(String time) {
        switch (time.toLowerCase()) {
            case "peak": return CityRideDataset.TimeBand.PEAK;
            case "offpeak": return CityRideDataset.TimeBand.OFF_PEAK;
            default: throw new IllegalArgumentException("Invalid time band");
        }
    }

    public Journey(int id, LocalDate date, int fromZone, int toZone, String passengerType, String timeBand){
        this.id = id;
        this.date = date;
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.passengerType = passengerType;
        this.timeBand = timeBand;
        this.zoneCrossed = Math.abs(toZone - fromZone) + 1;
        CityRideDataset.PassengerType pType = mapPassengerType(passengerType);
        CityRideDataset.TimeBand tBand = mapTimeBand(timeBand);
        baseFare = CityRideDataset.getBaseFare(fromZone, toZone, tBand);
        BigDecimal discount = CityRideDataset.DISCOUNT_RATE.get(pType);
        discountedFare = baseFare.multiply(BigDecimal.ONE.subtract(discount));
        chargedFare = discountedFare;
    }

    public int getId() {return id;}
    public int getFromZone() {return fromZone;}
    public int getToZone() {return toZone;}
    public int getZonesCrossed() {return zoneCrossed;}
    public BigDecimal getChargedFare() {return chargedFare;}
    public String getPassengerType() {
        return passengerType;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setChargedFare(BigDecimal fare) {
        this.chargedFare = fare;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                " | From: " + fromZone +
                " | To: " + toZone +
                " | Zones: " + zoneCrossed +
                " | Fare: £" + chargedFare.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public String getTimeBand() {
        return timeBand;
    }
}





class ReportGenerator {

    private Rider rider;

    public ReportGenerator(Rider rider) {
        this.rider = rider;
    }

    public static void generateReport(Rider rider) {
        List<Journey> journeys = rider.getJourneys();
        if (journeys.isEmpty()) {
            System.out.println("No journeys to report.");
            return;
        }
        int totalJourneys = journeys.size();
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal uncappedTotal = BigDecimal.ZERO;
        Journey mostExpensive = journeys.get(0);
        int peakCount = 0;
        int offPeakCount = 0;
        Map<String, Integer> zonePairs = new HashMap<>();
        for (Journey j : journeys) {
            BigDecimal fare = j.getChargedFare();
            totalCost = totalCost.add(fare);


            uncappedTotal = uncappedTotal.add(fare);
            if (fare.compareTo(mostExpensive.getChargedFare()) > 0) {
                mostExpensive = j;
            }
            if (j.getTimeBand().equalsIgnoreCase("Peak")) {
                peakCount++;
            } else {
                offPeakCount++;
            }
            String key = j.getFromZone() + "-" + j.getToZone();
            zonePairs.put(key, zonePairs.getOrDefault(key, 0) + 1);
        }
        BigDecimal average = totalCost.divide(BigDecimal.valueOf(totalJourneys), 2, RoundingMode.HALF_UP);
        boolean capReached = totalCost.compareTo(uncappedTotal) < 0;
        BigDecimal savings = uncappedTotal.subtract(totalCost);


        System.out.println("\n=== END OF DAY REPORT ===");
        System.out.println("Journeys: " + totalJourneys);
        System.out.println("Total cost: £" + totalCost.setScale(2, RoundingMode.HALF_UP));
        System.out.println("Average: £" + average);
        System.out.println("Most expensive: ID " + mostExpensive.getId() + " (£" + mostExpensive.getChargedFare().setScale(2, RoundingMode.HALF_UP) + ")");
        System.out.println("\nPeak: " + peakCount + " | Off-Peak: " + offPeakCount);
        System.out.println("\nZone usage:");
        for (String z : zonePairs.keySet()) {
            System.out.println(z + " -> " + zonePairs.get(z));
        }
        System.out.println("\nCap reached: " + (capReached ? "YES" : "NO"));
        System.out.println("Savings: £" + savings.setScale(2, RoundingMode.HALF_UP));
    }
    public void exportCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Date,From,To,Zones,Passenger,TimeBand,Fare\n");
        for (Journey j : rider.getJourneys()) {
            sb.append(j.getId()).append(",");
            sb.append(j.getDate()).append(",");
            sb.append(j.getFromZone()).append(",");
            sb.append(j.getToZone()).append(",");
            sb.append(j.getZonesCrossed()).append(",");
            sb.append(j.getPassengerType()).append(",");
            sb.append(j.getTimeBand()).append(",");
            sb.append(j.getChargedFare()).append("\n");
        }
        CSVFileManager fm = new CSVFileManager();
        fm.save(sb.toString());
    }
}
class Config {
    public static void save() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("config.json");
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("\"discounts\": {\n");
            for (CityRideDataset.PassengerType type : CityRideDataset.DISCOUNT_RATE.keySet()) {
                sb.append("\"").append(type).append("\": ").append(CityRideDataset.DISCOUNT_RATE.get(type)).append(",\n");
            }
            sb.append("},\n");
            sb.append("\"caps\": {\n");
            for (CityRideDataset.PassengerType type : CityRideDataset.DAILY_CAP.keySet()) {
                sb.append("\"").append(type).append("\": ").append(CityRideDataset.DAILY_CAP.get(type)).append(",\n");
            }
            sb.append("}\n");
            sb.append("}");
            writer.write(sb.toString());
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error saving config");
        }
    }
    public static void load() {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("config.json"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String data = sb.toString();
            for (CityRideDataset.PassengerType type : CityRideDataset.PassengerType.values()) {
                if (data.contains(type.toString())) {
                    String discountStr = data.split(type + "\": ")[1].split(",")[0];
                    BigDecimal discount = new BigDecimal(discountStr);
                    CityRideDataset.DISCOUNT_RATE.put(type, discount);
                    String capStr = data.split(type + "\": ")[2].split(",|}")[0];
                    BigDecimal cap = new BigDecimal(capStr);
                    CityRideDataset.DAILY_CAP.put(type, cap);
                }
            }
            reader.close();
        }
        catch (Exception e) {
            System.out.println("No config file → using defaults");
        }
    }
}



interface FileManager {
    void save(String data);
    String load();
}
//
//
class JSONFileManager implements FileManager {
    public void save(String data) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("rider.json");
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }
    public String load() {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("rider.json"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        }
        catch (Exception e) {
            System.out.println("Error loading file");
            return null;
        }
    }
}

class CSVFileManager implements FileManager {
    public void save(String data) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("report.csv");
            writer.write(data);
            writer.close();
            System.out.println("CSV report saved!");
        } catch (Exception e) {
            System.out.println("Error saving CSV file");
        }
    }
    public String load() {
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Config.load();
        Menu menu = new Menu();
        menu.showMainMenu();
    }
}