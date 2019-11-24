# TrackList

## What does this App do?
This Android application fetches tracks from the iTunes Search API. A list is then shown to the user containing the following fields:
  1. Track Name
  2. Genre
  3. Price, and
  4. Artwork*
  
The user may then select a track from the list which will open a new screen displaying the aforementioned fields alongside the track's long description.

The fetched tracks from iTunes Search API are then saved to a local database for future use.

## Architectural Pattern
* MVVM: This app uses the Model-View-ViewModel Pattern approach. This approach is chosen due to the following reasons:
  1. For the persistence of data during configuration changes
  2. To isolate the layers for setting up Views, doing the business logic, and fetching Data from either remote or local sources. This isolation of roles helps make a more robust and highly maintainable application.
  3. Android Architecture Components are greatly utilized using this architectural pattern.


* Repository Pattern: There are two sources of data for this app. The iTunes Search API itself and the Local database where previous results of the Search API is saved. These data sources are designated as RemoteSource and Dao (local source) respectively.

  These two data sources are then wrapped inside a Repository class. This wrapper class is the one that determines whether the data to be shown to the user is from the iTunes Search API or from the local database.


* Inversion of Control (Dependency Injection): To further segregate the responsibilities of different parts of the program, a technique called Dependency Injection is applied to this project. This pattern separates the creation of different dependencies from the objects that use them. This technique makes classes more testable by not letting them know how their dependencies are constructed.
  
## Android Architecture Components Used
  1. ViewModel: all business logic and calls to the Repository class are made inside this class. This class is not destroyed during configuration changes.
  2. LiveData: this object is used to propagate results from ViewModel to the View layer. This eliminates the need to expose the View layer, which often gets destroyed to the layer that performs the business logic.
  3. Room: an SQLite mapping library developed by Google. This component is used to locally persist data fetched from the iTunes Search API.
  
## Third-party Libraries Used
  1. Koin: used for Dependency Injection. [https://insert-koin.io/]
  2. Retrofit: used for creating HTTP calls to iTunes Search API [https://square.github.io/retrofit/]
  3. Gson: used to deserialized results from iTunes Search API. [https://github.com/google/gson]
  4. Glide: used to load Artwork* images from a URL [https://github.com/bumptech/glide]
  
  
  
  
 *The largest available size for artworks is 100x100. Artworks will appear pixelated when rendered on almost all devices.