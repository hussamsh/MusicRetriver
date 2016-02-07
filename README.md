#MusicRetriever 

A library that makes accessing audio files on android devices easier

#How to use:

##
```java
MusicRetriever retriever = MusicRetriever.with(getContextResolver());
```
```java
MusicQuery.Builder songsBuilder = new MusicQuery.Builder()
                .addMainArgument(RowConstants.Artist_NAME , Operator.EQUALS , "Adele")
                .sortBy(RowConstants.DATE_ADDED);

//Gets all songs Where "Adele" is the artist and sorts them by thier added date
ArrayList<Songs> songs = retriever.getSongs(songsBuilder.build);
```
```java
MusicQuery.Builder builder = new MusicQuery.Builder()
                .addMainArgument(RowConstants.ALBUM_NAME , Operator.LIKE , "M");
                
//Gets all albums where "m" is in the albume name
ArrayList<Album> albums = retriever.getAlbums(builder.build);
```
```java
MusicQuery.Builder builder = new MusicQuery.Builder()
                .addMainArgument(RowConstants.SONG_TITILE , Operator.LIKE , "HO")
                .sortBy(RowConstants.SONG_TITLE);
                
//Gets all artists who has a song with an "HO" in its title
ArrayList<Artist> artists = retriever.getArtists(builder.build);
```

#Motivation:

This library was made for "Musick" a Music player app for android and I thought it could be beneficial for me to share it
<!--You can try it out here [Google Play](I will add a link once it's published)-->

#Installation:


#Developed by:

You can contact me for more info or suggestions

<hossamshdesigns@gmail.com>

#License
  Copyright 2016 Hossam Sherif
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
