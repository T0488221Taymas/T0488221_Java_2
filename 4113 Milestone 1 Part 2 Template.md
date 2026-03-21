# IY4113 Milestone 1 Part 2

| Assessment Details | Please Complete All Details |
| ------------------ | --------------------------- |
| Group              | A                           |
| Module Title       | IY4113                      |
| Assessment Type    |                             |
| Module Tutor Name  | Jonathon Shore              |
| Student ID Number  | P488221                     |
| Date of Submission |                             |
| Word Count         |                             |
| GitHub Link        |                             |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.

------------------------------------------------------------------------------------------------------------------------------

### Purpose of the Program

------------------------------------------------------------------------------------------------------------------------------

*Add purpose of the program, identify core program functionality and and system contraints.*

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

- *Add **images** for the design of your algorithm. Choose either Flowchart or JSP diagrams to demonstrate the functional elements of the algorithm. There should be multiple images for this part as you are decomposing the problem into smaller elements.*

- *Include a class diagram to demonstrate the class structure of the proposed program design.

------------------------------------------------------------------------------------------------------------------------------

### Research (minimum of 1 required, preferrebly 2)

---

*Research existing programs that solve a similar problem. The program does not have to be written in java or object orientated in nature - just solve a similar type of problem.*

*Use the strucutre below to capture your evidence:*

------------------------------------------------------------------------------------------------------------------------------Name of program:

Reference (link):

What it does well (2-3 features that work effectively):

What it does poorly (at least 1 feature):

Key design ideas you could reuse (e.g., layout, navigation, input/output, program structure):

Screenshot (showing the interface/output):

------------------------------------------------------------------------------------------------------------------------------

### Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

*Add Gantt Chart (it maybe easier to create chart in Excel and paste as an image!)*

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries

------------------------------------------------------------------------------------------------------------------------------

*Add diary entries here detailing what you have done, wny you have done it, and any problems encountered.*

------------------------------------------------------------------------------------------------------------------------------
