import { BaseAPI } from "./../../baseApi/BaseAPI";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CvApplyService {
  //get cv by user id
  getcvUrl = "https://gateway.kshrd-ite.com/employee/cv/userId";
  //get apply cv as candidate
  applycvUrl = "https://gateway.kshrd-ite.com/job/candidates";
  //get commany id by announcement id
  getcompnayidUrl =
    "https://gateway.kshrd-ite.com/hr/companies-announcements";
  //file cv url
  filecvUrl = "https://gateway.kshrd-ite.com/job/uploads";
  //get employee by id
  getemployebyidUrl = "https://gateway.kshrd-ite.com/employee/employees";

  constructor(private http: HttpClient) {}

  //TODO: to get company by announcement id
  getcompanybyAnnouncementId(id: number): Observable<any> {
    return this.http.get(`${this.getcompnayidUrl}/${id}`);
  }

  //TODO: to get cv by id
  getcvbyid(id: number): Observable<any> {
    return this.http.get(`${this.getcvUrl}/${id}`);
  }

  //TODO: to post cv
  postcv(data) {
    return this.http
      .post(this.applycvUrl, data)
      .toPromise()
      .then((data: any) => {
        console.log(data);
      });
  }

  //TODO: to post cv file
  uploadcvfile(data) {
    const formDate = new FormData();
    formDate.append("files", data);
    return this.http.post(this.filecvUrl, formDate);
  }

  //TODO: to get employee by id
  getemployeebyid(id: number): Observable<any> {
    return this.http.get(`${this.getemployebyidUrl}/${id}`);
  }
}
