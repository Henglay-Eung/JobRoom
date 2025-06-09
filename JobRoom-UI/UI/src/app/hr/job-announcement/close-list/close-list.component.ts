import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { DatePipe } from "@angular/common";
import { Component, OnInit, TemplateRef } from "@angular/core";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import { HrService } from "../../shared/hr.service";
import { ClosedAnnouncementService } from "../../shared/closed-announcement.service";
import { BsDatepickerConfig } from "ngx-bootstrap";
import { I18nHrserviceService } from "./../../i18n-hrservice.service";
import { TranslateService } from "@ngx-translate/core";
import { NotificationsService } from "angular2-notifications";

@Component({
  selector: "app-close-list",
  templateUrl: "./close-list.component.html",
  styleUrls: ["../active-list/active-list.component.css"],
})
export class CloseListComponent implements OnInit {
  start_date = new Date();
  end_date = new Date();

  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: "YYYY-MM-DD",
    showWeekNumbers: false,
  };

  endDate = this.end_date;
  startDate = this.start_date;
  isFilterByDate = false;
  language: string = "hello";
  datas = JSON.parse(sessionStorage.getItem("changlang"));
  data = [];
  totalPage;
  itemPerPage;
  totalItem;
  currentPage = 1;
  listPosition;
  isSelectPosition = false;
  position;
  searchCloseAnnouncement = "";
  modalRef: BsModalRef;
  isLoading = false;
  pipe = new DatePipe("en-US");
  idHr: any;

  constructor(
    private modalService: BsModalService,
    private hrService: HrService,
    private close_announcement: ClosedAnnouncementService,
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
    this.idHr = "3"; // Replace with actual HR ID retrieval logic
  }

  //TODO: to open modal
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  //TODO: to all data
  getFirstData() {
    this.isLoading = true;
    this.isSelectPosition = false;
    this.getCloseAnnouncement(
      this.idHr,
      this.searchCloseAnnouncement,
      "",
      this.currentPage - 1
    );
    this.hrService.getPositionByCompany(this.idHr).subscribe((data) => {
      this.listPosition = data.data;
      this.isLoading = false;
    });
  }

  ngOnInit() {
    this.getFirstData();
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        console.log("my locale=" + locale),
        console.log("data=" + this.language),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
  }

  //TODO: to change data in table
  onTableDataChange(event): void {
    this.currentPage = event;
    if (this.isSelectPosition) {
      //TODO Change page (selectPosition)
      this.getCloseAnnouncement(this.idHr, "", this.position, this.currentPage - 1);
    } else if (this.isFilterByDate) {
      //TODO Change page (filter by date)
      var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
      var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
      this.filterByDate(this.idHr, start, end, this.currentPage - 1);
    } else {
      this.getCloseAnnouncement(
        this.idHr,
        this.searchCloseAnnouncement,
        "",
        this.currentPage - 1
      );
    }
  }

  //TODO: to search
  search() {
    this.isSelectPosition = false;
    this.isFilterByDate = false;
    this.currentPage = 1;
    this.getCloseAnnouncement(
      this.idHr,
      this.searchCloseAnnouncement,
      "",
      this.currentPage - 1
    );
  }

  //TODO Select Position
  selectPosition(e) {
    this.isSelectPosition = true;
    this.isFilterByDate = false;
    this.position = e.target.value;
    this.currentPage = 1;
    this.getCloseAnnouncement(this.idHr, "", e.target.value, this.currentPage - 1);
  }

  //TODO: to change date
  changeDate() {
    this.isFilterByDate = true;
    this.isSelectPosition = false;
    var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
    var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
    this.currentPage = 1;
    this.filterByDate(this.idHr, start, end, this.currentPage - 1);
  }

  //TODO: to all data in close announcement
  getCloseAnnouncement(id, caption, position, currentPage) {
    this.isLoading = true;
    this.close_announcement
      .getCloseAnnouncementByCompany(id, caption, position, currentPage)
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
    this.close_announcement
      .filterCloseAnnouncementByStartAndEndDate(id, start, end, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage=data.pagination.totalPages;
      });
  }

  //TODO: delete closed announcement
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
