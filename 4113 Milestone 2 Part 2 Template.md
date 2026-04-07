# IY4113 Part 2 Milestone 3

| Assessment Details | Please Complete All Details                       |
| ------------------ | ------------------------------------------------- |
| Group              | A                                                 |
| Module Title       | IY4113                                            |
| Assessment Type    |                                                   |
| Module Tutor Name  | Jonathon Shore                                    |
| Student ID Number  | P488221                                           |
| Date of Submission | 07.04.2026                                        |
| Word Count         |                                                   |
| GitHub Link        | https://github.com/T0488221Taymas/T0488221_Java_2 |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.

------------------------------------------------------------------------------------------------------------------------------

### Research (minimum of 2, at least 3)

---

Conduct research to support your coding process, including use of code examples, tutortials, documentation and AI tools (if used).

Use the structure below to capture your evidence:

------------------------------------------------------------------------------------------------------------------------------

Title of research: Abstract

Reference (link): GeeksForDeeks (2026).  *Abstract Class in Java*. Retrieved from: [Abstract Class in Java - GeeksforGeeks](https://www.geeksforgeeks.org/java/abstract-classes-in-java/)

How does the research help with coding practise?:

In my code I wanted to use polymorphism, but I had an understanding of it only in C++, because of this a research on this topic was carried out. During the recreation of the class diagram, I realized that the parent class User is ideally suited to the criteria of using this method.

Key coding ideas you could reuse in your program:

So, abstract is used in OOP, in particular in polymorphism and inheritance. The attribute name and the method getName had to be present both in Rider and in Admin, so as not to rewrite this, it was decided to use polymorphism.

Screenshot of research:

![](C:\Users\user\AppData\Roaming\marktext\images\2026-04-07-00-14-40-image.png)

------------------------------------------------------------------------------------------------------------------------------

Title of research: interface

Reference (link): [Interface vs abstract class in Java C# - YouTube](https://youtu.be/uA_6W4aWRFg?si=ZGSWKl3JwV50OeR-)

How does the research help with coding practise?:

In my code I wanted to use interfaces, but I did not fully understand how they work in Java, because of this a research on this topic was carried out. During the process it became clear that interfaces allow to define a common contract for classes that implement similar behaviour.

Key coding ideas you could reuse in your program:

For example, in the program different classes are used for working with files, such as JSON, CSV and Text. In order not to duplicate methods, an interface FileManager was created, which contains methods save and load, and the concrete classes implement them in their own way.

Screenshot of research:

![](C:\Users\user\AppData\Roaming\marktext\images\2026-04-07-00-26-33-image.png)

------------------------------------------------------------------------------------------------------------------------------

### Program Code

---

Paste the current program code created so far. It does not have to be runnable code (document though if it does not work!)

------------------------------------------------------------------------------------------------------------------------------

*Program code goes here:*

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

------------------------------------------------------------------------------------------------------------------------------

### Updated Class Diagramm

---

![class diagramm - Updated Class Diagramm (1) (1).svg](C:\Users\user\Downloads\class%20diagramm%20-%20Updated%20Class%20Diagramm%20(1)%20(1).svg)

---

### Updated Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

![*Add your updated Gantt chart here!*](C:\Users\user\Pictures\Screenshots\Снимок%20экрана%202026-04-07%20010028.png)

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries (at least 4)

------------------------------------------------------------------------------------------------------------------------------

*Add diary entries here detailing what you have done, wny you have done it, and any problems encountered.*

**Diary Entry - 1**

As soon as I heard the news about the released grades, I went to read the feedback and find out the mark, as soon as I saw 12, I was extremely shocked, I understood that I am not very smart, but not to that extent, but when I heard that the maximum mark was 20, although I felt some relief, the aftertaste still remained. I reread the criticism of my work several times, and realized that I do not really understand the scenario, so I spent the first day rethinking the work.

And why was the work started so late, it was because of intensive preparation for course days and the C++ game, but unfortunately, what I was preparing for did not appear, because of this it was decided to leave Java for later.

**Diary Entry - 2**

On the second day, after realizing my mistakes, a rework was carried out, it started with the class diagram, most of it was reworked. The main problem was the incomplete understanding of what the program is expected to do, after intense analysis and reading of the scenario, also in the feedback it was written that the classes are too big, and they should preferably be divided, and I was confused about how to do it, because in my opinion everything was fine, but after thinking it through, in the end I managed to somewhat improve it.

**Diary Entry - 3**

On the third day, work on research was carried out. During intense preparation for C++ coursework, I thought that it would be great to use abstract classes, and when I went to GeeksforGeeks and typed “abstract” in the search, I noticed from the corner of my eye the title “abstract vs interface”, I became interested in this feature and conducted research on it, for me this seemed like the most difficult task, because what was on GeeksforGeeks was not enough for me, and I moved to YouTube, at first I thought that the longer the video the better, but it is not like that, after long searches, I found what I needed, and the phrase that I liked was said in that video:

"collection of common properties -> abstract class  

common methods to be implemented in different ways for different characters -> interface".

**Diary Entry - 4**

On the last day, work on the code was carried out, at first there was an idea to simply insert the old code, but I realized that the mark for it was not very satisfying for me, and there was an idea to redo everything from scratch, but looking at the old code. Since there was not much time, because of pressing deadlines, the work was carried out only on creating classes, their attributes and methods, also on the same day the Gantt chart was redone because my plans changed drastically regarding what I wanted to do.

------------------------------------------------------------------------------------------------------------------------------
