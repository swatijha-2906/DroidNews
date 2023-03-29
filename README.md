#DroidNews

## About-

This is a news application that displays news related to Android to keep you informed on the topic.
Additionally you can select filters to view news on health, technology, food, science, or general news.

This app is built on offline-first-architecture. It uses Room database and a Repository to create an offline cache.
In addition, it also uses WorkManager for daily background data sync so that the users are updated on latest news even in offline mode.

## Screenshots

![Screenshot 1](/Screenshots/pic_1.jpg) ![Screenshot 2](/Screenshots/pic_2.jpg) ![Screenshot 3](/Screenshots/pic_3.jpg) 

## How to run this project-

1. Clone this repository in your local environment.
2. Open the application in Android Studio.
3. Replace the variable 'API_Key' in the code using your own API Key created in [NewsApi](https://newsapi.org) website
3. Build and run the app connecting to physical device( or enable internet in the emulator)


## Insights-

This app demonstrates the following techniques:
* MVVM architecture
* Retrofit to make api calls to an HTTP web service
* Moshi which handles the deserialization of the returned JSON to Kotlin data objects
* Glide to load and cache images by URL
* RecyclerView to display news image and title
* Custom (Chrome) Tabs to browse internet within the application to read full news article
* Offline-first-architecture using Room database and Repository
* WorkManager for daily background data sync
* Fragments and Navigation component within Jetpack
* SweepRefreshLayout for manual data refresh
* Coroutines
* Data Binding in XML files

