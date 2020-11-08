---
layout: page
title: Ng Shuo Qi, Constance's Project Portfolio Page
---

## Project: PIVOT (Police Investigation Virtual Organisational Tool)

PIVOT is a desktop application to assist the police investigators in keeping track of their investigations and relevant information. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to archive and unarchive cases, as well as list the archived cases. (Pull Request [\#164](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/164))
  * What it does: Allows the user to archive cases in the Home section one at a time, which would be automatically shifted to the Archive section on the GUI.
  The archived cases can be unarchived using the unarchive command in the Archive section. They can easily switch between the two lists via `list case` and `list archive` commands.
  * Justification: This feature improves the product significantly as it allows users to better organise the cases on hand. 
  They can archive cases that they deem of lower priority, hence helping to de-clutter the application, only viewing the list they wish to at one time. 
  At the same time, it provides a convenient way to unarchive cases if they wish to. 
  * Highlights: In implementing this feature, the team considered various design alternatives. 
  This enhancement builds on the newly implemented StateManager class, as well as the ability to update the currently shown list with a predicate.

* **New Feature**: Added the ability to add a victim to a case and delete a victim. (Pull Request [\#129](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/129))
  * What it does: Allows users to add a victim with the relevant fields (name, sex, phone, email, address) into a specified case.
  Victims can also be deleted if they are wrongly added.
  * Justification: This features improves the product as it provides more depth to the product, allowing users to specify more details related to the case. 
  This improves the effectiveness of the product in helping users to keep track of the investigations and the related victims at a glance.
  
* **New Feature**: Developed CasePerson to contain more fields (Pull Request [\#153](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/153), [\#235](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/235))
  * What it does: Users can specify the name, sex, phone, email and address when adding a suspect, witness or victim to the case.
  Name, sex and phone are compulsory fields used to identify two different suspects/victims/witnesses, while other fields are optional.
  * Justification: This feature improves the product as it provides more depth to the product, allowing users to specify more details related to the person to be added to the case. 
  This gives more importance to the added CasePerson rather than just having a name, as the CasePersons could be contacted with the extra information provided. 
  Within each category, the compulsory fields identify the specific CasePerson to prevent duplicates.
  * Highlights: This enhancement affects existing commands and commands to be added in the future, such as the `add` and `edit` commands for CasePersons.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=constancensq&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Code Quality and Standardisation:
    * Increased defensiveness of code: added assertion statements 
    * Standardised message usages for commands
    * Abstracted out common code to a method
  * Managed the recording of demo videos for `v1.2` and `v1.3` 
  
* **Enhancements to existing features**:
  * Edited `list` command to `list case` by creating a parser for `list` commands to allow for extensibility of code. (Pull Requests [\#83](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/83) and [\#119](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/119))
  * Augmented the `find` command to search through all details of the case and to check for the keyword as a substring instead of by word (Pull Request [\#156](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/156), [\#228](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/228))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `archive`, `unarchive` (Pull Request [\#170](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/170/files)) and all `list` features (Pull Request [\#179](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/179/files))
    * Tweaked the existing documentation of feature `find` to describe the augmented behavior (Pull Request [\#170](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/170/files))
    * Minor tweaks to documentation of features for `delete` (Pull Request [\#190](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/190/files))
    
  * Developer Guide:
    * Modified the UML Diagram for Model to show `Case` and `CasePerson` fields (Pull Request [\#147](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/147))
    * Modified the UML Diagram for Storage to fit PIVOT (Pull Request [\#147](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/147))

* **Community**:
  * Reported bugs and suggestions for other teams in the class during [PE-Dry Run](https://github.com/constancensq/ped/issues)
