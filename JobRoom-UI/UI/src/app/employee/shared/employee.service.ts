import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { BaseAPI } from "../../baseApi/BaseAPI";

@Injectable({
  providedIn: "root",
})
export class EmployeeService {
  private baseUrl: string = new BaseAPI().baseApiEmployee;

  constructor(private http: HttpClient) {}

  //TODO: to get employee detail
  getEmployeeDetails(id) {
    return this.http.get(`${this.baseUrl}/employees/` + id);
  }

  //TODO: to post feedback
  postFeedback(obj: Object) {
    return this.http.post("https://gateway.kshrd-ite.com/job/feedback", obj);
  }
}
