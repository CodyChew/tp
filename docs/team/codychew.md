---
layout: page
title: Cody's Project Portfolio Page
---

## Project: Police Investigation Virtual Organisational Tool 

PIVOT is a desktop application to assist the police investigators in keeping track of their investigations and relevant information. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to open documents from the application. (Pull request [\#155](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/155))
  * What it does: allows the user to open a file stored in their local computer directly from the program. 
  * Justification: This feature improves the product significantly there can be many files saved in the program, and the user
  can conveniently open relevant files while using the program.
  * Highlights: The main challenge was to store the references to work with the logic of the program as users are able 
  to directly manipulate files in their local folder from outside the program. As the program does not know the different 
  "states" of the files, there needs to be separate checks for validity of the reference as well as its existence when the 
  user opens the file.
  * Credits: 
    - The `Desktop` class allows us to open the file directly from the program.
    - `JUnit 5` Temporary Directory support allows me to test directory and file creation locally without interfering with 
  user program files. It also aids in the clean up of created files during unit testing.
    
* **New Feature**: Added `open case` and `return` functionality to the program (Pull requests [\#116](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/116), [\#121](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/121))
  * What it does: Changes the state of the program, enables users to access both the main panel as well as individual cases.
  * Justification: A user can execute different commands based on which interface or `state` the user is at.
  * Highlights: This command makes use of a global `StateManager` which tracks the state of the program. A single `StateManager`
  ensures that the `state` is accessible easily and the program has only one `state` at any instance.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=codychew&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Released `v1.3.trial` - `v1.3` on GitHub

* **Enhancements to existing features**:
  * Abstract out common Naming classes (Pull requests [\#82](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/82), [\#89](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/89))
  * Included `ReferenceStorage` to `StorageManager` to handle references storage on program initialization (Pull request [\#137](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/137))
  * Additional checks for non-positive `Index` to return more specific invalid messages to users. (Pull request [\#231](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/231))
  
* **Documentation**:
  * User Guide:
    * Added documentation for the features related to `documents` and `references`
    * Did cosmetic tweaks to existing documentation of features in the `case page` (Pull request [\#236](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/236))
  * Developer Guide:
    * Added implementation details for the `document` feature (Pull request [\#72](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/148))

* **Community**:
  * PRs reviewed (with non-trivial review comments): (Pull requests [\#81](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/81), [\#119](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/119))
  * Got help from a classmate when failing Github CI when testing the `document` feature (Issue [\#296](https://github.com/nus-cs2103-AY2021S1/forum/issues/296#issuecomment-704173027))

