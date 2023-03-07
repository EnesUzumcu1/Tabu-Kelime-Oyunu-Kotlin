<h1 align="center"> Tabu Kelime Oyunu </h1>

<p align="middle">
  <img src="https://user-images.githubusercontent.com/61823965/223453613-1e0e071f-2882-46e6-b2d6-db3a551aca4b.png" width="200">
  <img src="https://user-images.githubusercontent.com/61823965/223453630-7950e088-6331-475f-b792-dc3151e2852c.png" width="200">
  <img src="https://user-images.githubusercontent.com/61823965/223453640-0a7dd5c2-f8c7-46f6-8fcd-a647d30cff72.png" width="200">
  </p>
  <p align="middle">
  <img src="https://user-images.githubusercontent.com/61823965/223453653-1bc52233-6bff-4c8d-a643-2fe0e26f2788.png" width="200">
  <img src="https://user-images.githubusercontent.com/61823965/223453663-57bdabcc-9ed3-4bdf-ba1e-f29e0e55284b.png" width="200">
  <img src="https://user-images.githubusercontent.com/61823965/223453675-b1c547c2-e66d-4a39-831c-4a91d542cb1f.png" width="200">
</p>

## Table of Contents

* [About the Project](#about-the-project)
  * [Architecture](#architecture)
* [Features](#features)

## About The Project

Tabu Kelime Oyunu is a game application. The words from the database were read with SQLite Asset Helper. The team with the highest correct guess wins the game.

## Architecture
MVVM (Model-View-ViewModel) architecture pattern is used in the development of this application. The development language of the application is Kotlin.

* Architecture;
    * [View Binding](https://developer.android.com/topic/libraries/view-binding)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) 
    * [Navigation](https://developer.android.com/guide/navigation)
    
* Third parties;
    * [Sqlite Asset Helper](https://github.com/jgilfelt/android-sqlite-asset-helper)
    
## Features
   - Teams can change the number of teams, time, rounds, and passes from the settings page.
   - Teams can change their own team names on the "Takım Adı Belirle" page.
   - By clicking on the "Başla" button, the first team starts the game.
   - If the word is known correctly within the current time period, 1 points will be earned, if it is known incorrectly, 1 points will be reduced or if there is a right to pass, it can be skipped.
   - At the end of the game, the scores of all teams are shown and the name of the winning team is announced.
