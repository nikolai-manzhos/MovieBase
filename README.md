# MovieBase
[![CircleCI](https://circleci.com/gh/NikolayManzhos/MovieBase/tree/dev.svg?style=shield)](https://circleci.com/gh/NikolayManzhos/MovieBase/tree/dev)
[![codecov](https://codecov.io/gh/NikolayManzhos/MovieBase/branch/dev/graph/badge.svg)](https://codecov.io/gh/NikolayManzhos/MovieBase)  
[Get MovieBase on GooglePlay](https://play.google.com/store/apps/details?id=com.defaultapps.moviebase)  
# Building project
In order to build project you need to supply correct API keys for ```MDB``` and ```YouTube```.  
[Get MDB key.](https://www.themoviedb.org/settings/api)  
[Get YouTube key.](https://developers.google.com/youtube/v3/getting-started)  
After you got your keys create ```keystore.properties``` file in root folder and put keys in it.
```gradle
MovieDbSecretKey="YOUR-MDB-KEY"
YoutubeSecretKey="YOUR-YOUTUBE-KEY"
```
After that you should be able to build project by running ```gradlew assembleDebug```.
# Tests & checks
Running static analysis ```gradlew checkstyle```, Unit & Integration tests ```gradlew testDebugUnitTest```.
