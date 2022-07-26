# Class Scheduler
Class Scheduler is an Android mobile app that allows users to keep track of classes, upcoming exams and mentor contact information.

## How It's Made

  The architecture behind the mobile application includes room persistence library which is used to set up an SQLite database.  The mobile application also contains entities that represent tables in the database.  Data access objects are included in the architecture to allow CRUD functionality.  There is also a class that extends RoomDatabase, that when called creates an instance of the database.  View models update and manage the data for activities.  There are adaptors handle repetitive views such as recycler views.  The app uses the Android X support library which is a software capability that allows new features to work in older APIs.  This will help minimize such software limitations.  One limitation of the app is the lack of fragments to allow for flexibility of UI.  The app was developed under Android 10(API 29).  This app was tested on a Samsung Galaxy S9+ Android 9(API 28) device and on a Nexus 6(API 27) emulator.   
## Optimizations
  What I would do differently if I do this project again is that I would use fragments to set up different screens instead of an activity for each screen.  Using fragments would also take away the challenge of passing information between activities since an activity could be reused. I would also display each terms courses inside a recyclerView on the term details screen to make it more obvious that the courses are assigned to that term. 

## Challenges

  While developing the app, I faced certain challenges.  The first and probably the hardest challenge was getting started.  All the concepts were new, 
  and I had to face a learning curve in the beginning.  A second challenge I encountered was setting up more than one notification at a time.  
  If I tried to create two notifications at a time, only the first notification would work.  A third challenge would be setting up the Room Database.  
  I had difficulties connecting the view model to the repository and querying the database from the repository through the data access objects.  
  It was also a challenge to set up the user entered date to accept only the MM/dd/yyyy format.
## Screenshots
![Screenshot (1)](https://user-images.githubusercontent.com/41929486/181094823-622a6409-234e-42de-8ed6-e1e9e1cb50a0.png)
![Screenshot (2)](https://user-images.githubusercontent.com/41929486/181095123-23b69573-31ee-4483-99b8-2586f892e7b0.png)<br>
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181095541-470e1c2a-70bb-4cea-bfee-0e27fa214b13.png)<br>
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096019-3a8a3ee8-03c8-4415-8c08-adf174565f6e.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096129-6956e3ac-1af1-4d3a-b451-f7d7b3ccee3f.png)<br>
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096401-d22f82de-1986-4c0c-9153-13637ee9cdd4.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096466-be0970a0-001d-436f-8eef-deb113e42103.png)<br>
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096776-4767966d-e3d7-427b-856a-246f08e73a10.png)<br>
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096888-41a554de-a4d1-4589-8d9b-77c195ce6853.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181096981-c57a72cf-f76e-4da9-99e9-c6fc3b808605.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181097086-1499807f-a9d1-4a0b-a988-f674d1207994.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181097177-33200a22-14dd-4118-8939-56dc6ba00110.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181097290-50de4f2a-b46f-4053-a177-decfe5b630ac.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181097375-162386b1-8b6e-45ce-a963-c42b5aad3c3f.png)
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181097467-701ae661-6d01-4eff-bada-f63815fda4ce.png)<br>
![Screenshot (22)](https://user-images.githubusercontent.com/41929486/181097598-8c0f6632-7653-43c8-8d2a-9029e261afe8.png)
