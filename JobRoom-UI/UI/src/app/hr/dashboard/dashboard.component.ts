import { HrService } from "./../shared/hr.service";
import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { NgxEncryptCookieService } from "ngx-encrypt-cookie";
import { I18nHrserviceService } from "../i18n-hrservice.service";

import { DashboardService } from "../shared/dashboard.service";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"],
  providers: [NgxEncryptCookieService],
})
export class DashboardComponent implements OnInit {
  language: string = "hello";
  countActiveAnnouncement;
  countCloseAnnouncement;
  countCandidate;
  countSchedule;
  hrDetails;
  idHr: any;
  datas = JSON.parse(sessionStorage.getItem("changlang"));

  constructor(
    translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private cookie: NgxEncryptCookieService,
    private hrService: HrService,
    private dashboardService: DashboardService
  ) {
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
    translate.setDefaultLang("ar");
    this.idHr = this.cookie.get("id",true,"hrd")
  }

  ngOnInit(): void {
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });

    this.dashboardService.countActiveAnnouncement(this.idHr).subscribe((data) => {
      this.countActiveAnnouncement = data.data.count;
    });
    this.dashboardService.countCloseAnnouncement(this.idHr).subscribe((data) => {
      this.countCloseAnnouncement = data.data.count;
    });
    this.dashboardService
      .countCandidateByActiveAnnouncementAndCompany(this.idHr)
      .subscribe((data) => {
        this.countCandidate = data.data.count;
      });
    this.dashboardService.countScheduleByHr(this.idHr).subscribe((data) => {
      this.countSchedule = data.data.count;
    });
  }

  //TODO: to get detail hr
  getHrDetails() {
    let userIdAsString = this.cookie.get("userId", true, "hrd");
    let userId = parseInt(userIdAsString);
    this.hrService.getHrDetails(userIdAsString).subscribe((res: any) => {
      this.hrDetails = res.data.data;
    });
  }
}
