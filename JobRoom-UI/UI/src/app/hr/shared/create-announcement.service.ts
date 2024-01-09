import { HttpClient } from "@angular/common/http";
import { BaseAPI } from "../../baseApi/BaseAPI";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class CreateAnnouncementService {
  baseAPI = new BaseAPI();
  constructor(
    private http: HttpClient
  ) {}

  //TODO: to post job announcement
  postJobAnnouncement(object: Object) {
    return this.http.post(this.baseAPI.baseApi + "announcements", object);
  }

  //TODO: to update job announcement
  updateJobAnnouncement(object: Object, id: any) {
    return this.http.put(
      this.baseAPI.baseApi + "announcements" + `/${id}`,
      object
    );
  }

  //TODO: to post to preview job announcement
  postToPreview(object: Object) {
    return this.http.post(this.baseAPI.baseApi + "announcements", object);
  }

  //TODO: to update to preview job announcement
  updateToPreview(object: Object, id: any) {
    return this.http.put(
      this.baseAPI.baseApi + "announcements" + `/${id}`,
      object
    );
  }

  //TODO: to get job announcement by id
  getJobAnnouncementById(id) {
    return this.http.get(this.baseAPI.baseApi + "announcements/" + id);
  }
}
