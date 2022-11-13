EXPLORE WITH ME

Free time is a valuable resource. Every day we plan how to spend it — where and with whom to go. The most difficult thing in such planning is the search for information and negotiations. What events are planned, whether friends are free at this moment, how to invite everyone and where to gather. The application that you will create is a poster where you can offer any event from an exhibition to a trip to the cinema and recruit a company to participate in it.
<img width="1440" alt="S19_09_1662138862" src="https://user-images.githubusercontent.com/94691893/201513320-4e30b3d8-d193-4925-9694-13b7d13e4ff9.png">
Two services

* Main service — contains everything necessary for the operation of the product.
* Statistics service — stores the number of views and allows you to make various selections to analyze the operation of the application.
Main service

The API of the main service has three parts. The first one is public, accessible without registration to any network user. The second one is closed, available only to authorized users. The third is administrative, for service administrators. Each part has its own requirements.
Public API Requirements

The public API provides the ability to search and filter events.
* Sorting of the list of events is organized either by the number of views that should be requested in the statistics service, or by the dates of events.
* When viewing the list of events, only brief information about the events is returned.
* Viewing detailed information about a specific event needs to be configured separately (via a separate endpoint).
* Each event belongs to one of the categories fixed in the application.
* The ability to receive all available categories and collections of events is configured (such collections will be compiled by resource administrators).
* Each public request for a list of events or complete information about the event is recorded by the statistics service.

API requirements for authorized users

The closed part of the API is designed to implement the capabilities of registered users of the product.
* Authorized users have the ability to add new events to the application, edit them and view them after they are added.
* Submission of applications for participation in events of interest is configured.
* The creator of the event has the opportunity to confirm applications sent by other users of the service.

API requirements for the administrator

The administrative part of the API provides the ability to configure and support the operation of the service.
* Configured to add, change and delete categories for events.
* It is possible to add, delete and pin on the main page of the collection of events.
* There is moderation of events posted by users — publication or rejection.
* User management is also configured — add, view and delete.

Statistics Service

The second service, statistics, is designed to collect information. Firstly, about the number of user requests to the event lists and, secondly, about the number of requests for detailed information about the event. Based on this information, statistics on the operation of the application should be generated.
The functionality of the statistics service contains:
* record information that a request to the API endpoint has been processed;
* provide statistics for selected dates for the selected endpoint.Модель данных
The life cycle of an event should include several stages.
* Creation.
* Waiting for publication. The event switches to the publication waiting status immediately after creation.
* Publication. The event is transferred to this state by the administrator.
* Cancellation of publication. The event switches to this state in two cases. The first is if the administrator has decided that it cannot be published. The second is when the initiator of the event decided to cancel it at the stage of waiting for publication.
