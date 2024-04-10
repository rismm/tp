# Sim Jun Hong's Project Portfolio Page

## Project: SuperTracker

SuperTracker is a desktop app for managing a supermarket's inventory and expenditures,
optimized for use via a Command Line Interface (CLI).

Given below are my contributions to the project.

- **New Feature:** New Command
  - What it does: Allows users to add a new item to the inventory.
  - Justification: This is one of the core features of an inventory management system.
  - Highlights: Since this was one of the first commands implemented, 
  this involved me setting up the base code for the `Item` class
  and the `Inventory` class, as well as the general structure of the 
  methods used to parse each different type of command (such as `parseNewCommand`).


- **New Feature:** List Command
  - What it does: Allows users to list out all the items in the inventory.
  - Justification: Gives users the flexibility to view the list with their desired information 
  (name, quantity, price, expiry date) and sorted in the desired order (by name, quantity, price, expiry date)
  - Highlights: This command was challenging to implement as it took in many parameters.
  The presence and order of these parameters would affect how the list would be printed out.
  Please refer to the [list](https://ay2324s2-cs2113-t13-4.github.io/tp/UserGuide.html#list-all-items-list) section in the user guide for more details.


- **New Feature:** Add Command
  - What it does: Allows users to increase the quantity of an item in the inventory.
  - Justification: Useful if the user just received a new batch of items from suppliers,
  and wishes to update the quantity of the item without having to calculate the new total quantity of the item manually.
  - Highlights: I had to implement a special case of error checking as it was possible that the number of items added
  by the user would cause the new total quantity to overflow.


- **New Feature:** Remove Command
  - What it does: Allows users to decrease the quantity of an item in the inventory.
  - Justification: Useful if the user has just removed a bunch of items from the inventory (sold or delivered),
    and wishes to update the quantity of the item without having to calculate the new total quantity of the item manually.
  - Highlights: I implemented this command such that if the number of items to be removed 
  were to exceed the current quantity, it would simply set the new quantity to 0 instead of throwing an error.


- **Enhancement to existing features:** Buy and Sell Commands
  - What they do: These commands inherit from the add and remove commands, 
  with an additional functionality to create a new buy/sell transaction which will be stored in a transaction list.
  - Justification: The most common cases when items are added/removed from the inventory is when they are bought/sold.
  Adding this transaction recording functionality is useful because it allows users to change the quantity of items 
  and keep track of their transaction history using only 2 commands (buy/sell).
  - Highlights: I created a new `Transaction` class which inherited from the `Item` class 
  and a `TransactionList` class needed to store the transactions.


- **New Feature:** Clear Command
  - What it does: Clears all transactions before a specified date
  - Justification: This feature helps to reduce file size as the transactions will be stored on the hard disk.
  Transactions from a long time ago might no longer be important and can be cleared.
  - Highlights: I added a confirmation message as clearing transaction history is an irreversible operation 
  and this would help to ensure that the user would not clear by mistake.


- **Enhancement to existing features:** Add error checking for invalid inputs
  - What it does: Ensure that parameters entered are valid before commands are executed.
  - Justification: This was necessary to ensure that our application would not break if the user enters an invalid input.
  - Highlights: This was challenging as I had to run smoke-tests on our application to ensure that 
  most, if not all edge cases have been handled. I wrote many private methods such as `parseQuantity`, `parsePrice`, 
  `validateNonEmptyParam`, `validateItemExistsInInventory` etc. 
  that would throw a unique error in the case of invalid inputs.


- **Enhancement to existing features:** Contributed to JUnit tests for new, list, add, remove, clear commands
  - What it does: Ensure that the commands still work as expected when the codebase is modified.
  - Justification: Oftentimes changing one part of the code might break another part, 
  hence writing automated tests would help to streamline the testing process.
  - Highlights: Made use of methods not taught in the course, such as `@BeforeEach`, `@AfterEach`, 
  `ByteArrayInputStream` to simulate user input and `ByteArrayOutputStream` to test for console output.


- **Code contributed:** [RepoSense link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=awesomesjh&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)


- **Documentation:**
  - [User Guide](https://ay2324s2-cs2113-t13-4.github.io/tp/UserGuide.html)
    - Added documentation for new, list, add, remove, clear commands
    - Added notes about command format section
    - Added FAQ section
  - [Developer Guide](https://ay2324s2-cs2113-t13-4.github.io/tp/DeveloperGuide.html)
    - Added implementation details of new and list commands
    - Used PlantUML to create the UML class and sequence diagrams for the new and list commands


- **Contributions to team-based tasks:**
  - Refactored code in `Parser` as many of the commands were using the same error checking methods
  - Refactored code in `HelpCommandUi` to make it less repetitive and more scalable
  - Fixed UML diagrams in developer guide according to feedback from TA
  - Helped to create new issues and close completed issues on the issue tracker
  - Reviewed PRs and provided suggestions on fixing bugs, improving code quality etc.
  - Teammate PRs reviewed: [#75](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/75), [#104](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/104), [#212](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/212)