# visma-meetings-manager

Here is my meeting-manager app for the 2023 visma internship.
And here is the link to the postman workspace where you can find all the valid requests for this application:

https://www.postman.com/bold-astronaut-704144/workspace/visma/collection/26059576-43d04d0b-4592-4644-92b8-9f4329f186f6?action=share&creator=26059576

## **Endpoints:**

### Create a New Meeting

Endpoint: POST /create
Creates a new meeting.

### Delete a Meeting

Endpoint: DELETE /delete?name={name}

Deletes a meeting by its name.

### Get Meetings

Endpoint: GET /get

Retrieves a list of meetings based on search criteria.

### Add Person to Meeting

Endpoint: POST /addPersonToMeeting

Adds a person to a meeting.

### Remove Person from Meeting

Endpoint: DELETE /removePersonFromMeeting

Removes a person from a meeting.