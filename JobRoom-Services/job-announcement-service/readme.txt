+ Announcement Service 

-(POST) /api/v1/announcements

	-request body

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

-(GET) /api/v1/announcements-employees
-(GET) /api/v1/announcements-hr
-(GET) /api/v1/announcements/{id}

-(PUT) /api/v1/announcements/{id}

	-request body

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


-(DELETE) /api/v1/announcements/{id}
-(GET) /api/v1/announcements/company/{companyId}
-(GET) /api/v1/announcements/company/active-announcement/{companyId}
-(GET) /api/v1/announcements/company/closed-announcement/{companyId}
-(GET) /api/v1/announcements/company/draft/{companyId}
-(GET) /api/v1/announcements/company/list-position/{companyId}


-(POST) /api/v1/announcements/draft/{id}
-(POST) /api/v1/announcements/share/{id}
-(POST) /api/v1/announcements/unban/{id}
-(POST) /api/v1/announcements/undraft/{id}
-(POST) /api/v1/announcements/unshare/{id}

   -Candidate

	-(GET) /api/v1/candidates

	-(POST) /api/v1/candidates

	request body

	{
  	"announcementId": 0,
  	"cvLink": "string",
 	 "employeeId": 0
	}

	-(GET) /api/v1/candidates/{id}
	-(DELETE) /api/v1/candidates/{id}
	-(GET) /api/v1/candidates/announcement/{announcementId}
	-(GET) /api/v1/candidates/filterDate