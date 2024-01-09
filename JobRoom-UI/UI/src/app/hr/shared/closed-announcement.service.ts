import { BaseAPI } from "../../baseApi/BaseAPI";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ClosedAnnouncementService {
  baseAPI = new BaseAPI();

  constructor(private http: HttpClient) {}

  //TODO: to get close announcement by company
  getCloseAnnouncementByCompany(
    id,
    caption,
    position,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApi +
        "announcements/company/closed-announcement/" +
        id +
        "?page=" +
        currentPage +
        "&caption=" +
        caption +
        "&position=" +
        position
    );
  }

  //TODO: to filter close announcement between start date and end date
  filterCloseAnnouncementByStartAndEndDate(
    id,
    start,
    end,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApi +
        "announcements/company/closed-announcement-by-date/" +
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
