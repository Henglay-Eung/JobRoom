import { BaseAPI } from "./../../baseApi/BaseAPI";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CandidateService {
  baseAPI = new BaseAPI();

  constructor(private http: HttpClient) {}

  //TODO: to get data of candidate by company id
  getCandidateCompanyId(id, currentPage): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApi +
        "candidates/company/" +
        id +
        "?page=" +
        currentPage +
        "&pageSize=5"
    );
  }

  //TODO: to search candidate
  searchCandidate(id, currentPage, name): Observable<any> {
    return this.http.get(
      this.baseAPI.baseApi +
        "candidates/company/" +
        id +
        "/search?" +
        "name=" +
        name +
        "&page=" +
        currentPage +
        "&pageSize=5"
    );
  }

  //TODO: to get candidate by id
  getCandidateById(id) {
    return this.http.get(this.baseAPI.baseApi + "candidates/" + id);
  }
}
