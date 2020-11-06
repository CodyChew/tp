---
layout: page
title: Wong Wen Wei Esmanda's Project Portfolio Page
---

## Project: PIVOT (Police Investigation Virtual Organisational Tool)

PIVOT is a desktop application to assist the police investigators in keeping track of their investigations and relevant information. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo and redo previous commands. (Pull Request [\#157](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/157))
  * What it does: Allows the user to undo all previous commands that make changes to the PIVOT data one at a time.
  * Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the quality of the product significantly as it gives users a better user experience by providing a convenient way
  * to easily rectify any mistakes in commands.
  * Highlights: This enhancement affects existing commands and commands to be added in the future. The implementation was challenging as it required
  * changes to existing commands. It also required consideration of the behaviour of undo and redo when a case was open, as undo/redo may change the
  * list of cases in the main page, hence affecting the index of the case that was currently open.
  * Credits: Referenced the proposed implementation for undo/redo in the AB3 developer guide.

* **New Feature**: Added the ability to add and delete a suspect from a case. (Pull Request [\#130](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/130))
  * What it does: Allows the user to add and delete a suspect into a specified case one at a time.
  * Justification: This feature is highly pertinent as our application aims to help users better manage relevant information about investigations.
  * With the feature, users are able to keep track of the list of suspects of a case, with all its relevant details.
  
* **New Feature**: Added the ability to edit the fields of suspects/witnesses/victims in a specified case. (Pull Request [\#167](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/167))
  * What it does: Allows the user to be able to edit multiple fields of a suspect/witness/victim at one time.
  * Justification: This feature improves the product greatly as it provides a convenient way of rectifying a mistake made in one of the fields,
  * without having delete the person and create a new person by re-entering all the existing fields. 
  * Highlights: The implementation of this enhancement was challenging as there was much common code in implementing the edit command for the persons, thus
  * requiring rigorous brainstorming in reducing the duplicated code.
  * Credits: Took inspiration from AB3 implementation of the edit command to abstract out the common code.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=esmanda3w&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Took down minutes for meeting discussions on what to do for the next iteration
  * Facilitated the allocation of work among group members
  
* **Enhancements to existing features**:
  * Refactored the code base from working on `Person` to `Case` (Pull Requests [\#105](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/105))
  * Refactored the code base from working on `AddressBook` to `Pivot` (Pull Requests [\#141](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/141))
  * Abstract out AddCommand to be an abstract parent class for future add commands (Pull Requests [\#118](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/118))
  * Abstracted out common code in test cases (Pull Requests [\#229](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/229))
  * Increase defensiveness of code by returning defensive copies of `Case` fields (Pull Requests [\#239](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/239))
  
* **Documentation**:
  * User Guide:
    * Added documentation for the features `undo`, `redo` (Pull Request [\#171](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/171))
    * Updated documentation for the `edit` feature to include editing of the fields of suspects/witnesses/victims in a specified case (Pull Request [\#171](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/171))
  * Developer Guide:
    * Modified proposed implementation for Undo/Redo commands to be aligned with current implementation (Pull Request [\#171](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/171))

* **Community**:
  * Reported bugs and suggestions for other teams in the class during [PE-Dry Run](https://github.com/esmanda3w/ped/issues)
