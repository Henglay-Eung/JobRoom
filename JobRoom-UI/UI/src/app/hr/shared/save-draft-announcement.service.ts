import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BaseAPI } from "../../baseApi/BaseAPI";

@Injectable({
  providedIn: "root",
})
export class SaveDraftAnnouncementService {
  baseAPI = new BaseAPI();

  constructor(private http: HttpClient) {}

  //TODO: to get save draft announcement by company id
  getSaveDraftAnnouncementByCompany(
    id,
    caption,
    position,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiHR +
        "announcements/company/draft/" +
        id +
        "?caption=" +
        caption +
        "&position=" +
        position +
        "&page=" +
        currentPage
    );
  }

  //TODO: to fitler save draft announcement between start date and end date
  filterSaveDraftAnnouncementByStartAndEndDate(
    id,
    start,
    end,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiHR +
        "announcements/company/draft-by-date/" +
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
