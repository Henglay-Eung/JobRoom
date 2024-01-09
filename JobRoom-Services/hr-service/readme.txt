HR Service

+(GET) /api/v1/companies

+(POST) /api/v1/companies
	
	-Request body
	{
	  "banned": true,
	  "caption": "string",
	  "closedDate": "string",
	  "companyId": 0,
	  "draft": true,
	  "form": {},
	  "image": "string",
 	 "position": "string",
 	 "publishedDate": "string",
 	 "shared": true,
 	 "thumbnail": "string"
	}		

+(GET) /api/v1/companies-announcements/{id}
+(GET) /api/v1/companies/{id}

+(PUT) /api/v1/companies/{id}

	-Request body
	{
	  "banned": true,
	  "caption": "string",
	  "closedDate": "string",
	  "companyId": 0,
	  "draft": true,
	  "form": {},
	  "image": "string",
 	 "position": "string",
 	 "publishedDate": "string",
 	 "shared": true,
 	 "thumbnail": "string"
	}


+(DELETE) /api?/v1?/companies?/{id}