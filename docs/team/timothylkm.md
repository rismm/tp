# Timothy Lau Kah Ming's Project Portfolio Page

## Project: SuperTracker

SuperTracker is a desktop app for managing a supermarket's inventory and expenditures,
optimized for use via a Command Line Interface (CLI).

Given below are my contributions to the project.

- **Feature:** Rename Command
  - What it does: Allows users to rename their item of choice.
  - Justification: A supermarket manager can make mistakes when adding items
    and the app should provide a convenient way to rectify them, instead of manually deleting the item from the inventory
    and adding it back in with a new name.
  - Highlights: Before creating a new rename command, the parser helps to validate the users inputs by checking if the 
  original item name exists in the inventory and if the inventory has an identical item with the new name.


- **Feature:** Find Command
  - What it does: Allow users to quickly find an item of their choice.
  - Justification: A supermarket should be able to quick search an item off of it's name.
  - Highlights: A Find command would be able to return all items that contain the input word.


- **Feature:** Help Command
  - What it does: Allows supermarket managers to refer to a quick help sheet if they happen to forget the parameters.
  - Justification: The UG may be long and tedious to look up for parameters. With the use of the help command, it is 
  easier to identify parameters required.
  - Highlights: Instead of throwing all possible inputs that exists in SuperTracker, the Help command first returns a
  summarized list of functions SuperTracker has which the user can then find out more about a particular functions'
  parameters.


- **Code contributed:** [RepoSense link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=TimothyLKM&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)


- **Documentation:**
  - [User Guide](https://ay2324s2-cs2113-t13-4.github.io/tp/UserGuide.html)
    - Added documentation for Rename, Find and Help command
  - [Developer Guide:](https://ay2324s2-cs2113-t13-4.github.io/tp/DeveloperGuide.html)
    - Added implementation details of Find command
    - Used PlantUML to create the UML class and sequence diagrams for Find command


- **Contributions to team-based tasks:**
  - Teammate PRs reviewed: [#32](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/32), [#34](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/34), [#80](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/80)
  - Contributed to JUnit tests for Find Command.
  - Helped give improvements and suggestions to my teammates. Resolved bugs immediately.
