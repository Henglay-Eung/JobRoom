import { ShareDataService } from "./../shared/share-data.service";
import { Component, Input, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { I18nServiceService } from "../i18n-service.service";
import { EachAnnouncementService } from "../shared/each-announcement.service";
import { da } from "date-fns/locale";

@Component({
  selector: "app-each-announcement",
  templateUrl: "./each-announcement.component.html",
  styleUrls: ["./each-announcement.component.css"],
})
export class EachAnnouncementComponent implements OnInit {
  language: string = "hello";
  //tempary testing data
  datasss = [
    {
      id: 2,
      position: "developer",
      type: "fulltime",
      createDate: "12-2-2020",
    },
  ];

  datas = JSON.parse(sessionStorage.getItem("changlang"));
  @Input() itemPerPage;
  @Input() totalItem;
  @Input() currentPage = 1;
  @Input() isLoading = false;
  skeleton = new Array(5);
  @Input() data;
  @Input() searchValue;
  @Input() isSearch = false;
  @Input() isFilterByDate = false;
  @Input() totalPage = 0;

  constructor(
    translate: TranslateService,
    private i18nService: I18nServiceService,
    private eachAnnouncementService: EachAnnouncementService,
    private shareData: ShareDataService
  ) {
    translate.setDefaultLang("ar");
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
  }

  ngOnInit() {
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
    this.getAllAnnouncement("", "", "", 0);
    this.eachAnnouncementService.currentPage.subscribe((data) => {
      this.currentPage = data;
    });
    // get share data from slideComponent
    this.shareData.isLoading.subscribe((data) => {
      this.isLoading = data;
    });
    this.shareData.isSearch.subscribe((data) => {
      this.isSearch = data;
    });
    this.shareData.itemPerPage.subscribe((data) => {
      this.itemPerPage = data;
    });

    this.shareData.searchValue.subscribe((data) => {
      this.searchValue = data;
    });
    this.shareData.totalItem.subscribe((data) => {
      this.totalItem = data;
    });
    this.shareData.totalPage.subscribe((data) => {
      this.totalPage = data;
    });
    this.shareData.data.subscribe((data) => {
      this.data = data;
    });
    this.shareData.isFilterByDate.subscribe((data) => {
      this.isFilterByDate = data;
    });
    this.shareData.currentPage.subscribe((data) => {
      this.currentPage = data;
    });
  }

  //TODO: .....
  onTableDataChange(event): void {
    this.currentPage = event;

    if (this.isSearch) {
      this.eachAnnouncementService.searchSource.next(this.currentPage);
    } else if (this.isFilterByDate) {
      //TODO send page value to homepage.component.ts for change page
      this.eachAnnouncementService.filterByDateSource.next(this.currentPage);
    } else {
      //TODO change page (default)
      this.getAllAnnouncement("", "", "", this.currentPage - 1);
    }
  }

  //TODO: to get all announcement
  getAllAnnouncement(startDate, endDate, position, currentPage) {
    this.isLoading = true;
    this.eachAnnouncementService
      .getAllAnnouncement(startDate, endDate, position, currentPage)
      .subscribe((data) => {
        this.data = data.data;
        this.itemPerPage = data.pagination.pageSize;
        this.totalItem = data.pagination.totalItems;
        this.isLoading = false;
        this.totalPage = data.pagination.totalPages;
      });
  }
}
