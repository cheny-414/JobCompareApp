# **Requirement  1**
When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

**The entry point of the application is realized by creating a singleton instance of ApplicationController class . All the other elements in the main screen will be handled entirely within the GUI implementation.**

# **Requirement  2**
When choosing to enter current job details, a user will be shown a user interface to enter (if it is the first time) or edit all the details of their current job

**The details of current job in the UI is realized using an object of JobEntity class which is fetched using getCurrentJob() method of the ApplicationController class. If there is no current job yet, then a new JobEntity object is created and added to the ApplicationController class using addJob() of the ApplicationController class. On clicking "save/cancel" the job objects are either saved or deleted from the database and the ApplicationController class. All the methods/classes involving CRUD operations are not shown**

# **Requirement  3**
1.  When choosing to enter job offers, a user will be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job

**The details of  a new job offer in the UI is realized using an object of JobEntity class. All the information is captured in the JobEntity object and added to ApplicationController class using addJob() of the ApplicationController class. On clicking "another offer" the current job offer object is saved in the database and a new screen is shown to add another offer. On clicking "cancel" the current job object is deleted from the database and the ApplicationController class using removeJob() method. On clicking "compare with current job", a JobComparator object will be created and job offer and current job objects along with object of JobCompareSettings object is passed for comparison using a compareJobs() method in the JobComparator class which holds the information about the comparison using the objects of JobCompareEntity class**

# **Requirement  4**

Adjusting the comparison settings for job offer comparison

**Comparison settings is realized using an object of JobCompareSettings class. This contains all the relevant fields with weights for comparison. On clicking "save/cancel" the settings object is either saved or deleted from the database and the ApplicationController class.  All the fields are initialized with default values of 1 to realize equal weights requirement. All the methods/classes involving CRUD operations are not shown**

# **Requirement  5**
1.  When choosing to compare job offers, a user will be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.

**This is realized by computing job score using the formula and weights from JobSettings class and storing it in the variable "jobScore" of each and every object of the JobEntity class. This is realized by the method computeJobScores() and sorted using sortJobsByScore() in the ApplicationController class and shown in the UI**

2. Be shown a table comparing the two jobs, displaying, for each jobs details

**On clicking "compare with jobs", a JobComparator object will be created either with two job offers or with a current job and a job offer objects along with object of JobCompareSettings object is passed for comparison using a compareJobs() method in the JobComparator class. This method returns an object of JobCompareResult class which holds the information about the comparison using the objects of JobCompareEntity class.**
