import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BaseAPI } from "../../baseApi/BaseAPI";
import { Subject } from "rxjs";
import { tap } from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class SettingService {
  baseAPI = new BaseAPI();
  private _refreshNeed = new Subject<void>();

  get refreshNeed(): Subject<void> {
    return this._refreshNeed;
  }

  constructor(private http: HttpClient) {}

  //TODO: to get detail company by id
  getHrById(id): Observable<any> {
    return this.http.get(this.baseAPI.baseApiHR + "companies/" + id);
  }

  //TODO: to update hr
  updateHr(id, hr): Observable<any> {
    return this.http.put(this.baseAPI.baseApiHR + "companies/" + id, hr).pipe(
      tap(()=>{
        this._refreshNeed.next();
      })
    )
  }

  //TODO: to update logo company
  uploadImage(image): Observable<any> {
    const formData = new FormData();
    formData.append("files", image);
    return this.http.post(
      "https://gateway.kshrd-ite.com/hr/uploads",
      formData
    );
  }

  //TODO: to update password
  updatePassword(hr): Observable<any> {
    return this.http.post(this.baseAPI.baseApiAuth + "update/hr", hr);
  }

  //TODO: to get company by id Oauth
  getCompanyUserByIdOauth(id): Observable<any> {
    return this.http.get(this.baseAPI.baseApiAuth + "getCompanyUser/" + id);
  }
}
