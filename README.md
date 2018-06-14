# chh-test
Android technical test for the Android Developer position at CHH

### Features

* Written in **Kotlin**
* Clean architecture using **MVVM** as design pattern with unidirectional data flow a.k.a. **MVI**
* **Architecture Components** (`ViewModel` to survive screen orientation changes and `LiveData` as a lifecycle-aware observable data holder with the observer pattern)
* **RxKotlin / RxAndroid** in order to build the reactive architecture
* **RxBinding** by *Jake Wharton* to further emphasize the reactive approach by having UI events such as click listeners, etc as observables which will be passed to the view model
* **Realm**
* **Retrofit** for HTTP requests
* **HttpLoggingInterceptor** to log every HTTP request
* **Dagger** for handling DI easily
* **Mockito** for unit testing

### Some of the advantages of a reactive architecture with unidirectional data flow or MVI

* **Immutability**: For every result (loading, success, error), a new `ViewState` is constructed
* **Testing**: This approach makes testing easier because, in order to test view models, you only need to assert that for a given `UiEvent`, the corresponding `ViewState` is created and rendered
* **Single source of truth for data**: The data is coming from a single point, which makes it a lot easier to debug errors, becuse they're easily traceable

For a more detailed explanation of MVI, check this out: http://hannesdorfmann.com/android/mosby3-mvi-1


# Additional notes

The test is not 100% complete. I had no time to unit test every class nor implement any UI test. Furthermore, whenever fetching the user portfolio, there may arise the case where we don't directly know the name of a coin with a particular ID, because we haven't fetched that from the list, and so, it has not been stored locally. For these cases, I'm showing **Unknown Coin**. I would also have moved dimensions (for margins, padding, text sizes, etc)  to a separated `dimens.xml`, as well as moved hard-coded strings to `strings.xml`.

### Thank you very much!
