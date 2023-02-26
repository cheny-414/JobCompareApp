# Test Plan

**Author**: Puneeth, Yiliang, Feng, Alex

## 1 Testing Strategy

### 1.1 Overall strategy
Testing of the Job Comparison app will primarily be performed using a combination of white and black box testing, with additional unit level verification potentially provided by reviews and walkthroughs.
- Unit testing will be developed by class, with tests verifying intended functionality that is internal to said class. 
- Integration testing will focus on the compare job functionality and will assume jobs and settings are previously populated by the user. As the proposed system is relatively simple, this is the only integration test planned.
- System testing will cover the full system, emulating the user from first opening the app through successful job comparison. It will also include adjusting of the ComparisonSettings parameters prior to executing the comparison.
- Regression testing will use the full test suite defined for solution development which can be re-run and updated as additional features are requested and failures are resolved.

There will be significant overlap of integration testing and system testing based on the overall test strategy discussed here. As tests are developed, integration testing may be absorbed into system testing, with no subsystems remaining that warrant discrete integration testing.

### 1.2 Test Selection
For unit test, both black-box test and white-box test will be applied. 
- Black-box test is used to test whether each unit functions as expected and partition testing technique will be used to check whether the outputs are correct or whether the component can generate corresponding error messages when inputs do not meet the specified criteria. 
- White-box test is used to identify errors in the corresponding codes. Statement coverage technique will be used to examine all the statements in the code. 

For system test, black-box test will be applied to check whether the system meets the specified requirements. 
- Partition testing technique will be used to test whether the system generates correct results.
- State transition testing technique will be used to examine whether the system transit among pages as specified. 


### 1.3 Adequacy Criterion
All the model and controller classes will be tested using JUnit and the system/functional tests will be performed using UI testcases. The test cases should cover all requirements or functionalities of the system being tested including both positive and negative scenarios. Android code coverage report will be used to assess the quality of the test cases. 

### 1.4 Bug Tracking
GitHub projects will be used for bug tracking and managing enhancement requests. As this is a single user app, communication from the user will be direct to the development team with the Project Manager coordinating communication. Two projects have been created on the 6300Spring23Team057 repository for this purpose.

### 1.5 Technology
- JUnit/Mockito will be used for Unit testing
- Espresso will used for UI and System testing

## 2 Test Cases

*This section should be the core of this document. You should provide a table of test cases, one per row. For each test case, the table should provide its purpose, the steps necessary to perform the test, the expected result, the actual result (to be filled later), pass/fail information (to be filled later), and any additional information you think is relevant.*

### Unit Test Cases (JUnit)
| Test Case # | Purpose | Scope  | Inputs | Steps | Expected Result  | Actual result | Pass / Fail |
|-------------|---------|--------|--------|-------|------------------|---------------|-------------|
| 1           | sdokm   | asd    | asd    | asd   | asd              |               |             |\


### System Test Cases (UI)
| Test Case # | Purpose | Scope  | Inputs | Steps | Expected Result  | Actual result | Pass / Fail |
|-------------|---------|--------|--------|-------|------------------|---------------|-------------|
| 1           | sdokm   | asd    | asd    | asd   | asd              |               |             |\


- job offer - Puneeth
- compare job - Feng
- current job - Yiliang
- job compare settings - Puneeth
- app controller - Alex