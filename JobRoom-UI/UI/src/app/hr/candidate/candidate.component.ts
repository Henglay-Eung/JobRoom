import { Router } from '@angular/router';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
// import { AngularEditorConfig } from "@kolkov/angular-editor";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { HrService } from "../shared/hr.service";
import { DatePipe } from "@angular/common";
import { BsDatepickerConfig, DateFormatter } from "ngx-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { I18nHrserviceService } from "../i18n-hrservice.service";
import { NotificationsService } from "angular2-notifications";
import { ActiveAnnouncementService } from "../shared/active-announcement.service";
import { CandidateService } from "../shared/candidate.service";
import { SetScheduleService } from "../shared/set-schedule.service";

@Component({
  selector: "app-candidate",
  templateUrl: "./candidate.component.html",
  styleUrls: ["../job-announcement/active-list/active-list.component.css"],
})
export class CandidateComponent implements OnInit {
  language: string = "hello";
  datas = JSON.parse(sessionStorage.getItem("changlang"));
  isLoading = false;
  tagsForm: FormGroup;
  ModifyForm: FormGroup;
  count = 0;
  htmlContent = "";
  myLoading: boolean = false;
  isNotPosition: boolean = false;
  totalPage;
  masterSelected: boolean;
  checklist: any;
  checkedList: any;

  bsValue = new Date();
  date_modify = new Date();
  start_date = new Date();
  end_date = new Date();

  bsRangeValue: Date[];
  maxDate = new Date();
  minDate: Date;

  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: "YYYY-MM-DD",
    showWeekNumbers: false,
  };

  settings = {
    bigBanner: true,
    timePicker: true,
    format: "yyy-MM-d hh:mm a",
    defaultOpen: false,
    closeOnSelect: false,
  };

  pipe = new DatePipe("en-US");
  data;
  initData;
  endDate = this.end_date;
  startDate = this.start_date;
  itemPerPage;
  totalItem;
  currentPage = 1;
  announcementId;
  isFilterByAnnouncement = false;
  isFilterByDate = false;
  isSearch = false;
  candiates = [];
  searchValue;
  selectedCandidate = [];

  select_all = false;
  select_one = false;

  modalRef: BsModalRef;
  modalRef1: BsModalRef;
  modalRef2: BsModalRef;
  announcements;

  idHr: any;
  companyName: string;

  // config: AngularEditorConfig = {
  //   editable: true,
  //   spellcheck: true,
  //   height: "5rem",
  //   minHeight: "10rem",
  //   placeholder: "Enter text here...",
  //   translate: "no",
  //   defaultParagraphSeparator: "p",
  //   defaultFontName: '"Roboto", "Siemreap", sans-serif',
  //   outline: false,
  //   toolbarHiddenButtons: [
  //     [
  //       // "undo",
  //       // "redo",
  //       "strikeThrough",
  //       "subscript",
  //       "superscript",
  //       // 'justifyLeft',
  //       // 'justifyCenter',
  //       // 'justifyRight',
  //       // "justifyFull",
  //       "indent",
  //       "outdent",
  //       // 'insertUnorderedList',
  //       // 'insertOrderedList',
  //       // 'heading',
  //       // 'fontName'
  //     ],
  //     [
  //       // 'fontSize',
  //       // "textColor",
  //       // "backgroundColor",
  //       "customClasses",
  //       // "link",
  //       "unlink",
  //       // "insertImage",
  //       "insertVideo",
  //       "insertHorizontalRule",
  //       // "removeFormat",
  //       "toggleEditorMode",
  //     ],
  //   ],
  //   customClasses: [
  //     {
  //       name: "quote",
  //       class: "quote",
  //     },
  //     {
  //       name: "redText",
  //       class: "redText",
  //     },
  //     {
  //       name: "titleText",
  //       class: "titleText",
  //       tag: "h1",
  //     },
  //   ],
  // };

  @ViewChild('template', { static: true }) template: TemplateRef<any>;
  @ViewChild('confirm', {static: true}) confirm: TemplateRef<any>;

  constructor(
    private modalService: BsModalService,
    private hrService: HrService,
    translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private _service: NotificationsService,
    private activeAnnouncementService: ActiveAnnouncementService,
    private candidateService: CandidateService,
    private _schedule: SetScheduleService,
    private fb: FormBuilder,
    private cookie: NgxEncryptCookieService,
    private router: Router
  ) {
    this.ModifyForm = this.fb.group({
      candidates: ["", Validators.required],
      position: ["", Validators.required],
      date_modify: [this.date_modify, Validators.required],
      time_modify: ["", Validators.required],
      remark: ["", Validators.required],
    });
    this.bsRangeValue = [this.bsValue, this.maxDate];
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
    translate.setDefaultLang("en");
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate());
    // For testing purpose
    // this.idHr = this.cookie.get("id", true, "hrd");
    // Replace with actual HR ID retrieval logic
    this.idHr = "3"; // For testing purpose, replace with actual HR ID retrieval logic
  }

  name = "Angular 6";
  public newTime: string = "13:30";
  time = { hour: 13, minute: 30 };
  onTimeChange(value: { hour: string; minute: string }) {
    this.newTime = `${value.hour}:${value.minute}`;
  }

  get remark() {
    return this.ModifyForm.get("remark");
  }

  //TODO: to select candidate by announcement
  selectCandidateByAnnouncement(e) {
  
    this.announcementId = e.target.value;
    if(this.announcementId==0){
      this.getFirstData(this.idHr, 0);
    }else{
    //TODO set isFilterByDate and isFilterByAnnouncement true or false because we need check when change page
    this.isFilterByDate = false;
    this.isFilterByAnnouncement = true;
    //TODO every on announcement we need to set currentPage to 1 (start page)
    this.currentPage = 1;
    this.isLoading = true;
    this.hrService
      .getCandidateByAnnouncement(e.target.value, this.currentPage - 1)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage=data.pagination.totalPages;
      });
    }

  }

  //TODO: to set schedule
  onSend() {
    this.myLoading = true;
    let id_array = [];
    for (let item of this.ModifyForm.get("candidates").value) {
      let obj = {
        id: item.id,
      };
      id_array.push(obj);
    }

    var modify_date = this.pipe.transform(
      this.ModifyForm.get("date_modify").value,
      "yyyy-MM-dd"
    );

    let modify = {
      candidateIdRequest: id_array,
      emailContent: this.htmlContent,
      hrId: this.idHr,
      meetingDate: modify_date,
      meetingTime: this.ModifyForm.get("time_modify").value,
      position: this.ModifyForm.get("position").value,
      remark: this.ModifyForm.get("remark").value,
    };

    this._schedule
      .postSetSchedule(modify, "jobroom.kshrd@gmail.com", "Jobroomkshrd9",this.companyName)
      .subscribe((res: any) => {
        if (res.message === "Schedule has been inserted successfully") {
          this.myLoading = false;
          this._service.success(res.message, "", {
            timeOut: 2000,
            position: ["bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50,
          });
          this.ModifyForm.reset();
          this.htmlContent = "";
          this.checkUncheckAll();
        } else {
          this.myLoading = false;
          this._service.warn(res.message, "", {
            timeOut: 2000,
            position: ["bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 50,
          });
        }
      });
  }

  ngOnInit() {
    this.hrService.getHrDetails(this.idHr).subscribe((res: any)=>{
      this.companyName = res.data.name;
    })
    this.getFirstData(this.idHr, 0);
    this.hrService.getAnnouncementByCompany(this.idHr, 0, "").subscribe((data) => {
      this.announcements = data.data;
      this.myLoading = false;
    });
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
  }

  //TODO: to get data
  getFirstData(id, currentPage) {
    this.masterSelected = false;
    this.isLoading = true;
    this.isFilterByAnnouncement = false;
    this.isFilterByDate = false;
    this.candidateService
      .getCandidateCompanyId(id, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.isLoading = false;
        for (let item of data.data) {
          let obj = {
            id: item.id,
            name: item.name,
          };
          this.candiates.push(obj);
          this.selectedCandidate.push(obj);
        }
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
      });
  }

  //TODO: ............
  changeDate() {
    var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
    var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
    this.isFilterByDate = true;
    this.isFilterByAnnouncement = false;
    this.currentPage = 1;
    this.isLoading = true;
    this.hrService
      .filterCandidateByDate(this.idHr, start, end, this.currentPage - 1)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage=data.pagination.totalPages;
      });
  }

  //TODO: ...........
  onTableDataChange(event): void {
    this.currentPage = event;
    //TODO check is filter by date is true mean we change page for filter by date
    if (this.isFilterByDate) {
      var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
      var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
      this.isLoading = true;
      this.hrService
        .filterCandidateByDate(this.idHr, start, end, this.currentPage - 1)
        .subscribe((data) => {
          this.data = data.data;
          this.itemPerPage = data.pagination.pageSize;
          this.totalItem = data.pagination.totalItems;
          this.isLoading = false;
          this.totalPage=data.pagination.totalPages;
        });
    } else if (this.isFilterByAnnouncement) {
      this.isLoading = true;
      this.hrService
        .getCandidateByAnnouncement(this.announcementId, this.currentPage - 1)
        .subscribe((data) => {
          this.data = data.data;
          this.itemPerPage = data.pagination.pageSize;
          this.totalItem = data.pagination.totalItems;
          this.isLoading = false;
          this.totalPage=data.pagination.totalPages;
        });
    } else if (this.isSearch) {
      this.isLoading = true;
      this.candidateService
        .searchCandidate(1, this.currentPage - 1, this.searchValue)
        .subscribe((data) => {
          this.data = data.data;
          this.itemPerPage = data.pagination.pageSize;
          this.totalItem = data.pagination.totalItems;
          this.isLoading = false;
          this.totalPage=data.pagination.totalPages;
        });
    } else {
      this.getFirstData(this.idHr, this.currentPage - 1);
    }
  }

  //TODO: to uncheck
  checkUncheckAll() {
    for (var i = 0; i < this.data.length; i++) {
      this.data[i].is_selected = this.masterSelected;
    }
    this.getCheckedItemList();
  }

  //TODO: to selected
  isAllSelected() {
    this.masterSelected = this.data.every(function (item: any) {
      return item.is_selected == true;
    });
    this.getCheckedItemList();
  }

  //TODO: to open chat with new tab
  chat(id:any,name:any){
    let companyId = this.cookie.get("id",true,"hrd")
    this.cookie.delete("receiverId","/",".kshrd-ite.com")
    this.cookie.delete("receiverName","/",".kshrd-ite.com")
    this.cookie.delete("userId","/",".kshrd-ite.com")
    this.cookie.set('userId', id.toString(), true,'hrd', 365,"/", '.kshrd-ite.com')
    this.cookie.set('receiverId', companyId, true,'hrd', 365,"/", '.kshrd-ite.com')
    this.cookie.set('receiverName', name, true,'hrd', 365,"/", '.kshrd-ite.com')
    window.open("https://chat.kshrd-ite.com/", "_blank");
  //  window.location.href="http://localhost:4500"
  }

  //TODO: to check dubplicate position when checked to set schedule
  getCheckedItemList() {
    this.selectedCandidate.length = 0;
    for (var i = 0; i < this.data.length; i++) {
      if (this.data[i].is_selected) {
        let obj = {
          id: this.data[i].id,
          name: this.data[i].name,
          position: this.data[i].announcement.position
        };
        this.selectedCandidate.push(obj);
        for(var j = 0; j < this.selectedCandidate.length; j++){
          if( this.selectedCandidate[j].position !== this.data[i].announcement.position){
            this.isNotPosition = true;
          }
        }
        this.ModifyForm.get("position").setValue(
          this.data[i].announcement.position
        );
      }
    }
    if(this.isNotPosition){
      this.modalRef2 = this.modalService.show(this.confirm, { class: "modal-md" });
      this.isNotPosition = false;
    }
    if (this.selectedCandidate.length >= 2) {
      this.select_one = true;
    } else if (this.selectedCandidate.length < 2) {
      this.select_one = false;
    }
  }

  //TODO: to close confirm modal
  closeConfirm(){
    for (var i = 0; i < this.data.length; i++) {
      this.data[i].is_selected = this.masterSelected;
    }
    this.select_one = false;
  }

  //TODO: to open set schedule modal
  openModalSetSchedule1(templates: TemplateRef<any>) {
    this.modalRef = this.modalService.show(templates, { backdrop: "static" });
  }

  //TODO: to close set schedule modal
  closeModalSetSchedule(template: TemplateRef<any>) {
    this.modalRef1 = this.modalService.show(template, { class: "modal-md" });
  }

  //TODO: to open set schedule modal
  openModalSetSchedule(templates: TemplateRef<any>, id: any) {
    this.ModifyForm.reset();
    this.htmlContent = "";
    this.modalRef = this.modalService.show(templates, { backdrop: "static" });
    this.candiates.length = 0;

    for (let item of this.data) {
      if (item.id === id) {
        this.selectedCandidate.length = 0;
        let obj = {
          id: item.id,
          name: item.name,
        };
        this.ModifyForm.get("position").setValue(item.announcement.position);
        this.selectedCandidate.push(obj);
      }

      let obj1 = {
        id: item.id,
        name: item.name,
      };
      this.candiates.push(obj1);
    }
  }

  //TODO: to search position
  search() {
    this.isSearch = true;
    this.isFilterByDate = false;
    this.isFilterByAnnouncement = false;
    this.currentPage = 1;

    this.isLoading = true;
    this.candidateService
      .searchCandidate(this.idHr, this.currentPage - 1, this.searchValue)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage=data.pagination.totalPages;
      });
  }

  //TODO: to open modal
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  //TODO: to delete announcement
  onDeleteAnnouncement(id) {
    this.hrService.deleteCandidate(id).subscribe((res) => {
      this._service.success(res.message, "", {
        timeOut: 2000,
        position: ["bottom", "right"],
        clickToClose: true,
        clickIconToClose: true,
        animate: "fromRight",
        maxLength: 50,
      });
      this.getFirstData(this.idHr, 0);
    });
  }

  //TODO: to view candidate cv
  onViewCv(cvLink: any){
    if( !isNaN(cvLink)){
      window.open(`https://connex.kshrd-ite.com/cv/${cvLink}`, '_blank');
    }else{
      window.open(`${cvLink}`, '_blank');
    }
  }

}
