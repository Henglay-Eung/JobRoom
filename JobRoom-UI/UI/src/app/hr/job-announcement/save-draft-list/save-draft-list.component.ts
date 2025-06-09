import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { DatePipe } from "@angular/common";
import { Component, OnInit, TemplateRef } from "@angular/core";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import { SaveDraftAnnouncementService } from "../../shared/save-draft-announcement.service";
import { HrService } from "../../shared/hr.service";
import { Subject } from "rxjs";
import { BsDatepickerConfig } from "ngx-bootstrap";

import { I18nHrserviceService } from "./../../i18n-hrservice.service";
import { TranslateService } from "@ngx-translate/core";
import { NotificationsService } from "angular2-notifications";

@Component({
  selector: "app-save-draft-list",
  templateUrl: "./save-draft-list.component.html",
  styleUrls: ["../active-list/active-list.component.css"],
})
export class SaveDraftListComponent implements OnInit {
  start_date = new Date();
  end_date = new Date();

  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: "YYYY-MM-DD",
    showWeekNumbers: false,
  };

  endDate = this.end_date;
  startDate = this.start_date;

  data;
  isFilterByDate = false;
  language: string = "hello";
  datas = JSON.parse(sessionStorage.getItem("changlang"));
  listPosition;
  itemPerPage;
  totalItem;
  currentPage = 1;
  isSelectPosition = false;
  position;
  searchSaveDraft = "";
  modalRef: BsModalRef;
  isLoading = false;
  pipe = new DatePipe("en-US");
  idHr: any;
  totalPage;

  constructor(
    private modalService: BsModalService,
    private saveDraft: SaveDraftAnnouncementService,
    private hrService: HrService,
    translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private _service: NotificationsService,
    private cookie: NgxEncryptCookieService
  ) {
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
    translate.setDefaultLang("en");
    // For testing purpose
    // this.idHr = this.cookie.get("id", true, "hrd");
    // Replace with actual HR ID retrieval logic
    this.idHr = "3"
  }

  //TODO: to open delete modal
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  //TODO: to get all data
  getFirstData() {
    this.isSelectPosition = false;
    this.getSaveDraft(this.idHr, this.searchSaveDraft, "", this.currentPage - 1);
    this.hrService.getPositionByCompany(this.idHr).subscribe((data) => {
      this.listPosition = data.data;
    });
  }

  ngOnInit() {
    this.getFirstData();
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
  }

  //TODO: to change pagination
  onTableDataChange(event): void {
    this.currentPage = event;
    if (this.isSelectPosition) {
      //TODO Change page (selectPosition)
      this.getSaveDraft(this.idHr, "", this.position, this.currentPage - 1);
    } else if (this.isFilterByDate) {
      //TODO Change page (filter by date)
      var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
      var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
      this.filterByDate(this.idHr, start, end, this.currentPage - 1);
    } else {
      this.getSaveDraft(this.idHr, this.searchSaveDraft, "", this.currentPage - 1);
    }
  }

  //TODO: to search title announcement
  search() {
    this.isSelectPosition = false;
    this.isFilterByDate = false;
    this.currentPage = 1;
    this.getSaveDraft(this.idHr, this.searchSaveDraft, "", this.currentPage - 1);
  }

  //TODO Select Position
  selectPosition(e) {
    this.isSelectPosition = true;
    this.isFilterByDate = false;
    this.position = e.target.value;
    this.currentPage = 1;
    this.getSaveDraft(this.idHr, "", e.target.value, this.currentPage - 1);
  }

  //TODO Filter Draft By Date
  changeDate() {
    this.isFilterByDate = true;
    this.isSelectPosition = false;
    var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
    var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
    this.currentPage = 1;
    this.filterByDate(this.idHr, start, end, this.currentPage - 1);
  }

  //TODO Function getAll Draft
  getSaveDraft(id, caption, position, currentPage) {
    this.isLoading = true;
    this.saveDraft
      .getSaveDraftAnnouncementByCompany(this.idHr, caption, position, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage=data.pagination.totalPages;
      });
  }

  //TODO: to filter date
  filterByDate(id, start, end, currentPage) {
    this.isLoading = true;
    this.saveDraft
      .filterSaveDraftAnnouncementByStartAndEndDate(id, start, end, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage=data.pagination.totalPages;
      });
  }

  //TODO : delete save-draft announcement
  onDeleteAnnouncement(id) {
    this.hrService.deleteAnnouncement(id).subscribe((res) => {
      this._service.success(res.message, "", {
        timeOut: 2000,
        position: ["bottom", "right"],
        clickToClose: true,
        clickIconToClose: true,
        animate: "fromRight",
        maxLength: 50,
      });
      this.getFirstData();
    });
  }
}
