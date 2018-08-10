# android-musicCollection
An android app that allows for users to store and rate album information

The app is written in Java. 

Album information is requested from the [MusicBrainz](https://musicbrainz.org/) XML API. 

User's album information is saved in an SQLite database.

## Overview

Users are able to search for albums using a a searchview at the top of the screen

![album search](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848509.png?raw=true)

Once the user submits a search query, an AsyncTask fetches the XML with the user's given query. 
A Dialogfragment is then presented to the user containing all of the results from their query. 
Albums are sorted by release year and duplicate listing are removed (Albums are sometimes listed multiple times in the API if they were released in multiple formats)

![album results](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848506.png?raw=true)

If the user selects an album from the list, the Dialogfragment is closed, the album is added to the database
and the Recyclerview is updated to show the new album

The user can then click on the album record to edit it or add a rating

![album added](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848509.png?raw=true)


User's are also able to create custom Albums to be saved to the database

![custom album[(https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848525.png?raw=true)

Albums can be highlighted to be removed by long clicking on their row in the Recyclerview. Once an album is highlighted a contextual action menu is displayed. When all albums are deselected the action menu is hidden

![album selected](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848552.png)

Users are also able to select multiple rows at once

This selection is kept if the view is rotated

![multiple selected](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848549.png)


The app also supports landscape mode for all views and information is maintained on rotation.

![landscape selected](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848555.png)

![landscape list](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848561.png)

![landscape custom album](https://github.com/jwells-github/android-musicCollection/blob/master/readmeImages/Screenshot_1533848565.png)




