# Tay Wen Duan David Project Portfolio Page

## Project: SuperTracker

SuperTracker is a desktop app for managing a supermarket's inventory and expenditures,
optimized for use via a Command Line Interface (CLI).

Given below are my contributions to the project.

- **Feature:** Update Command
  - What it does: Allows users to update the price, quantity and/or expiry date.
  - Justification: A supermarket manager can make mistakes when adding items
  and the app should provide a convenient way to rectify them, instead of manually deleting the item from the inventory
  and adding it back in with a new price, quantity and/or expiry date.
  - Highlights: Before creating a new update command, the parser helps to validate the users inputs by checking if the 
  numbers provided are negative or over the integer limit and whether the expiry date follows the correct date format.

- **Feature:** Report Command
  - What it does: Allows supermarket managers to see what items are low on stock and are close to expiry.
  - Justification: A supermarket manager may want to see what items are low on stock or close to expiry, so that they 
  may make an order to replace them.
  - Highlights: Report command handles two different types of reports with only 1 needing the optional parameter and 
  sorts the report based on quantity or expiry date for low stock and expiry report respectively.

- **Feature:** Expenditure Command
  - What it does: Allows supermarket managers to check their expenditures over a period of time.
  - Justification: This function is necessary to see what is the cash outflow of the supermarket. Moreover, this 
  function is necessary to calculate the profit of the supermarket
  - Highlights: The expenditure command unifies what the revenue commands uses to reduce the amount of repeated code.


- **Code contributed** [RepoSense link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=dtaywd&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)


- **Documentation**
  - [User Guide](https://ay2324s2-cs2113-t13-4.github.io/tp/UserGuide.html)
    - Added documentation for update, report and expenditure command
  - [Developer Guide:](https://ay2324s2-cs2113-t13-4.github.io/tp/DeveloperGuide.html)
    - Added implementation details of update command
    - Used PlantUML to create the UML class and sequence diagrams for update command

- **Contributions to team-based tasks:**
  - Teammate PR reviewed: [40](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/85), [71](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/71), [85](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/85) 
  - Contributed to the JUnit tests for update and report
  - Helped suggest improvements