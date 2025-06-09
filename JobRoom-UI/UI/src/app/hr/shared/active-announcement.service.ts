import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BaseAPI } from "../../baseApi/BaseAPI";

@Injectable({
  providedIn: "root",
})
export class ActiveAnnouncementService {
  baseAPI = new BaseAPI();

  constructor(private http: HttpClient) {}

  //TODO: to get data of active announcement by company id
  getActiveAnnouncementByCompany(
    id,
    caption,
    position,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement +
        "announcements/company/active-announcement/" +
        id +
        "?caption=" +
        caption +
        "&page=" +
        currentPage +
        "&position=" +
        position
    );
  }

  //TODO: to filter active announcement between start date and end date
  filterAnnouncementByStartAndEndDate(
    id,
    start,
    end,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement +
        "announcements/company/active-announcement-by-date/" +
        id +
        "?startDate=" +
        start +
        "&endDate=" +
        end +
        "&page=" +
        currentPage
    );
  }
}
