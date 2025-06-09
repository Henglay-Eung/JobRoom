import { HrService } from './../../shared/hr.service';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { ActivatedRoute, Router } from "@angular/router";
import { DatePipe } from "@angular/common";
import { CreateAnnouncementService } from "./../../shared/create-announcement.service";
import { Component, OnInit } from "@angular/core";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { BsDatepickerConfig } from "ngx-bootstrap";

import { I18nHrserviceService } from "./../../i18n-hrservice.service";
import { TranslateService } from "@ngx-translate/core";
import { NotificationsService } from "angular2-notifications";
import {createBrowserLoggingCallback} from "@angular-devkit/build-angular/src/browser";


@Component({
  selector: "app-create-announcement",
  templateUrl: "./create-announcement.component.html",
  styleUrls: ["./create-announcement.component.css"],
})
export class CreateAnnouncementComponent implements OnInit {
  languages: string = "hello";
  announcement: object;
  idUpdate: any;
  isAddMode: boolean = false;
  idPreview: any;
  myLoading: boolean = false;

  logo: string;

  pipe = new DatePipe("en-US");
  datas = JSON.parse(sessionStorage.getItem("changlang"));
  JobAnounForm: FormGroup;

  close_date = new Date();

  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: "YYYY-MM-DD",
    showWeekNumbers: false,
  };

  header: object;
  body: object;
  footer: object;

  announcements = [];
  announcement_pdf = [];

  // Header
  isLogo: boolean = false;
  isTitle: boolean = false;
  isPosition: boolean = false;
  isCloseDate: boolean = false;
  isSalaryRange: boolean = false;
  isYearOfEx: boolean = false;
  isNumberOfHiring: boolean = false;
  isType: boolean = false;
  isIndustry: boolean = false;
  isQualification: boolean = false;
  isLanguage: boolean = false;
  isLocation: boolean = false;
  isHideDialog: boolean = false;

  //Body
  isBackground: boolean = false;
  isJobRequirement: boolean = false;
  isJobDescription: boolean = false;
  isHowToApply: boolean = false;
  isHideBody: boolean = false;

  //Footer
  isContact: boolean = false;
  isHideFooter: boolean = false;

  //Disable
  isDisableHeader: boolean = false;
  isDisableBody: boolean = false;
  isDisableFooter: boolean = false;

  idHr: any;

  provinces = [
    "Banteay Meanchey",
    "Battambang",
    "Kampong Cham",
    "Kampong Chhnang",
    "Kampong Speu",
    "Kampong Thom",
    "Kampot",
    "Kandal",
    "Koh Kong",
    "Kratié",
    "Mondulkiri",
    "Phnom Penh",
    "Preah Vihear",
    "Prey Veng",
    "Pursat",
    "Ratanak Kiri",
    "Siem Reap",
    "Preah Sihanouk",
    "Stung Treng",
    "Svay Rieng",
    "Takéo",
    "Oddar Meanchey",
    "Kep",
    "Pailin",
    "Tboung Khmum",
  ];

  constructor(
    private fb: FormBuilder,
    translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private ja: CreateAnnouncementService,
    private _activatedRoute: ActivatedRoute,
    private _service: NotificationsService,
    private router: Router,
    private cookie: NgxEncryptCookieService,
    private hrService: HrService,
  ) {
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
    translate.setDefaultLang("en");
    this.idHr = this.cookie.get("id",true,"hrd");
    this.hrService.getHrDetails(this.idHr).subscribe((res: any)=>{
      this.logo = res.data.logo;
      this.JobAnounForm.get('header').get('logo').setValue(this.logo);
    })
    this.JobAnounForm = this.fb.group({
      header: this.fb.group({
        logo: [this.logo, Validators.required],
        title: ["", [Validators.required, Validators.maxLength(100)]],
        position: ["", [Validators.required, Validators.maxLength(100)]],
        close_date: [this.close_date, Validators.required],
        salary_range: ["Negotiable", Validators.required],
        year_of_ex: ["", Validators.required],
        number_of_hiring: ["", Validators.required],
        type: ["Full Time", Validators.required],
        industry: ["", Validators.required],
        qualification: ["", Validators.required],
        language: ["", Validators.required],
        location: ["Phnom Penh", Validators.required],
      }),
      body: this.fb.group({
        background: ["", Validators.required],
        job_requirement: this.fb.array([]),
        job_description: this.fb.array([]),
        how_to_apply: ["", Validators.required],
      }),
      footer: this.fb.group({
        contact: this.fb.group({
          address: ["", Validators.required],
          website: ["", Validators.required],
        }),
      }),
    });
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
    this._activatedRoute.queryParams.subscribe((params) => {
      this.idUpdate = params["id"];
      if (this.idUpdate !== undefined) {
        this.isAddMode = true;
      }
    });
  }

  get address() {
    return this.JobAnounForm.get("footer").get("contact").get("address");
  }

  get website() {
    return this.JobAnounForm.get("footer").get("contact").get("website");
  }

  get background() {
    return this.JobAnounForm.get("body").get("background");
  }

  get how_to_apply() {
    return this.JobAnounForm.get("body").get("how_to_apply");
  }

  get title() {
    return this.JobAnounForm.get("header").get("title");
  }

  get position() {
    return this.JobAnounForm.get("header").get("position");
  }

  get salaryRange() {
    return this.JobAnounForm.get("header").get("salary_range");
  }

  get yearOfEx() {
    return this.JobAnounForm.get("header").get("year_of_ex");
  }

  get numberOfHiring() {
    return this.JobAnounForm.get("header").get("number_of_hiring");
  }

  get industry() {
    return this.JobAnounForm.get("header").get("industry");
  }

  get qualification() {
    return this.JobAnounForm.get("header").get("qualification");
  }

  get language() {
    return this.JobAnounForm.get("header").get("language");
  }

  // TODO: requirement
  JobRequirement(): FormArray {
    return this.JobAnounForm.get("body").get("job_requirement") as FormArray;
  }

  // TODO: description
  JobDescription(): FormArray {
    return this.JobAnounForm.get("body").get("job_description") as FormArray;
  }

  // TODO: add list
  newList(): FormGroup {
    return this.fb.group({
      list: "",
    });
  }

  // TODO: add requirement
  addJobRequirement() {
    this.JobRequirement().push(this.newList());
  }

  // TODO: add description
  addJobDescription() {
    this.JobDescription().push(this.newList());
  }

  // TODO: remove list of description
  removeJobDescription(JobInformationIndex: number) {
    this.JobDescription().removeAt(JobInformationIndex);
  }

  // TODO: remove list of requirement
  removeJobRequirement(JobInformationIndex: number) {
    this.JobRequirement().removeAt(JobInformationIndex);
  }

  // TODO: Hide and show dialog header
  onHideAndShowDialog() {
    this.isHideBody = false;
    this.isHideFooter = false;
    this.isHideDialog = !this.isHideDialog;
  }

  // TODO: Hide and show dialog body
  onHideAndShowBody() {
    this.isHideFooter = false;
    this.isHideBody = !this.isHideBody;
  }

  // TODO: Hide and show dialog footer
  onHideAndShowFooter() {
    this.isHideFooter = !this.isHideFooter;
  }

  // TODO: Hide and show logo
  onHideAndShowLogo() {
    this.isLogo = !this.isLogo;
    this.isDisableHeader = true;
  }

  // TODO: Hide and show title
  onHideAndShowTitle() {
    this.isTitle = !this.isTitle;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("title").setValue("");
  }

  // TODO: Hide and show position
  onHideAndShowPosition() {
    this.isPosition = !this.isPosition;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("position").setValue("");
  }

  onHideAndShowCloseDate() {
    this.isCloseDate = !this.isCloseDate;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("close_date").setValue("");
  }

  // TODO: Hide and show salary range
  onHideAndShowSalaryRange() {
    this.isSalaryRange = !this.isSalaryRange;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("salary_range").setValue("");
  }

  // TODO: Hide and show year of experience
  onHideAndShowYearOfEx() {
    this.isYearOfEx = !this.isYearOfEx;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("year_of_ex").setValue("");
  }

  // TODO: Hide and show numnber of hiring
  onHideAndShowNumberOfHiring() {
    this.isNumberOfHiring = !this.isNumberOfHiring;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("number_of_hiring").setValue("");
  }

  // TODO: Hide and show type
  onHideAndShowType() {
    this.isType = !this.isType;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("type").setValue("");
  }

  // TODO: Hide and show industry
  onHideAndShowIndustry() {
    this.isIndustry = !this.isIndustry;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("industry").setValue("");
  }

  // TODO: Hide and show qualification
  onHideAndShowQualification() {
    this.isQualification = !this.isQualification;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("qualification").setValue("");
  }

  // TODO: Hide and show language
  onHideAndShowLanguage() {
    this.isLanguage = !this.isLanguage;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("language").setValue("");
  }

  // TODO: Hide and show location
  onHideAndShowLocation() {
    this.isLocation = !this.isLocation;
    this.isDisableHeader = true;
    this.JobAnounForm.get("header").get("location").setValue("");
  }

  // TODO: Hide and show background
  onHideAndShowBackground() {
    this.isBackground = !this.isBackground;
    this.isDisableBody = true;
    this.JobAnounForm.get("body").get("background").setValue("");
  }

  // TODO: Hide and show job requirement
  onHideAndShowJobRequirement() {
    this.isJobRequirement = !this.isJobRequirement;
    this.isDisableBody = true;
  }

  // TODO: Hide and show job description
  onHideAndShowJobDescription() {
    this.isJobDescription = !this.isJobDescription;
    this.isDisableBody = true;
  }

  // TODO: Hide and show how to apply
  onHideAndShowHowToApply() {
    this.isHowToApply = !this.isHowToApply;
    this.isDisableBody = true;
    this.JobAnounForm.get("body").get("how_to_apply").setValue("");
  }

  // TODO: Hide and show contact
  onHideAndShowContact() {
    this.isContact = !this.isContact;
    this.isDisableFooter = true;
    this.JobAnounForm.get("footer").get("contact").get("address").setValue("");
    this.JobAnounForm.get("footer").get("contact").get("website").setValue("");
  }

  ngOnInit() {
    this.hrService.getHrDetails(this.idHr).subscribe((res: any)=>{
      this.logo = res.data.logo;
      this.JobAnounForm.get('header').get('logo').setValue(this.logo);
    })
    this.i18nService.localeEvent.subscribe((locale) => {
      this.languages = locale,
        sessionStorage.setItem('changlang', JSON.stringify(this.languages))
    });

    //TODO: if update announcement
    if (this.isAddMode) {
      this.ja.getJobAnnouncementById(this.idUpdate).subscribe((res: any) => {
        this.announcements.push(res.data);
        if (res.data.form.header.title) {
          this.isDisableHeader = true;
          this.isTitle = true;
        }
        if (res.data.form.header.position) {
          this.isDisableHeader = true;
          this.isPosition = true;
        }
        if (res.data.form.header.close_date) {
          this.close_date = res.data.closedDate;
          this.isDisableHeader = true;
          this.isCloseDate = true;
        }
        if (res.data.form.header.salary_range) {
          this.isDisableHeader = true;
          this.isSalaryRange = true;
        }
        if (res.data.form.header.year_of_ex) {
          this.isDisableHeader = true;
          this.isYearOfEx = true;
        }
        if (res.data.form.header.number_of_hiring) {
          this.isDisableHeader = true;
          this.isNumberOfHiring = true;
        }
        if (res.data.form.header.type) {
          this.isDisableHeader = true;
          this.isType = true;
        }
        if (res.data.form.header.industry) {
          this.isDisableHeader = true;
          this.isIndustry = true;
        }
        if (res.data.form.header.qualification) {
          this.isDisableHeader = true;
          this.isQualification = true;
        }
        if (res.data.form.header.language) {
          this.isDisableHeader = true;
          this.isLanguage = true;
        }
        if (res.data.form.header.location) {
          this.isDisableHeader = true;
          this.isLocation = true;
        }
        if (res.data.form.body.background) {
          this.isDisableBody = true;
          this.isBackground = true;
        }
        if (res.data.form.body.job_requirement.length !== 0) {
          this.isDisableBody = true;
          this.isJobRequirement = true;
        }
        if (res.data.form.body.job_description.length !== 0) {
          this.isDisableBody = true;
          this.isJobDescription = true;
        }
        if (res.data.form.body.how_to_apply) {
          this.isDisableBody = true;
          this.isHowToApply = true;
        }
        if (
          res.data.form.footer.contact.address !== "" ||
          res.data.form.footer.contact.website !== ""
        ) {
          this.isDisableFooter = true;
          this.isContact = true;
        }

        //set data to header
        this.JobAnounForm.get("header")
          .get("logo")
          .setValue(res.data.form.header.logo);
        this.JobAnounForm.get("header")
          .get("title")
          .setValue(res.data.form.header.title);
        this.JobAnounForm.get("header")
          .get("position")
          .setValue(res.data.form.header.position);
        this.JobAnounForm.get("header")
          .get("close_date")
          .setValue(res.data.form.header.close_date);
        this.JobAnounForm.get("header")
          .get("salary_range")
          .setValue(res.data.form.header.salary_range);
        this.JobAnounForm.get("header")
          .get("year_of_ex")
          .setValue(res.data.form.header.year_of_ex);
        this.JobAnounForm.get("header")
          .get("number_of_hiring")
          .setValue(res.data.form.header.number_of_hiring);
        this.JobAnounForm.get("header")
          .get("type")
          .setValue(res.data.form.header.type);
        this.JobAnounForm.get("header")
          .get("industry")
          .setValue(res.data.form.header.industry);
        this.JobAnounForm.get("header")
          .get("qualification")
          .setValue(res.data.form.header.qualification);
        this.JobAnounForm.get("header")
          .get("language")
          .setValue(res.data.form.header.language);
        this.JobAnounForm.get("header")
          .get("location")
          .setValue(res.data.form.header.location);

        //set data to body
        this.JobAnounForm.get("body")
          .get("background")
          .setValue(res.data.form.body.background);
        this.JobAnounForm.get("body")
          .get("how_to_apply")
          .setValue(res.data.form.body.how_to_apply);

        //set data to job requirement
        for (let i = 0; i < res.data.form.body.job_requirement.length; i++) {
          this.JobRequirement().push(this.newList());
        }
        this.JobAnounForm.get("body")
          .get("job_requirement")
          .setValue(res.data.form.body.job_requirement);

        //set data to job description
        for (let i = 0; i < res.data.form.body.job_description.length; i++) {
          this.JobDescription().push(this.newList());
        }
        this.JobAnounForm.get("body")
          .get("job_description")
          .setValue(res.data.form.body.job_description);

        //set data to footer
        this.JobAnounForm.get("footer")
          .get("contact")
          .setValue(res.data.form.footer.contact);
      });
    }
  }

  //TODO: to preview announcement
  onPreview() {
    console.log("idPreview: ", this.idPreview);
    console.log("idUpdate: ", this.idUpdate);
    
    if(this.idPreview !== this.idUpdate){
      this.isAddMode = true;
    }
    this.myLoading = true;
    var close_date = this.pipe.transform(
      this.JobAnounForm.get("header").get("close_date").value,
      "yyyy-MM-dd"
    );
    console.log("isAddMode: ", this.isAddMode);
    if(!this.isAddMode){
      this.announcement = {
        banned: false,
        caption: this.JobAnounForm.get("header").get("title").value,
        closedDate: close_date,
        companyId: this.idHr,
        draft: true,
        form: this.JobAnounForm.value,
        image: this.JobAnounForm.get("header").get("logo").value,
        position: this.JobAnounForm.get("header").get("position").value,
        publishedDate: close_date,
        shared: false,
        type: this.JobAnounForm.get("header").get("type").value,
        location: this.JobAnounForm.get("header").get("location").value,
        numberOfHiring: this.JobAnounForm.get("header").get("number_of_hiring").value,
        salaryRank: this.JobAnounForm.get("header").get("salary_range").value,
        thumbnail: "string",
      };

      // to post data to save draft when preview announcement
      this.ja.postToPreview(this.announcement).subscribe((res: any)=>{
        if(res.data == undefined){
          this.myLoading = false;
        }
        this.idPreview = res.data.id;
        this.idUpdate = res.data.id;
        if(res.message == "Announcement has been inserted successfully"){
          this.myLoading = false;
        }else if (res.message == "Announcement is failed to insert"){
          this.myLoading = true;
        }
        const url = this.router.serializeUrl(
          this.router.createUrlTree([`/announcement/view-detail/${this.idPreview}`])
        );
        window.open(url, '_blank');
      });
    }else {
      this.announcement = {
        banned: false,
        caption: this.JobAnounForm.get("header").get("title").value,
        closedDate: close_date,
        companyId: this.idHr,
        draft: true,
        form: this.JobAnounForm.value,
        image: this.JobAnounForm.get("header").get("logo").value,
        position: this.JobAnounForm.get("header").get("position").value,
        publishedDate: close_date,
        shared: false,
        type: this.JobAnounForm.get("header").get("type").value,
        location: this.JobAnounForm.get("header").get("location").value,
        numberOfHiring: this.JobAnounForm.get("header").get("number_of_hiring").value,
        salaryRank: this.JobAnounForm.get("header").get("salary_range").value,
        thumbnail: "string",
      };

      // to update preview announcement
      this.ja.updateToPreview(this.announcement,this.idUpdate).subscribe((res: any)=>{
        if(res.data == undefined){
          this.myLoading = false;
        }
        if(res.message == "announcement has been updated successfully"){
          this.myLoading = false;
        }else if (res.message == "Announcement is failed to insert"){
          this.myLoading = true;
        }
        const url = this.router.serializeUrl(
          this.router.createUrlTree([`/announcement/view-detail/${this.idUpdate}`])
        );
        window.open(url, '_blank');
      });
    }
  }

  //TODO: to submit announcement
  onSubmit() {
    this.myLoading = true;
    var close_date = this.pipe.transform(
      this.JobAnounForm.get("header").get("close_date").value,
      "yyyy-MM-dd"
    );
    if (this.isAddMode == false) {
      this.announcement = {
        banned: false,
        caption: this.JobAnounForm.get("header").get("title").value,
        closedDate: close_date,
        companyId: this.idHr,
        draft: false,
        form: this.JobAnounForm.value,
        image: this.JobAnounForm.get("header").get("logo").value,
        position: this.JobAnounForm.get("header").get("position").value,
        publishedDate: close_date,
        shared: false,
        type: this.JobAnounForm.get("header").get("type").value,
        location: this.JobAnounForm.get("header").get("location").value,
        numberOfHiring: this.JobAnounForm.get("header").get("number_of_hiring").value,
        salaryRank: this.JobAnounForm.get("header").get("salary_range").value,
        thumbnail: "string",
      };

      // to post announcement
      this.ja.postJobAnnouncement(this.announcement).subscribe((res:any) => {
        if(res.message === "Announcement is failed to insert"){
          this.myLoading = false;
          this._service.warn(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }else if(res.message === "Announcement has been inserted successfully"){
          this.myLoading = false;
          this._service.success(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }
      });
    } else if (this.isAddMode == true) {
      this.announcement = {
        banned: false,
        caption: this.JobAnounForm.get("header").get("title").value,
        closedDate: close_date,
        companyId: this.idHr,
        draft: false,
        form: this.JobAnounForm.value,
        image: this.JobAnounForm.get("header").get("logo").value,
        position: this.JobAnounForm.get("header").get("position").value,
        publishedDate: close_date,
        shared: false,
        type: this.JobAnounForm.get("header").get("type").value,
        location: this.JobAnounForm.get("header").get("location").value,
        numberOfHiring: this.JobAnounForm.get("header").get("number_of_hiring").value,
        salaryRank: this.JobAnounForm.get("header").get("salary_range").value,
        thumbnail: "string",
      };

      // to update announcement
      this.ja.updateJobAnnouncement(this.announcement, this.idUpdate).subscribe((res:any) => {
        if(res.message === "Announcement is failed to insert"){
          this.myLoading = false;
          this._service.warn(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }else if(res.message === "announcement has been updated successfully"){
          this.myLoading = false;
          this._service.success(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }
      });
    }
  }

  //TODO: to post data to save draft announcement
  onSaveDraft(){
    this.myLoading = true;
    var close_date = this.pipe.transform(
      this.JobAnounForm.get("header").get("close_date").value,
      "yyyy-MM-dd"
    );
    if(this.isAddMode == false){
      this.announcement = {
        banned: false,
        caption: this.JobAnounForm.get("header").get("title").value,
        closedDate: close_date,
        companyId: this.idHr,
        draft: true,
        form: this.JobAnounForm.value,
        image: this.JobAnounForm.get("header").get("logo").value,
        position: this.JobAnounForm.get("header").get("position").value,
        publishedDate: close_date,
        shared: false,
        type: this.JobAnounForm.get("header").get("type").value,
        location: this.JobAnounForm.get("header").get("location").value,
        numberOfHiring: this.JobAnounForm.get("header").get("number_of_hiring").value,
        salaryRank: this.JobAnounForm.get("header").get("salary_range").value,
        thumbnail: "string",
      };

      // to post data to save draft
      this.ja.postJobAnnouncement(this.announcement).subscribe((res:any) => {
        if(res.message === "Announcement is failed to insert"){
          this.myLoading = false;
          this._service.warn(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }else if(res.message === "Announcement has been inserted successfully"){
          this.myLoading = false;
          this._service.success(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }
      });
    }else if (this.isAddMode == true){
      this.announcement = {
        banned: false,
        caption: this.JobAnounForm.get("header").get("title").value,
        closedDate: close_date,
        companyId: this.idHr,
        draft: true,
        form: this.JobAnounForm.value,
        image: this.JobAnounForm.get("header").get("logo").value,
        position: this.JobAnounForm.get("header").get("position").value,
        publishedDate: close_date,
        shared: false,
        type: this.JobAnounForm.get("header").get("type").value,
        location: this.JobAnounForm.get("header").get("location").value,
        numberOfHiring: this.JobAnounForm.get("header").get("number_of_hiring").value,
        salaryRank: this.JobAnounForm.get("header").get("salary_range").value,
        thumbnail: "string",
      };

      // to update save draft announcement
      this.ja.updateJobAnnouncement(this.announcement, this.idUpdate).subscribe((res:any) => {
        if(res.message === "Announcement is failed to insert"){
          this.myLoading = false;
          this._service.warn(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }else if(res.message === "announcement has been updated successfully"){
          this.myLoading = false;
          this._service.success(res.message,'',{
            timeOut: 2000,
            position: [ "bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50
          })
        }
      });
    }
  }

}
