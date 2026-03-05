import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;
import java.math.RoundingMode;

class Journey {
    private final int id;
    private String date;
    private int fromZone;
    private int toZone;
    private String passengerType;
    private String timeBand;
    private int zoneCrossed;


    private BigDecimal baseFare;
    private BigDecimal discountedFare;
    private BigDecimal chargedFare;

    public Journey() {
        id = 0;
        date = "Unknown";
        fromZone = 0;
        toZone = 0;
        passengerType = "Unknown";
        timeBand = "Unknown";
        zoneCrossed = 0;

        baseFare = money("0.00");
        discountedFare = money("0.00");
        chargedFare = money("0.00");
    }

    public Journey(int id, String date, int fromZone, int toZone, String passengerType, String timeBand) {
        this.id = id;
        this.date = date;
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.passengerType = passengerType;
        this.timeBand = timeBand;
        recalcZonesCrossed();

        baseFare = money("0.00");
        discountedFare = money("0.00");
        chargedFare = money("0.00");
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public int getFromZone() { return fromZone; }
    public int getToZone() { return toZone; }
    public String getPassengerType() { return passengerType; }
    public String getTimeBand() { return timeBand; }
    public int getZonesCrossed() { return zoneCrossed; }

    public BigDecimal getBaseFare() { return baseFare; }
    public BigDecimal getDiscountedFare() { return discountedFare; }
    public BigDecimal getChargedFare() { return chargedFare; }

    public void setDate(String date) { this.date = date; }
    public void setFromZone(int fromZone) { this.fromZone = fromZone; recalcZonesCrossed(); }
    public void setToZone(int toZone) { this.toZone = toZone; recalcZonesCrossed(); }
    public void setPassengerType(String passengerType) { this.passengerType = passengerType; }
    public void setTimeBand(String timeBand) { this.timeBand = timeBand; }

    public void setBaseFare(BigDecimal baseFare) { this.baseFare = money(baseFare); }
    public void setDiscountedFare(BigDecimal discountedFare) { this.discountedFare = money(discountedFare); }
    public void setChargedFare(BigDecimal chargedFare) { this.chargedFare = money(chargedFare); }

    private void recalcZonesCrossed() {
        zoneCrossed = Math.abs(toZone - fromZone) + 1;
    }

    public void displayDetails() {
        System.out.println("[ID " + id + "] Date: " + date +
                " | " + fromZone + " -> " + toZone +
                " | Zones crossed: " + zoneCrossed +
                " | " + timeBand +
                " | " + passengerType +
                " | Base: £" + baseFare +
                " | Discounted: £" + discountedFare +
                " | Charged: £" + chargedFare);
    }

    private BigDecimal money(String amount) {
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal money(BigDecimal amount) {
        if (amount == null) return new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}

class manageJourney {
    private final List<Journey> journeyList = new ArrayList<>();
    private int nextId = 1;

    public int getNextId() {
        return nextId++;
    }

    public void addJourney(Journey journey) {
        journeyList.add(journey);
        System.out.println("Journey added with ID: " + journey.getId());
    }

    public void showAllJourneys() {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded.");
        } else {
            for (Journey j : journeyList) {
                j.displayDetails();
            }
        }
    }

    public void addJourneyFromUser(Scanner sc) {
        String date = readDate(sc);

        int fromZone = readIntInRange(
                sc,
                "Enter From Zone (" + CityRideDataset.MIN_ZONE + "-" + CityRideDataset.MAX_ZONE + "): ",
                CityRideDataset.MIN_ZONE,
                CityRideDataset.MAX_ZONE
        );

        int toZone = readIntInRange(
                sc,
                "Enter To Zone (" + CityRideDataset.MIN_ZONE + "-" + CityRideDataset.MAX_ZONE + "): ",
                CityRideDataset.MIN_ZONE,
                CityRideDataset.MAX_ZONE
        );

        CityRideDataset.PassengerType passengerType = readPassengerType(sc);
        CityRideDataset.TimeBand timeBand = readTimeBand(sc);

        Journey newJourney = new Journey(
                getNextId(),
                date,
                fromZone,
                toZone,
                passengerType.name(),
                timeBand.name()
        );


        applyFareAndCap(newJourney);

        addJourney(newJourney);
    }


    private void applyFareAndCap(Journey journey) {
        CityRideDataset.PassengerType p;
        CityRideDataset.TimeBand b;

        try {
            p = CityRideDataset.PassengerType.valueOf(journey.getPassengerType());
            b = CityRideDataset.TimeBand.valueOf(journey.getTimeBand());
        } catch (IllegalArgumentException ex) {

            journey.setBaseFare(money("0.00"));
            journey.setDiscountedFare(money("0.00"));
            journey.setChargedFare(money("0.00"));
            return;
        }

        BigDecimal base = CityRideDataset.getBaseFare(journey.getFromZone(), journey.getToZone(), b);
        if (base == null) base = money("0.00");
        journey.setBaseFare(base);

        BigDecimal discountRate = CityRideDataset.DISCOUNT_RATE.get(p);
        if (discountRate == null) discountRate = money("0.00");

        BigDecimal discounted = base.multiply(BigDecimal.ONE.subtract(discountRate));
        discounted = discounted.setScale(2, RoundingMode.HALF_UP);
        journey.setDiscountedFare(discounted);

        BigDecimal cap = CityRideDataset.DAILY_CAP.get(p);
        if (cap == null) cap = money("0.00");

        BigDecimal spentToday = getSpentForDateAndPassenger(journey.getDate(), p);
        BigDecimal remaining = cap.subtract(spentToday).setScale(2, RoundingMode.HALF_UP);

        BigDecimal charged;
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            charged = money("0.00");
        } else if (discounted.compareTo(remaining) > 0) {
            charged = remaining;
        } else {
            charged = discounted;
        }

        journey.setChargedFare(charged);
    }

    private BigDecimal getSpentForDateAndPassenger(String date, CityRideDataset.PassengerType passengerType) {
        BigDecimal total = money("0.00");
        for (Journey j : journeyList) {
            if (j.getDate().equals(date)) {
                try {
                    CityRideDataset.PassengerType p = CityRideDataset.PassengerType.valueOf(j.getPassengerType());
                    if (p == passengerType) {
                        total = total.add(j.getChargedFare());
                    }
                } catch (IllegalArgumentException ignored) {}
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal money(String amount) {
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }


    public void filterJourneys(Scanner sc) {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded.");
            return;
        }

        System.out.println("\n--- Filter journeys ---");
        System.out.println("1) By date (yyyy-MM-dd)");
        System.out.println("2) By passenger type");
        System.out.println("3) By time band");
        System.out.println("4) By zones crossed");
        System.out.println("0) Back");
        System.out.print("Enter your choice: ");

        String line = sc.nextLine().trim();

        int choice;
        try {
            choice = Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            System.out.println("Incorrect input. Please enter a number.");
            return;
        }

        boolean foundAny = false;

        switch (choice) {
            case 1: {
                String date = readDate(sc);
                for (Journey j : journeyList) {
                    if (j.getDate().equals(date)) {
                        j.displayDetails();
                        foundAny = true;
                    }
                }
                break;
            }
            case 2: {
                CityRideDataset.PassengerType passengerType = readPassengerType(sc);
                String target = passengerType.name();
                for (Journey j : journeyList) {
                    if (j.getPassengerType().equalsIgnoreCase(target)) {
                        j.displayDetails();
                        foundAny = true;
                    }
                }
                break;
            }
            case 3: {
                CityRideDataset.TimeBand timeBand = readTimeBand(sc);
                String target = timeBand.name();
                for (Journey j : journeyList) {
                    if (j.getTimeBand().equalsIgnoreCase(target)) {
                        j.displayDetails();
                        foundAny = true;
                    }
                }
                break;
            }
            case 4: {
                int zones = readIntInRange(sc, "Enter zones crossed (1-99): ", 1, 99);
                for (Journey j : journeyList) {
                    if (j.getZonesCrossed() == zones) {
                        j.displayDetails();
                        foundAny = true;
                    }
                }
                break;
            }
            case 0:
                return;
            default:
                System.out.println("Incorrect Input, try again");
                return;
        }

        if (!foundAny) {
            System.out.println("No journeys matched your filter.");
        }
    }


    public void viewDailySummary(Scanner sc) {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded.");
            return;
        }

        String date = readDate(sc);

        int totalJourneys = 0;
        int totalZones = 0;
        BigDecimal totalCharged = money("0.00");

        for (Journey j : journeyList) {
            if (j.getDate().equals(date)) {
                totalJourneys++;
                totalZones += j.getZonesCrossed();
                totalCharged = totalCharged.add(j.getChargedFare());
            }
        }

        System.out.println("\n--- Daily summary for: " + date + " ---");
        System.out.println("Total journeys: " + totalJourneys);
        System.out.println("Total zones crossed: " + totalZones);
        System.out.println("Total charged: £" + totalCharged.setScale(2, RoundingMode.HALF_UP));

        if (totalJourneys == 0) {
            System.out.println("No journeys for this date.");
        }
    }


    public void viewTotalsByPassenger() {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded.");
            return;
        }

        int adultCount = 0, studentCount = 0, childCount = 0, seniorCount = 0;
        BigDecimal adultCharged = money("0.00");
        BigDecimal studentCharged = money("0.00");
        BigDecimal childCharged = money("0.00");
        BigDecimal seniorCharged = money("0.00");

        for (Journey j : journeyList) {
            if (j.getPassengerType().equalsIgnoreCase(CityRideDataset.PassengerType.ADULT.name())) {
                adultCount++;
                adultCharged = adultCharged.add(j.getChargedFare());
            } else if (j.getPassengerType().equalsIgnoreCase(CityRideDataset.PassengerType.STUDENT.name())) {
                studentCount++;
                studentCharged = studentCharged.add(j.getChargedFare());
            } else if (j.getPassengerType().equalsIgnoreCase(CityRideDataset.PassengerType.CHILD.name())) {
                childCount++;
                childCharged = childCharged.add(j.getChargedFare());
            } else if (j.getPassengerType().equalsIgnoreCase(CityRideDataset.PassengerType.SENIOR_CITIZEN.name())) {
                seniorCount++;
                seniorCharged = seniorCharged.add(j.getChargedFare());
            }
        }

        System.out.println("\n--- Totals by passenger type ---");
        System.out.println("ADULT: " + adultCount + " journeys | charged: £" + adultCharged.setScale(2, RoundingMode.HALF_UP));
        System.out.println("STUDENT: " + studentCount + " journeys | charged: £" + studentCharged.setScale(2, RoundingMode.HALF_UP));
        System.out.println("CHILD: " + childCount + " journeys | charged: £" + childCharged.setScale(2, RoundingMode.HALF_UP));
        System.out.println("SENIOR_CITIZEN: " + seniorCount + " journeys | charged: £" + seniorCharged.setScale(2, RoundingMode.HALF_UP));
    }


    public void removeDay(Scanner sc) {
        if (journeyList.isEmpty()) {
            System.out.println("No journeys recorded.");
            return;
        }

        String date = readDate(sc);

        int before = journeyList.size();
        for (int i = journeyList.size() - 1; i >= 0; i--) {
            if (journeyList.get(i).getDate().equals(date)) {
                journeyList.remove(i);
            }
        }
        int removed = before - journeyList.size();

        if (removed > 0) {
            System.out.println("Removed journeys for date " + date + ": " + removed);
        } else {
            System.out.println("No journeys found for date " + date);
        }
    }


    public void resetDay() {
        journeyList.clear();
        nextId = 1;
        System.out.println("All journeys cleared. Day reset complete.");
    }

    //(ChatGPT, 2024)
    //works with dates and checks their input
    private String readDate(Scanner sc) {
        while (true) {
            System.out.print("Enter date (yyyy-MM-dd) or leave blank for today: ");
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                return LocalDate.now().toString();
            }

            try {
                LocalDate parsed = LocalDate.parse(line);
                return parsed.toString();
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format. Example: 2026-02-13");
            }
        }
    }
    //(ChatGPT,2024)
    //checks that the user enters a number and makes sure it is within the allowed range. If the input is invalid, it shows an error and asks again.
    private int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();

            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input. Please enter a number.");
            }
        }
    }

    private CityRideDataset.PassengerType readPassengerType(Scanner sc) {
        while (true) {
            System.out.println("Choose passenger type:");
            System.out.println("1) ADULT");
            System.out.println("2) STUDENT");
            System.out.println("3) CHILD");
            System.out.println("4) SENIOR_CITIZEN");
            System.out.print("Enter choice (1-4): ");

            String line = sc.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input. Please enter a number 1-4.");
                continue;
            }

            switch (choice) {
                case 1: return CityRideDataset.PassengerType.ADULT;
                case 2: return CityRideDataset.PassengerType.STUDENT;
                case 3: return CityRideDataset.PassengerType.CHILD;
                case 4: return CityRideDataset.PassengerType.SENIOR_CITIZEN;
                default:
                    System.out.println("Value must be between 1 and 4.");
            }
        }
    }

    private CityRideDataset.TimeBand readTimeBand(Scanner sc) {
        while (true) {
            System.out.println("Choose time band:");
            System.out.println("1) PEAK");
            System.out.println("2) OFF_PEAK");
            System.out.print("Enter choice (1-2): ");

            String line = sc.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input. Please enter a number 1-2.");
                continue;
            }

            switch (choice) {
                case 1: return CityRideDataset.TimeBand.PEAK;
                case 2: return CityRideDataset.TimeBand.OFF_PEAK;
                default:
                    System.out.println("Value must be 1 or 2.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        manageJourney manage = new manageJourney();
        boolean running = true;

        while (running) {
            System.out.println("\n---- Menu ----");
            System.out.println("1) Add journey");
            System.out.println("2) List journeys");
            System.out.println("3) Filter journeys");
            System.out.println("4) View daily summary");
            System.out.println("5) View totals by passenger");
            System.out.println("6) Remove day");
            System.out.println("7) Reset day");
            System.out.println("0) Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Incorrect input. Please enter a number.");
                sc.next();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manage.addJourneyFromUser(sc);
                    break;
                case 2:
                    manage.showAllJourneys();
                    break;
                case 3:
                    manage.filterJourneys(sc);
                    break;
                case 4:
                    manage.viewDailySummary(sc);
                    break;
                case 5:
                    manage.viewTotalsByPassenger();
                    break;
                case 6:
                    manage.removeDay(sc);
                    break;
                case 7:
                    manage.resetDay();
                    break;
                case 0:
                    System.out.println("Thank you, goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Incorrect Input, try again");
                    break;
            }
        }

        sc.close();
    }
}
