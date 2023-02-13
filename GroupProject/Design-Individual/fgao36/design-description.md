#Requirement 1:
1. When the app is started, the user is presented with the main menu, which allows the
user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison
settings, or (4) compare job offers (disabled if no job offers were entered yet1).


To realize this, 'UserMainMenu' is added as the entry point to connect and initiate all necessary components. The attributes contain simple user information and system components. Methods would be called if corresponding button is clicked from UI. Details of each action are discussed in the corresponding requirements below. 

#Requirement 2:
2. When choosing to enter current job details, a user will:
a. Be shown a user interface to enter (if it is the first time) or edit all the details of
their current job, which consist of:
i. Title
ii. Company
iii. Location (entered as city and state)
iv. Cost of living in the location (expressed as an index)
v. Yearly salary
vi. Yearly bonus
vii. Restricted Stock Unit Award (expressed as a lump sum vested over 4
years)
viii. Relocation stipend (A single value from $0 to $25,000)
ix. Personal Choice Holidays (A single overall number of days from 0 to 20)
b. Be able to either save the job details or cancel and exit without saving, returning
in both cases to the main menu.

To realize this requirement, I added 'CurrentJobEditor' for the user to provide/edit the details of the current job. The user can choose to save the edits by method save() or cancel by method cancel(). 'Job' is added to store all required details of a job with corresponding data type. An additional attribute 'currentJob' is set to true as an indicator for current job. An instance of 'Job' will be created if user saves their edits. Method returnMainMenu() will then be called automatically to return to main menu after both actions. 


#Requirement 3:
3. When choosing to enter job offers, a user will:
a. Be shown a user interface to enter all the details of the offer, which are the same
ones listed above for the current job.
b. Be able to either save the job offer details or cancel.
c. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the
offer (if they saved it) with the current job details (if present).

To realize this requirement, I added 'JobOfferEditor' for the user to provide the details of job offers. The user can choose to save the edits by method save() or cancel by method cancel(). After saving/discarding the current edits, user can choose the next actions: 
1. returnMainMenu() to return to the main page; 
2. addAnotherJobOffer() to edit details for another job offer; 
3.compareToCurrentJob(): to compare the job offer that user just saved to the current job, if current job has already been provided. 
An instance of 'Job' will be created if user saves their edits. Attribute 'currentJob' is set to false. 

#Requirement 4:
4. When adjusting the comparison settings, the user can assign integer weights to:
a. Yearly salary
b. Yearly bonus
c. Restricted Stock Unit Award
d. Relocation stipend
e. Personal Choice Holidays
If no weights are assigned, all factors are considered equal.

To realize this requirement, I added the class 'JobRanker', in which the attributes store the customized weights for the five items. They will be set to equal by default. It also calculates the weighted score and yields a ranking for all jobs saved (see Requirement 6).


#Requirement 5:
5. When choosing to compare job offers, a user will:
a. Be shown a list of job offers, displayed as Title and Company, ranked from best
to worst (see below for details), and including the current job (if present), clearly
indicated.
b. Select two jobs to compare and trigger the comparison.
c. Be shown a table comparing the two jobs, displaying, for each job:
i. Title
ii. Company
iii. Location
iv. Yearly salary adjusted for cost of living
v. Yearly bonus adjusted for cost of living
vi. Restricted Stock Unit Award
vii. Relocation stipend
viii. Personal Choice Holidays
d. Be offered to perform another comparison or go back to the main menu.

To realize this requirement, I added 'CompareJobs' to perform all the actions:
a. To show the ranked job list, the method displayRankedJobList() uses 'JobRanker' to get the ranked list and then display the required information to UI.
b. The action of 'selecting two jobs' is not represented in the design. It will be handled through GUI implementation. When user choose to compare two offers, method compareTwoOffers() will be called to pull required information for the selected jobs. 
c. The returned results from compareTwoOffers() will be displayed through GUI implementation. 
d. The user can choose to return to main page by method returnMainMenu() or compare another two jobs by compareOtherJobs(). compareOtherJobs() will trigger displayRankedJobList() automatically for the user to start over the comparison. 


#Requirement 6:
6. When ranking jobs, a job?s score is computed as the weighted sum of:
AYS + AYB + (RSUA / 4) + RELO + (PCH * AYS / 260)
where:
AYS = yearly salary adjusted for cost of living
AYB = yearly bonus adjusted for cost of living
RSU = restricted stock unit award
RELO = relocation stipend
PCH = personal choice holidays
For example, if the weights are 2 for the yearly salary, 2 for relocation stipend and 1 for
all other factors, the score would be computed as:
2/7 * AYS + 1/7 * AYB + 1/7 * (RSUA / 4) + 2/7 * RELO + 1/7 * (PCH * AYS / 260)
To realize this requirement, I added method in ?JobRanker? to do the calculation using the logic provided. Method calculateJobRankings() will calculate the weighted score for each job using the job information in jobList and weights. It returns to the ranked list of jobs with required information. It uses the class ?Job? to obtain information. 


#Requirement 7:
7. The user interface must be intuitive and responsive.
This is not represented in my design, as it will be handled entirely within the GUI implementation.


#Requirement 8:
8. For simplicity, you may assume there is a single system running the app (no
communication or saving between devices is necessary).
This is not represented in my design, as it will be handled entirely within the GUI implementation.
