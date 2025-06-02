import { Injectable } from "@angular/core";
import { BaseAPI } from "../../baseApi/BaseAPI";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class DashboardService {
  baseAPI = new BaseAPI();

  constructor(private http: HttpClient) {}

  //TODO: to count active announcement
  countActiveAnnouncement(id): Observable<any> {
    
    return this.http.get(
      this.baseAPI.baseApiHR + "announcements/company/count-active/" + id
    );
  }

  //TODO: to count close announcement
  countCloseAnnouncement(id): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiHR + "announcements/company/count-close/" + id
    );
  }

  //TODO: to count canidate by active announcement and company
  countCandidateByActiveAnnouncementAndCompany(id): Observable<any> {
    return this.http.get(this.baseAPI.baseApiHR + "candidates/count/" + id);
  }

  //TODO: to count set schedule
  countScheduleByHr(id): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiSchedule + "schedules/count/" + id
    );
  }
}
