﻿# CourseSurfer
An online course aggregation application which brings together all the online courses and MOOCs into a single platform. 
The application has a massive web scraped database with over 10,000 courses from more than 25 online course providers. 
# Behind the scene(Database creation and optimization)
The database is maintained on firebase. The database is made in a way using a machine learning model so that the data from all the various websites can be generalised and the data can be uniform. Categorization and optimization of the database is done using ML model.
# How searching for a course works 
There is a machine learning model on the amazon web services which process the user's request and returns an array of categories within which the course you have searched for belongs to. 
The categories returned by the above queries are the ones searched and the results are displayed on the user's device. 
