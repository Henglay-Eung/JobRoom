import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class ShareAnnouncementService {
  url1 = "https://gateway.kshrd-ite.com/post/posts";
  baseUrl = "https://gateway.kshrd-ite.com/job/announcements";
  //file cv url
  filecvUrl = "https://gateway.kshrd-ite.com/job/uploads";
  //get compny uuid by id
  companybyidUrl = "https://gateway.kshrd-ite.com/hr/companies";

  constructor(private http: HttpClient) {}

  //TODO: to get announcement by id
  getannouncementbyId(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  //TODO: to post 
  postData(data): Observable<any> {
    return this.http.post(`${this.url1}`, data);
  }

  //TODO: to post cv file
  uploadcvfile(data) {
    const formDate = new FormData();
    formDate.append("files", data);
    return this.http.post(this.filecvUrl, formDate);
  }
  
  //TODO: to get company uuid by company id
  getcompanyuuidbyCompanyId(id: number): Observable<any> {
    return this.http.get(`${this.companybyidUrl}/${id}`);
  }
}
