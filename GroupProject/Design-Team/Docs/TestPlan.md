# Test Plan

*This is the template for your test plan. The parts in italics are concise explanations of what should go in the corresponding sections and should not appear in the final document.*

**Author**: \<person or team name\>

## 1 Testing Strategy

### 1.1 Overall strategy

*This section should provide details about your unit-, integration-, system-, and regression-testing strategies. In particular, it should discuss which activities you will perform as part of your testing process, and who will perform such activities.*
-
- Alex
- 
- unit test strategy
- system test str ategy
- integration test not needed ...
- regression testing not needed ...

Testing of the Job Comparison app will primarily be performed using a combination of white and black box testing, with additional unit level verification potentially provided by reviews and walkthroughs.
- Unit testing will be developed by class, with tests verifying intended functionality that is internal to said class. 
- Integration testing will focus on the compare job functionality and will assume jobs and settings are previously populated by the user. As the proposed system is relatively simple, this is the only integration test planned.
- System testing will cover the full system, emulating the user from first opening the app through successful job comparison. It will also include adjusting of the ComparisonSettings parameters prior to executing the comparison.
- Regression testing will use the full test suite defined for solution development which can be re-run and updated as additional features are requested and failures are resolved.

There will be significant overlap of integration testing and system testing based on the overall test strategy discussed here. As tests are developed, integration testing may be absorbed into system testing, with no subsystems remaining that warrant discrete integration testing.

### 1.2 Test Selection

*Here you should discuss how you are going to select your test cases, that is, which black-box and/or white-box techniques you will use. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*
- 
- Feng
- 
- test selection methods
- unit: white and black box
- system: black box
### 1.3 Adequacy Criterion

*Define how you are going to assess the quality of your test cases. Typically, this involves some form of functional or structural coverage. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*
- 
- Puneeth
- 
- code coverage of test cases

All the model and controller classes will be test using Junits and the system/functional tests will be performed using UI testcases. The est cases should cover all of the requirements or functionalities of the system being tested including both positive and negative scenarios. Android code coverage report will be used to assess the quality of your 

### 1.4 Bug Tracking

*Describe how bugs and enhancement requests will be tracked.*

GitHub projects will be used for bug tracking and managing enhancement requests. As this is a single user app, communication from the user will be direct to the development team with the Project Manager coordinating communication. Two projects have been created on the 6300Spring23Team057 repository for this purpose.

### 1.5 Technology

*Describe any testing technology you intend to use or build (e.g., JUnit, Selenium).*
- Junits/Mockito will be used for Unit testing
- Espresso will used for UI and System testing

## 2 Test Cases

*This section should be the core of this document. You should provide a table of test cases, one per row. For each test case, the table should provide its purpose, the steps necessary to perform the test, the expected result, the actual result (to be filled later), pass/fail information (to be filled later), and any additional information you think is relevant.*

### Unit Test Cases (JUnit)
| Test Case # | Purpose | Scope  | Inputs | Steps | Expected Result  | Actual result | Pass / Fail |
|-------------|---------|--------|--------|-------|------------------|---------------|-------------|
| 1           | sdokm   | asd    | asd    | asd   | asd              |               |             |


### System Test Cases (UI)
| Test Case # | Purpose | Scope  | Inputs | Steps | Expected Result  | Actual result | Pass / Fail |
|-------------|---------|--------|--------|-------|------------------|---------------|-------------|
| 1           | sdokm   | asd    | asd    | asd   | asd              |               |             |

- job offer - Puneeth
- compare job - Feng
- current job - Yiliang
- job compare settings - Puneeth
- app controller - Alex