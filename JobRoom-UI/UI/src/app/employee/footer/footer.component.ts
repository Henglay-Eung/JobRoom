import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { I18nServiceService } from "../i18n-service.service";
import { NgxEncryptCookieService } from "ngx-encrypt-cookie";
import { EmployeeService } from "../shared/employee.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { NotificationsService } from "angular2-notifications";

@Component({
  selector: "app-footer",
  templateUrl: "./footer.component.html",
  styleUrls: ["./footer.component.css"],
})
export class FooterComponent implements OnInit {
  language: string = "hello";
  idHr: any;
  feedBackForm: FormGroup;
  username: string;
  myLoading: boolean = false;
  data = JSON.parse(sessionStorage.getItem("changlang"));

  constructor(
    translate: TranslateService,
    private i18nService: I18nServiceService,
    private cookie: NgxEncryptCookieService,
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private _service: NotificationsService
  ) {
    this.feedBackForm = this.fb.group({
      content: ["", Validators.required],
    });
    translate.setDefaultLang(this.data);
    translate.use(this.data);
    translate.setDefaultLang("en");
    this.idHr = this.cookie.get("id", true,"hrd");
  }
  
  ngOnInit(): void {
    this.employeeService.getEmployeeDetails(this.idHr).subscribe((res: any) => {
      this.username = res.data.fullName;
    });
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
  }

  //TODO: feedback from job seeker to admin
  onFeedback() {
    this.myLoading = true;
    let object = {
      content: this.feedBackForm.get("content").value,
      userId: this.idHr,
      username: this.username,
    };

    this.employeeService.postFeedback(object).subscribe((res: any) => {
      if (res.message == "feedback has been inserted successfully") {
        this._service.success(res.message, "", {
          timeOut: 2000,
          position: ["bottom", "right"],
          clickToClose: true,
          clickIconToClose: true,
          animate: "fromRight",
          maxLength: 50,
        });
      } else {
        this._service.warn(res.message, "", {
          timeOut: 2000,
          position: ["bottom", "right"],
          clickToClose: true,
          clickIconToClose: true,
          animate: "fromRight",
          maxLength: 50,
        });
      }
      this.myLoading = false;
      this.feedBackForm.reset();
    });
  }
}
