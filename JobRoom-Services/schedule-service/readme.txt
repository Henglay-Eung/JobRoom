Schedule Service

*Allow spring to use email : https://myaccount.google.com/lesssecureapps


+(POST) /api/v1/schedules

	-request param : email,password
	-request body
	{
 	 "candidateIdRequest": [
 	   {
	      "id": 0
 	   }
 	 ],
	  "emailContent": "string",
	  "hrId": 0,
	  "meetingDate": "string",
	  "position": "string",
 	 "remark": "string"
	}

+(GET) /api/v1/schedules/{id}


+(PUT) /api/v1/schedules/{id}

	-request param : email,password
	-request body
	{
 	 "candidateIdRequest": [
 	   {
 	     "id": 0
 	   }
 	 ],
 	 "emailContent": "string",
 	 "hrId": 0,
 	 "meetingDate": "string",
 	 "position": "string",
 	 "remark": "string"
	}

+(DELETE) /api/v1/schedules/{id}
