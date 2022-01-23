# Archelon Surveys

[![CircleCI](https://circleci.com/gh/Birkbeck/android-coursework-danoc93.svg?style=svg&circle-token=b2ad37ffbdab282bf2a350c048925f2d65bf005a)](https://app.circleci.com/pipelines/github/Birkbeck/android-coursework-danoc93)

This project builds some functionality for the **Archelon Surveys** Android application.

As per the specifications, this uses AtomUI to standardize the design and implements two of the available UI flows.
On the system side, we load/push data from/to the [Achelon API](https://documenter.getpostman.com/view/13303180/TVepAUJQ).


##  Details

The application has been built with the following specifications

* Min SDK Version: 26
* Max SDK Version: 30
* Version: 0.1

## Project

The goal is to use this service as a means to collect survey information for **ARCHELON** (Τhe Sea Turtle Protection Society of Greece), reducing the need for manual data entry.

## Implementation

Two sequences were implemented for this project, one to allow users to login, logout and maintain a session within the application; and a second sequence allows users to create a survey and record "Inundation or Wash" events.

The Architecture follows the MVVM paradigm, using repositories for data management, as well as retrofit2/moshi for API data access and serialization.

### Login/Logout Sequence

The provided `Achelon API` provides a `login` endpoint that returns a single `token` that can be used to make other types of requests. Given that this token is not part of a SAML/OpenID OAuth implementation, we cannot leverage much for session management.

As a simple session management system, `SharedPreferences` are used to keep this token in the device. This is relatively safe since the only way to get a hold of these tokens is by rooting the device.

This token is injected in the Header of the outgoing requests for the other endpoints using an `Interceptor` as part of the `retrofit2` client.

Because there is no way to detect when a token is expired, the phone will automatically expire sessions after a week.

This all can be managed via the [SessionRepository](app/src/main/java/com/application/archelon/repositories/SessionRepository.kt) and the [SharedPrefs](./app/src/main/java/com/application/archelon/system/SharedPrefs.kt) utility class.

If at any point this API is changed to handle Authorization/Authentication via a more complex protocol, we can easily add an SDK to make our lives easier.

### New Survey Sequence

In the home screen the only icon that is functional is the one for **New Survey**, the others were just added for reference.

When a survey is started, the initial metadata is requested, and only after setting it the user can proceed to add individual events via the corresponding menu.

After all the records that a user wants are created, the only way for a survey to be pushed is for them to click **End Survey**.

At every point in this sequence the user can cancel things, ensuring no data ends up anywhere unless the whole thing is finished.

#### New Event: Inundation or Wash

This is the only event type that was implemented for the survey.

Because of the current limitations of the API, the photo functionality is not implemented; however, a widget has been added and a placeholder code was added to create a camera intent if desired.

As a compromise, the image ids are generated automatically and pushed as part of the event model.

### Survey State Management

This was implemented with a top level [NewSurveyViewModel](./app/src/main/java/com/application/archelon/screens/survey/NewSurveyViewModel.kt) that maintains the state for a whole survey.

Each individual event type maintains their own `EventViewModel`, whose state is only fed to the enclosing `NewSurveyViewModel` once a user has confirmed they want to save an event.

### Saving the Survey

The only point where all the user's inputs end up somewhere is when the data is pushed via the [SurveyRepository](./app/src/main/java/com/application/archelon/repositories/SurveyRepository.kt). This function is called by the top level NewSurveyViewModel, with all the relevant UI state.

It is important to understand that due to the current state of the API, a few of the fields are not used (for example observers and weather data is collected, but never pushed). Another detail is we push a `morning_survey` request per event/nest, which is not ideal.


## Credits

The images in the application were grabbed from [Icons8](https://icons8.com/icons/set/open-source). These are open as long as they are credited in the repository.

A [guide](https://stackoverflow.com/questions/32963394/how-to-use-interceptor-to-add-headers-in-retrofit-2-0) on how to add a network `Interceptor` to `retrofit2` was followed to automatically append the secure tokens to requests.

AtomUI was used as style system and to define the standard design for the application, some the included extensions in their code sample have been adapted for this application.
