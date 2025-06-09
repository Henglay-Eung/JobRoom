import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BaseAPI } from "../../baseApi/BaseAPI";

@Injectable({
  providedIn: "root",
})
export class HrService {
  baseAPI = new BaseAPI();

  constructor(private http: HttpClient) {}

  //TODO: to get candidate by announcement
  getCandidateByAnnouncement(id, currentPage): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement +
        "candidates/announcement/" +
        id +
        "?page=" +
        currentPage
    );
  }

  //TODO: to get announcement by company
  getAnnouncementByCompany(id, currentPage, caption): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement +
        "announcements/company/" +
        id +
        "?page=" +
        currentPage +
        "&caption=" +
        caption
    );
  }

  //TODO: to filter candidate between start date and end date
  filterCandidateByDate(id, startDate, endDate, currentPage): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement +
        "candidates/company/" +
        id +
        "/filterDate?endDate=" +
        endDate +
        "&startDate=" +
        startDate +
        "&page=" +
        currentPage +
        "&pageSize=2"
    );
  }

  //TODO: to get position by company
  getPositionByCompany(id): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement + "announcements/company/list-position/" + id
    );
  }

  //TODO: to delete candidate by id
  deleteCandidate(id): Observable<any> {
    return this.http.delete(this.baseAPI.baseApiAnnouncement + "candidates/" + id);
  }

  //TODO : delete active announcement by id
  deleteAnnouncement(id): Observable<any> {
    return this.http.delete(this.baseAPI.baseApiAnnouncement + "announcements/" + id);
  }

  //TODO: to details company by id
  getHrDetails(id) {
    //return this.http.get("http://35.197.132.204:30005/api/v1/companies/" + id);
    return this.http.get(this.baseAPI.baseApiHR+"/companies/"+id)
  }
}
