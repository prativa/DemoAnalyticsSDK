SDK Work Flow:
To use the Android Demo SDK, user will will have to import the AAR file in libs folder and add the given lines of code in build.gradle file:
implementation(files("libs/mysdk-debug.aar"))
implementation(libs.realm)


Where, libs.real is configured as follows in libs file:
realm = "2.0.0"
realm = {group = "io.realm.kotlin", name ="library-base", version.ref ="realm"}


The core class of our SDK  is mysdk-debug.aar file is:


AnalyticsDemoSDK(
   val context: Context,
   val userId: String,
   trackAutomaticEvents: Boolean,
   val apiToken: String
)


context is the context of the View where the SDK is initialized.
userId is the unique user to track user session.
trackAutomaticEvents will automatically enable the session tracking.
apiToken is the unique key to identify the user who has used the Android Demo SDK.  


To use the Analytics Demo SDK, it can be used by initializing in the following way:

val analytics = AnalyticsDemoSDK.getInstance(this, "1", true, dummyApiToken)

Here, we have used a Singleton pattern. It ensures that we only have one instance of our SDK through the app. By providing a single access point, it makes it easy to interact with the instance from anywhere in our codebase.
The user has three params
class User : RealmObject {
   var userId: String = ""
   @PrimaryKey
   var userApiKey: String = ""
   var sessionEnabled: Boolean = false
}
Say, we have a userApiKey from the backend server.
The userId is the primary user identifier through id. 
If the user has not logged into the app, then the userId can be deviceId. Device id will be unique to each user. 
Once the user has logged in to the backend server, the response params will provide user id. Then, the userId will be set.
The tracking will be enabled by default, but, if in case, the user wants to stop or restart session, then they can do so, using 
startSession()
stopSession()

User can track events using AnalyticsDemoSDK:
analytics.trackEvent("Login");
The property has following params
We have to set event with properties in the following way:
val properties = mutableMapOf<String, Any>()
properties["device"] = "Android"
properties["version"] = "1.0.0"
properties["name"] = "Demo User"
analytics.trackEventWithProperties("SignUp", properties)
The user session can be enabled and disabled. If the user session is enabled, the events will be tracked in the SDK and if the events are disabled, the events will not be tracked.
analytics.startSession()
analytics.stopSession()


The data is stored in a persistent way in Database so that the data is not lost on the app restart. As further enhancement in the future, we can run a background sync in the SDK. In this way, users will also be able to track data in the offline mode. When the user comes back online, the sync manager runs in the background every 2 minutes and pushes the data to the server.


It will be stored in the following format in database:

{"device":"Android","version":"1.0.0","name":"Demo User"}
