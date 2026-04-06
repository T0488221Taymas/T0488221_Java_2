# IY4113 Milestone 1 Part 2

| Assessment Details | Please Complete All Details                       |
| ------------------ | ------------------------------------------------- |
| Group              | A                                                 |
| Module Title       | IY4113                                            |
| Assessment Type    |                                                   |
| Module Tutor Name  | Jonathon Shore                                    |
| Student ID Number  | P488221                                           |
| Date of Submission |                                                   |
| Word Count         |                                                   |
| GitHub Link        | https://github.com/T0488221Taymas/T0488221_Java_2 |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.
  
  

------------------------------------------------------------------------------------------------------------------------------

### Purpose of the Program

------------------------------------------------------------------------------------------------------------------------------

The purpose of the CityRide Lite program is to track user trips and calculate the total cost of travel on public transport for one day for one user in a console - based Java application, taking into account fare zones, discounts, and daily spending limits. Additionally, the program provides an admin role that manages the system, edits prices, caps, and peak windows.

### Core program functionality

- **Choose position**

The program displays the chosen of the role: user or admin. Depending on the selection, program displays different menu

- **Registration / Login**

The program allows the user to either register a new account or log in to an existing account by entering the required credentials.

- **User's Menu Display**

The program displays the user's options: add journey, list journey, filter journeys, view
daily summary, view totals by passenger type, remove journey, reset day, and exit. The program runs until the user selects exit.

- **Add Journey**

The system allows the user to add a trip by entering the relevant information (date, time band (Peak/Off-peak), from zone, to zone, passenger type). The input is then validated.

- **List of Journeys**

The user can view all added trip information.

- **Filter Journeys**

The program allows filtering the trip list by several criteria: passenger type, time band (Peak/Off-peak), zone (from or to), and date.

- **View Daily Summary**

The program displays daily information, including the number of trips per day, total cost, average trip cost, and the most expensive trip. All values ​​are rounded to 2 decimal places.

- **Viewing totals by passenger type**

The system displays separate information for each passenger type, including the number of trips, the total cost, and whether the daily limit has been reached.

- **Deleting a trip**

The user can cancel a trip by entering its ID. If the ID is correct, the trip is deleted. The user must confirm, after which the total cost is recalculated.

- **Cost Calculation**

The system calculates the trip price based on the passenger type and applies a discount. If the user has reached the daily limit, they are no longer charged.

- **Errors**

If the user has entered incorrect data, the program notifies them and asks them to repeat the entry until the data is correct.

- **Reset Day**

The program clears all information and starts from scratch.

- **Admin's menu display**

The program displays a menu with administrator options, such as managing fares, caps, peak time windows, and discounts.

- **Add (Admin Function)**

The administrator can add new system settings, such as fares, caps, peak time windows, or discounts, by entering the required data. The input is validated before being applied to the system.

- **Update (Admin Function)**

The administrator can update existing system settings, such as fares, caps, peak time windows, or discounts, by modifying the required data. All changes are validated before being applied.

- **Delete (Admin Function)**

The administrator can delete existing system settings, such as fares, caps, peak time windows, or discounts. The system requires confirmation before deletion, after which the changes are applied. All changes are validated before being applied.

- **Exit**

Ends the program.

### System constraints (limitations / rules)

- **Console UI only**: interaction occurs via text menus and keyboard input.

- **User limit:** the program only supports one user.

- **Files system:** data stores in files, all data won't lost after program finishes

- **Cost calculation rules:** after reaching the daily limit, the program charges £0.00 for subsequent trips and applies discounts to certain user types.

- **Add journey rule:** a trip is added only if all data for it is correct.

- **Delete journey rule:** a valid ID must be entered and the user must confirm deletion; totals are then recalculated.

- **Session end:** the program only ends after the user selects "exit" from the menu.

------------------------------------------------------------------------------------------------------------------------------

### Input Process Output Table

------------------------------------------------------------------------------------------------------------------------------

| Feature / Task                   | Inputs                                                                                       | Processing (what the system does)                                                                                                                                            | Outputs                                      |
| -------------------------------- | -------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- |
| Start program                    | None (program launch)                                                                        | Initializing the program, creating an empty journey list, setting daily limits and tariff data                                                                               | Main menu displayed                          |
| Select Role                      | User choice rider or admin (1,2)                                                             | Validate input, continue in selected role                                                                                                                                    | rider menu or admin menu                     |
| Admin login                      | create or login account(1-2)                                                                 | Validate input,create or checking account                                                                                                                                    | Admin's create account or login              |
| create account                   | login and password                                                                           | Validate input, add account into the file                                                                                                                                    | Successfully created                         |
| login                            | login and password                                                                           | Validate input, check if account exists or no                                                                                                                                | Hello "username" or no account               |
| Rider profile                    | create or login account(1-2)                                                                 | Validate input,create or checking account                                                                                                                                    | Rider creates account or login               |
| create rider profile             | Name, passenger type, payment option                                                         | Validate inputs; create profile and adding it into the file                                                                                                                  | "Profile created"                            |
| login                            | login and password                                                                           | Validate input, check if account exists or no                                                                                                                                | Hello "username" or no account               |
| Display main menu                | User menu choice (0-7)                                                                       | Validate input is an integer in range; route to chosen function                                                                                                              | Confirm selection / launch selected function |
| Adding journey                   | date, time band (Peak/Off-peak), from zone, to zone, passenger type                          | Checking on correct data, calculate price of journey, giving uniq iq to trip                                                                                                 | "Journey succesfully added"                  |
| List of journey                  | none(menu selection)                                                                         | Displaying all added journeys                                                                                                                                                | All journeys or "No journeys"                |
| Filter journeys                  | type of the filter (passenger type, time band (Peak/Off-peak), zone (from or to), and date.) | Selection of trips that match the selected criterias.                                                                                                                        | Filtred list of journeys or "No joureys"     |
| View daily summary               | none(menu selection)                                                                         | displays daily information, including the number of trips per day, total cost, average trip cost, and the most expensive trip. All values ​​are rounded to 2 decimal places. | Daily journey summary                        |
| Viewing totals by passenger type | none(menu selection)                                                                         | displays separate information for each passenger type, including the number of trips, the total cost, and whether the daily limit has been reached.                          | Results for each passenger type              |
| Deleting a trip                  | write uniq id of journey and confirm it                                                      | if id correct delete the journey and recalculate prices                                                                                                                      | "successfully deleted"                       |
| validating errors                | incorrect input                                                                              | error detection and asking to reenter untill input correct                                                                                                                   | "Enter valid input"                          |
| reset day                        | none(menu selection)                                                                         | clears all information and starts from scratch.                                                                                                                              | "Succesfully reseted"                        |
| Admin's menu                     | Admin menu choice(1-3)                                                                       | Validate input is an integer in range; route to chosen function                                                                                                              | Confirm selection / launch selected function |
| Add fare rules                   | Choice(1-3)                                                                                  | Validate input, route to chosen function                                                                                                                                     | "config updated" or error                    |
| Add                              | choice(1-3)                                                                                  | Validate input, and go to chosen route                                                                                                                                       | "Config updated" or error                    |
| Update                           | choice(1-3)                                                                                  | Validate input, and go to chosen route                                                                                                                                       | "Config updated" or error                    |
| Delete                           | choice(1-3)                                                                                  | Validate input, and go to chosen route                                                                                                                                       | "Config updated" or error                    |
| Discounts (add, update,delete)   | new values                                                                                   | Validate input, update configurations in files                                                                                                                               | "Config updated" or error                    |
| Daily caps (add, update,delete)  | new values                                                                                   | Validate input, update configurations in files                                                                                                                               | "Config updated" or error                    |
| Peak times (add, update,delete)  | new values                                                                                   | Validate input, update configurations in files                                                                                                                               | "Config updated" or error                    |
| Exit program                     | Menu choice = 0                                                                              | End loop; terminate program                                                                                                                                                  | “Goodbye!” message                           |

------------------------------------------------------------------------------------------------------------------------------

### Algorithm Design

---

#### Class Diagramm

![class](C:\Users\user\Downloads\class%20diagramm%20-%20Frame%201%20(1).jpg)

### Flow Chart

![a](C:\Users\user\Downloads\Flow%20chart%20-%20choose.jpg)

![b](C:\Users\user\Downloads\Flow%20chart%20-%20Frame%201.jpg)

![c](C:\Users\user\Downloads\Flow%20chart%20-%20Frame%202.jpg)

![Flow chart - Frame 3.jpg](C:\Users\user\Downloads\Flow%20chart%20-%20Frame%203.jpg)

![Flow chart - Frame 4 (1).jpg](C:\Users\user\Downloads\Flow%20chart%20-%20Frame%204%20(1).jpg)

![Flow chart - Frame 5.jpg](C:\Users\user\Downloads\Flow%20chart%20-%20Frame%205.jpg)

------------------------------------------------------------------------------------------------------------------------------

### Research (minimum of 1 required, preferrebly 2)

---

*Research existing programs that solve a similar problem. The program does not have to be written in java or object orientated in nature - just solve a similar type of problem.*

*Use the strucutre below to capture your evidence:*

------------------------------------------------------------------------------------------------------------------------------**Name of program:** *A c++ Application with functionality to install Bus ,Bus reservation ,show Bus ,delete Bus using file handling Concept*.

**Reference (link):** Shubham S. (2020). *A c++ Application with functionality to install Bus ,Bus reservation ,show Bus ,delete Bus using file handling Concept*. Retrieved from: [GitHub - shubmSingh/Bus-Reservation-System: A c++ Application with functionality to install Bus ,Bus reservation ,show Bus ,delete Bus using file handling Concept · GitHub](https://github.com/shubmSingh/Bus-Reservation-System) [Accessed 22 March 2026 ].

**What it does well (2-3 features that work effectively)**: 

The advantages of the program are that it has an account registration system and stores it in a specific file. Thanks to this, after the program is closed, you can run it again and log in using the registered username and password.

Also, the program creates and stores information not in one file, but in several, separating the data into different files, which makes it easier to read and store the data. 

In addition, the program is complete and not just a set of functions, as it includes registration, adding and deleting journeys, and other features.

**What it does poorly (at least 1 feature)**:

The program has not option to exit, which makes it infinity, the one choice to exit from the program enter invalid input. Also it is not clear what to input in some places.

**Key design ideas you could reuse (e.g., layout, navigation, input/output, program structure):**

The functions that would be reused in the project are login system and separate file handlings.

**Screenshot (showing the interface/output)**:

![a](C:\Users\user\Pictures\Screenshots\Снимок%20экрана%202026-03-22%20171227.png)

![b](C:\Users\user\Pictures\Screenshots\Снимок%20экрана%202026-03-22%20171322.png)

![a](C:\Users\user\Pictures\Screenshots\Снимок%20экрана%202026-03-22%20171333.png)

![c](C:\Users\user\Pictures\Screenshots\Снимок%20экрана%202026-03-22%20171415.png)

-------------------------------------------------------------------------

-----------------------------------------------------

### Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

![*](C:\Users\user\Pictures\Screenshots\Gantt%20Chart%202.png)

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries

------------------------------------------------------------------------------------------------------------------------------

 **21-03-2026**

On the first day of work I started to do the input output and process table, in the process of completing this table no difficulties were found, as I took an example from previous milestones and simply added a couple of functions. Then I made the purpose of the program with which there were also no problems, as an identical task was before. And finally a Gantt chart was made, where 3 weeks of the following implementation of this project were planned.

**22-030-2026**

On the last day before the deadline a class diagram was made, during the period of completing this task I faced a problem such as fatigue, as I was doing it late at night, understanding that there is little time left and it needs to be done, after I finished completing the class diagram I went to sleep, and when I woke up I continued completing the tasks and moved to the research, this part seemed the most difficult to me, as during the first half an hour of searching for a similar project nothing gave results, but in the end the project was still found. And in the end the flow chart task was completed, as the base was already set in the previous project, it only remained to add to it. I think that with the problems and stress that I experienced it would have been possible to deal with them by starting at least a day earlier, but unfortunately this did not happen.

------------------------------------------------------------------------------------------------------------------------------
