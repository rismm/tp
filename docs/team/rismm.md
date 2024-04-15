# Sim Jing Jie Ryan's Project Portfolio Page

## Project: SuperTracker

SuperTracker is a desktop app for managing a supermarket's inventory and expenditures,
optimized for use via a Command Line Interface (CLI).

Given below are my contributions to the project.

- **Feature:** Base functionality of the input parser
  - Implemented the use of a regex format for matching user inputs.
  - Justification: Streamlines the process of obtaining the user parameters and checking for valid/invalid inputs entered by the user.
  The base implementation can be used to simplify the parsing of inputs for any future commands that are to be implemented in the future.
  - Highlights: The implementation allows user to enter valid input parameters in any order they wish. This introduced greater complexity and challenge in
  the implementation in creating a format and regex that can accomplish this feature.


- **Feature:** File saving and loading
  - Justification: This feature enables the program to save item and transaction data that was entered in the program to a local hard disk,
  and loads the saved data back into the program when the program is started up again.
  - Highlights: Corrupting the save files with invalid formats would not crash the program, and corrupted lines will be ignored.


- **Feature:** Error handling
  - Added base implementation of error handling and error output messages
  - Justification: Error handling is important to ensure that the program does not crash unexpectedly. The format that I have implemented
  is used widely to catch errors in user inputs.


- **Code contributed:** [RepoSense link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=rismm&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)


- **Documentation:**
  - [User Guide:](https://ay2324s2-cs2113-t13-4.github.io/tp/UserGuide.html)
    - Added documentation for the features of saving, loading and editing file data
  - [Developer Guide:](https://ay2324s2-cs2113-t13-4.github.io/tp/DeveloperGuide.html)
    - Added implementation details of input parsing (the use of regex) and storage (data file saving and loading)
    - Used PlantUML to create the UML class and sequence diagrams for input parsing and the storage implementation
    - Added the architecture diagram its descriptions of the components
    - Filled in the user stories in the appendix


- **Contributions to team-based tasks:**
  - Teammate PRs reviewed: [#40](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/40), [#70](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/70), [#73](https://github.com/AY2324S2-CS2113-T13-4/tp/pull/73)
  - Contributed to JUnit tests for parser and storage, also helped fix JUnit tests for the update command
  - Helped detect bugs and provided suggestions to teammates on fixes/improvements that they can make
  - Created and assigned issues to teammates
