DESIGN DESCRIPTION


Requirements
1.	When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet ).

    I have created a “user” class as entry point.

2.	When choosing to enter current job details, a user will:
  a.	Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consist of:
    i.	Title
    ii.	Company
    iii.	Location (entered as city and state)
    iv.	Cost of living in the location (expressed as an index)
    v.	Yearly salary
    vi.	Yearly bonus
    vii.	Restricted Stock Unit Award (expressed as a lump sum vested over 4 years)
    viii.	Relocation stipend (A single value from $0 to $25,000)
    ix.	Personal Choice Holidays (A single overall number of days from 0 to 20)
  b.	Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

  I have created a “Job” class with the attributes described above and an additional isCurrentJob boolean to identify the current job. The user will "compare" the jobs.

3.	When choosing to enter job offers, a user will:
  a.	Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
  b.	Be able to either save the job offer details or cancel.
  c.	Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

  The job offer will use the same “Job class” as all the attributes are the same. The rest of this requirement looks like an implementation of the GUI.

4.	When adjusting the comparison settings, the user can assign integer weights to:
  a.	Yearly salary
  b.	Yearly bonus
  c.	Restricted Stock Unit Award
  d.	Relocation stipend
  e.	Personal Choice Holidays
  If no weights are assigned, all factors are considered equal.

  I created a “Weights” class to store each integer weight that the user assigns (an operation).

5.	When choosing to compare job offers, a user will:
  a.	Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
  b.	Select two jobs to compare and trigger the comparison.
  c.	Be shown a table comparing the two jobs, displaying, for each job:
    i.	Title
    ii.	Company
    iii.	Location
    iv.	Yearly salary adjusted for cost of living
    v.	Yearly bonus adjusted for cost of living
    vi.	Restricted Stock Unit Award
    vii.	Relocation stipend
    viii.	Personal Choice Holidays
  d.	Be offered to perform another comparison or go back to the main menu.

  A user will be able to “compare” the offers, an operation between the User class and the Job class.

6.	When ranking jobs, a job’s score is computed as the weighted sum of:

  AYS + AYB + (RSUA / 4) + RELO + (PCH * AYS / 260)

  where:
  AYS = yearly salary adjusted for cost of living
  AYB = yearly bonus adjusted for cost of living
  RSU = restricted stock unit award
  RELO = relocation stipend
  PCH = personal choice holidays

  For example, if the weights are 2 for the yearly salary, 2 for relocation stipend and 1 for all other factors, the score would be computed as:

  2/7 * AYS + 1/7 * AYB + 1/7 * (RSUA / 4) + 2/7 * RELO + 1/7 * (PCH * AYS / 260)

  The specific calculation will be reflected as a method in the “Job” class that is called to calculate the score.

7.	The user interface must be intuitive and responsive.

  The requirement is a description of the GUI

8.	For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
