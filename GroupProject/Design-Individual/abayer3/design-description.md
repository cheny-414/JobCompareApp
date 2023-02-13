# Design Description Document
### Requirement 1 
1. User is presented with the main menu
   1. Visual of presentation to the user is entirely withing GUI, though a MainMenu class is used in the implementation
2. Main menu allows the user to
   1. Enter or edit current job details
      1. accomplished via the MainMenu.updateCurrentJob() method which uses JobList.createJob() and stores the result in the JobList.currentJob attribute or JobList.editJob(JobList.currentJob) to edit an existing current job
   2. Enter job offers
      1. initiated in MainMenu.enterJobOffer() which uses JobList.createJob() to create a new Job instance
   3. Adjust the comparison settings
      1. MainMenu.adjustComparisonSettings() is used to display the existing settings stored in ComparisonSettings which has methods for saving changes or cancelling the edit
   4. Compare job offers
      1. As described in requirement 5, comparing job offers starts with displaying a ranked list of jobs. This is accomplished via the JobList.displayJobList() method which displays the jobs in the job list in ranked order.
      2. If joblist is empty, this option would be disabled. This logic would be handled in MainMenu.jobList().
### Requirement 2
1. When entering current job details
   1. Listed job details are all stored as attributes of the Job class after being input via the GUI
   2. Saving or cancelling job creation or edit is done via methods of the Job class.
      1. If the Job is saved via the Job.save() method, the JobList class will add the new Job to the jobList attribute and set JobList.currentJob as a reference to the new object, or update the existing JobList.currentJob with the new information
      2. if the Job is canceled via the Job.cancel() method, the JobList class will take no action meaning the new Job is not added to the jobList or is not updated in the jobList
      3. Returning to the main menu is handled within JobList.createJob() or JobList.editJob()
### Requirement 3
1. When entering job offers
   1. Entry of a new job offer is handled in the same way as entering a new current job (JobList.createJob()) except for referencing the new job in JobList.currentJob
   2. Saving will cause JobList.createJob() to add the new job to the JobList.jobList linked list, cancelling will cause no action
   3. Options to enter another offer, return to the main menu, or compare offer with current job will all be handled within JobList.createJob
      1. Enter another offer will pass control back to MainMenu.enterJobOffer() if the correct return is received from the user input while saving the job which will then re-call JobList.createJob()
      2. Return to the main menu will be handled through JobList.createJob() completing with no return
      3. Comparing to current job will be handled by JobList.createJob() calling JobList.compareJobs with the current job and the newly created job
### Requirement 4
1. Adjusting the comparison settings
   1. Settings are displayed and edited by the user in the GUI, with new values being entered by the ComparisonSettings.Save() method into the ComparisonSettings attributes
   2. If no weights are assigned, all factors are considered equal
      1. Handled by setting the default values of the ComparisonSettings attributes
### Requirement 5
1. Comparing offers
   1. Displaying list of job offers is handled by the JobList.displayJobList() method, with the Title and Company and ranking computed at time of need using ComparisonSettings, Job detail, and the provided calculation
   2. Selection of jobs to compare is handled in JobList.jobSelect()
   3. Comparison is done in JobList.CompareJobs(job1: Job, job2 : Job)
   4. Running an additional comparison is done via ComparisonTable.compareAgain() which returns to the job list 
   5. Returning to the main menu is done with ComparisonTable.cancel()
### Requirement 6
1. Job score computation
   1. This is done in the process of building the job list in JobList.displayJobList()
### Requirement 7
1. User interface must be intuitive and responsive.
   1. This is not represented in my design, as it will be handled entirely within the GUI implementation.