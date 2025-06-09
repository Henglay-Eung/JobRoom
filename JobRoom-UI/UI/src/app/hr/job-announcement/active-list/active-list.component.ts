import { NgxEncryptCookieService } from "ngx-encrypt-cookie";
import { ShareData } from "./../../share-data";
import { ShareAnnouncementService } from "./../../shared/share-announcement.service";
import { DatePipe } from "@angular/common";
import {
  Component,
  OnInit,
  TemplateRef,
  ViewEncapsulation,
} from "@angular/core";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import { HrService } from "../../shared/hr.service";
import { ActiveAnnouncementService } from "../../shared/active-announcement.service";
import { BsDatepickerConfig } from "ngx-bootstrap";
import { I18nHrserviceService } from "./../../i18n-hrservice.service";
import { TranslateService } from "@ngx-translate/core";
import { NotificationsService } from "angular2-notifications";
import { AnonymousSubject } from "rxjs/internal/Subject";
import { post } from "jquery";

@Component({
  selector: "app-active-list",
  templateUrl: "./active-list.component.html",
  styleUrls: ["./active-list.component.css"],
})
export class ActiveListComponent implements OnInit {
  //varialble new caption
  newcaption: string;
  //declare data for share announcement
  postData = {
    //varaible flexible
    caption: "",
    //from announcement
    images: [],
    //job announcement true
    jobAnnouncement: true,
    //jobAnnouncement idg
    jobAnnouncementID: 40,
    //share variable is true
    shared: true,
    //static variable
    userId: "",
  };
  id: number;
  sharedata: ShareData;
  disablePublish = true;
  //data as array for testing

  //default image of share announcement
  thumbnails = "assets/img/default-thumbnail.jpg";
  //declarationfile variable
  file;
  isLoading = false;
  data;
  second: string = "Active announcement";
  first: string = "Job announcement";

  //variable for switch language
  language: string = "hello";
  datas = JSON.parse(sessionStorage.getItem("changlang"));
  start_date = new Date();
  end_date = new Date();
  isFilterByDate = false;
  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: "YYYY-MM-DD",
    showWeekNumbers: false,
  };
  pipe = new DatePipe("en-US");
  endDate = this.end_date;
  startDate = this.start_date;
  totalPage;
  itemPerPage;
  totalItem;
  isSelectPosition = false;
  currentPage = 1;
  modalRef: BsModalRef;
  listPosition;
  searchActiveAnnouncement = "";
  position;
  isChooseOptionHasSelect = false;
  idHr: any;
  err = [];

  constructor(
    private ShareAnnouncement: ShareAnnouncementService,
    private modalService: BsModalService,
    private hrService: HrService,
    private active_announcement: ActiveAnnouncementService,
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

  //TODO: to get data to caption
  onChangeCaption(e) {
    this.postData.caption = e.target.value;
  }

  //TODO: to open modal
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  //TODO: to change local file
  onLocalFileChange(e) {
    if (e.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (event: any) => {
        this.thumbnails = event.target.result;
        // upload file pdf to store file
        this.ShareAnnouncement.uploadcvfile(e.target.files[0]).subscribe(
          (data: any) => {
            this.postData.images.push(data.file1);
          }
        );
      };
    }
  }
  openModalShare(templateShare: TemplateRef<any>, id: number) {
    this.modalRef = this.modalService.show(templateShare);
    //get uuid from compnay
    this.ShareAnnouncement.getcompanyuuidbyCompanyId(this.idHr).subscribe(
      (data: any) => {
        //assign uuid to userid
        this.postData.userId = data.data.uuid;
      }
    );
    this.ShareAnnouncement.getannouncementbyId(id).subscribe((data: any) => {
      //pass id to announcement id
      this.postData.jobAnnouncementID = id;
    });
  }

  //TODO: delete active announcement
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

  //TODO: share active announcement to connext
  onShareAnnouncement() {
    this.ShareAnnouncement.postData(this.postData).subscribe((data: any) => {
      this._service.success(" Applied Successfully!", "", {
        timeOut: 2000,
        position: ["top", "right"],
        clickToClose: true,
        clickIconToClose: true,
        animate: "fromRight",
        maxLength: 100,
      });
    });
  }

  //TODO: to get active announcement
  getFirstData() {
    this.isLoading = true;
    this.isSelectPosition = false;
    this.getActiveAnnouncement(
      this.idHr,
      this.searchActiveAnnouncement,
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
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
  }

  //TODO: to change data when pagination
  onTableDataChange(event): void {
    this.currentPage = event;
    if (this.isSelectPosition) {
      //TODO Change page (selectPosition)
      this.getActiveAnnouncement(
        this.idHr,
        "",
        this.position,
        this.currentPage - 1
      );
    } else if (this.isFilterByDate) {
      //TODO Change page (filter by date)
      var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
      var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
      this.filterByDate(this.idHr, start, end, this.currentPage - 1);
    } else {
      this.getActiveAnnouncement(
        this.idHr,
        this.searchActiveAnnouncement,
        "",
        this.currentPage - 1
      );
    }
  }

  //TODO: to search position
  search() {
    this.isSelectPosition = false;
    this.isFilterByDate = false;
    this.currentPage = 1;
    this.getActiveAnnouncement(
      this.idHr,
      this.searchActiveAnnouncement,
      "",
      this.currentPage - 1
    );
  }

  //TODO: Select Position
  selectPosition(e) {
    this.isSelectPosition = true;
    this.isFilterByDate = false;
    this.position = e.target.value;
    this.currentPage = 1;
    this.getActiveAnnouncement(
      this.idHr,
      "",
      e.target.value,
      this.currentPage - 1
    );
  }

  //TODO: .................
  changeDate() {
    this.isFilterByDate = true;
    this.isSelectPosition = false;
    var end = this.pipe.transform(this.endDate, "yyyy-MM-dd");
    var start = this.pipe.transform(this.startDate, "yyyy-MM-dd");
    this.currentPage = 1;
    this.filterByDate(this.idHr, start, end, this.currentPage - 1);
  }

  //TODO: to get active announcement
  getActiveAnnouncement(id, caption, position, currentPage) {
    this.isLoading = true;
    this.active_announcement
      .getActiveAnnouncementByCompany(id, caption, position, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage = data.pagination.totalPages;
      });
  }

  //TODO: to filter date
  filterByDate(id, start, end, currentPage) {
    this.isLoading = true;
    this.active_announcement
      .filterAnnouncementByStartAndEndDate(id, start, end, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage = data.pagination.totalPages;
      });
  }
}
