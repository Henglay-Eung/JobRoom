import { BaseAPI } from "./../../baseApi/BaseAPI";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class EmployeeService {
  constructor(private http: HttpClient) {}

  //TODO: to get employee detail
  getEmployeeDetails(id) {
    return this.http.get("https://gateway.kshrd-ite.com/employee/employees/" + id);
  }

  //TODO: to post feedback
  postFeedback(obj: Object) {
    return this.http.post("https://gateway.kshrd-ite.com/job/feedback", obj);
  }
}
