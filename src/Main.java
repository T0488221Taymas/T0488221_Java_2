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
    private List<Journey> journeys;

    public void addJourney(Journey j) {}
    public void deleteJourney(int id) {}
    public List<Journey> getJourneys() {}
}
class Admin extends User {
    private String password;

    public boolean login() {}
    public void updateConfig() {}
}

class Menu {
    public void showMainMenu() {}
    public void showRiderMenu() {}
    public void showAdminMenu() {}
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

    public int calculateZones() {}
    public int getFromZone() {}
    public int getToZone() {}
    public double getFinalFare() {}
    public Date getDate() {}
}


class JourneyManager {
    public void addJourney() {}
    public void deleteJourney() {}
    public void filterJourneys() {}
}


class FareCalculator {
    public double calculateFare() {}
    public double applyDiscount() {}
    public double applyCap() {}
}


class ReportGenerator {
    public String generateSummary() {}
    public void exportCSV() {}
    public void exportText() {}
    public void viewDailySummary() {}
}


class Config {
    private Map<String, Double> baseFares;
    private Map<String, Double> discounts;
    private Map<String, Double> caps;

    public boolean validate() {}
}



interface FileManager {
    void save(String data);
    String load();
}


class JSONFileManager implements FileManager {
    public void save(String data) {}
    public String load() {}
}


class TextFileManager implements FileManager {
    public void save(String data) {}
    public String load() {}
}


class CSVFileManager implements FileManager {
    public void save(String data) {}
    public String load() {}
}