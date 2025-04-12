import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root",
})
export class EmployeeService {
  private baseUrl: string = environment.baseAPIUrl;

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
