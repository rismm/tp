# Vimalapugazhan Purushothaman - Project Portfolio Page

## Project: SuperTracker
SuperTracker is a desktop app for managing a supermarket's inventory and expenditures, optimized for use via a Command Line Interface (CLI).

Given below are my contributions to the project.

- **Feature:** Delete Command
    - What it does: Allows the user to delete an item from the Inventory List.
    - Justification: Items in a supermarket may get sold out or expire and the manager would need to be able to remove the item from the inventory. 
    - Highlights: It checks whether there is an item in the inventory that matches the input.
    <br><br>
  
- **Feature:** Revenue Command
    - What it does: Allows supermarket managers to check their revenue over a period of time.
    - Justification: This function is necessary to see what is the cash inflow of the supermarket. Moreover, this
    function is necessary to calculate the profit of the supermarket
    - Highlights: Able to give different revenue reports for 4 different time frames; Today, specified day, specified range and total.
    <br><br>
  
- **Feature:** Profit Command
    - What it does: Allows supermarket managers to check their profit over a period of time.
    - Justification: Profit is a good indication of the performance of the supermarket. 
    As a supermarket manager, this function would be important in both keeping track at finances and gauging the performance of the business.
    - Highlights: Provides reports for the different time frames, similar to revenue command.
    <br><br>
  
- **Enhancement to existing features:** Expiry Date in New Command, List
    - What it does: Allow manager to add perishable items added with an expiry date.
    - Justification: Most items in a supermarket contains an expiry date and as a supermarket manager, tracking the expiry dates would be important for sorting stock and restocking.
    - Highlights: The inventory can be sorted by expiry date in list and the information is also used in other commands such as report.
    <br><br>
  
- **Enhancement to existing features:** Expiry Date in Update Command
    - What it does: Allows manager to change the expiry dates of items.
    - Justification: Expiry dates of items may need to be updated due to mistake in entry or if there was no expiry date initially added with the item.
    - Highlights: Able to remove and add expiry dates in addition to just changing the dates.
    <br><br>
  
- **Code contributed** [RepoSense link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=vimalapugazhan&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=vimalapugazhan&tabRepo=AY2324S2-CS2113-T13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
  <br><br>

- **Documentation**
  - [User Guide](https://ay2324s2-cs2113-t13-4.github.io/tp/UserGuide.html)
    - Added documentation for Delete, Revenue and Profit commands.
    - Updated documentation for New, List and update commands to handle expiry dates.
  - [Developer Guide:](https://ay2324s2-cs2113-t13-4.github.io/tp/DeveloperGuide.html)
    - Added documentation for Delete, Revenue and Profit Commands
    - Used PlantUML do make class and sequence diagrams for DeleteCommand, RevenueCommand and ProfitCommand.
  <br><br>
    
- **Contributions to team-based tasks:**
    - Teammate PR reviewed: [#254](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/254), [#188](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/188), [183](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/183).
    - Contributed to the JUnit tests, JavaDocs, User Guide and Developer Guide.
    - Helped give improvements and suggestions to my teammates. Resolved bugs immediately.
    - Created and assigned issues. Checked and merged pull requests in a timely manner.
