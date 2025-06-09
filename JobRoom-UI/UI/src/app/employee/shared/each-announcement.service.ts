import { BaseAPI } from "../../baseApi/BaseAPI";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, Subject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class EachAnnouncementService {
  baseAPI = new BaseAPI();
  searchSource = new Subject<number>();
  search = this.searchSource.asObservable();
  filterByDateSource = new Subject<number>();
  filerByDate = this.filterByDateSource.asObservable();
  currentPageSource = new Subject<number>();
  currentPage = this.currentPageSource.asObservable();

  constructor(private http: HttpClient) {}

  //TODO: to get all announcement
  getAllAnnouncement(
    startDate,
    endDate,
    position,
    currentPage
  ): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApiAnnouncement +
        "announcements-employees/?page=" +
        currentPage +
        "&startDate=" +
        startDate +
        "&endDate=" +
        endDate +
        "&position"
    );
  }
}
