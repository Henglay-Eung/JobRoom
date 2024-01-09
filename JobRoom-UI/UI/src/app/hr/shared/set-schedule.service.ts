import { BaseAPI } from "./../../baseApi/BaseAPI";

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NotificationsService } from "angular2-notifications";
import {Subject} from "rxjs";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class SetScheduleService {
  baseAPI = new BaseAPI();
  private _refreshNeed = new Subject<void>();

  get refreshNeed(): Subject<void> {
    return this._refreshNeed;
  }
  constructor(
    private http: HttpClient,
    private _service: NotificationsService
  ) {}

  //TODO: to post schedule
  postSetSchedule(object: Object, email, password,companyName) {
    return this.http.post(
      this.baseAPI.baseApiSchedule +
        "schedules?companyName= "+ companyName+ "&email=" + email +
        "&password=" +
        password,
      object
    );
  }

  //TODO: to update scheduel
  updateSchedule(object: Object, email, password, id: any, companyName) {
    return this.http.put(
      this.baseAPI.baseApiSchedule +
        "schedules/" + id + "?companyName=" + companyName + "&email=" + email + "&password=" + password,object
    ).pipe(
      tap(()=>{
        this._refreshNeed.next();
      })
    );
  }

  getScheduleByCompanyId(id) {
    return this.http.get(this.baseAPI.baseApiSchedule + "schedules/" + id);
  }

  getScheduleById(id) {
    return this.http.get(this.baseAPI.baseApiSchedule + "schedules/by/" + id);
  }

  deleteSchedule(id) {
    return this.http.delete(this.baseAPI.baseApiSchedule + "schedules/" + id);
  }
}
