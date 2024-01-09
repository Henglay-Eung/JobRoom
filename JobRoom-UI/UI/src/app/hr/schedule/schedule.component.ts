import { HrService } from './../shared/hr.service';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { CandidateService } from "./../shared/candidate.service";
import { DatePipe } from "@angular/common";
import { I18nHrserviceService } from "./../i18n-hrservice.service";

import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
  OnInit,
} from "@angular/core";
import { startOfDay, isSameDay, isSameMonth, subDays } from "date-fns";
import { Subject } from "rxjs";

import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from "angular-calendar";

import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import { AngularEditorConfig } from "@kolkov/angular-editor";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { BsDatepickerConfig } from "ngx-bootstrap";
import { SetScheduleService } from "../shared/set-schedule.service";
import { NotificationsService } from "angular2-notifications";
import * as events from "events";

@Component({
  selector: "app-schedule",
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: "./schedule.component.html",
  styleUrls: ["./schedule.component.css"],
})
export class ScheduleComponent implements OnInit {
  language: string = "hello";
  data = JSON.parse(sessionStorage.getItem("changlang"));
  ModifyForm: FormGroup;
  modalRef: BsModalRef;
  modalRef1: BsModalRef;
  pipe = new DatePipe("en-US");

  bsValue = new Date();
  bsRangeValue: Date[];
  maxDate = new Date();
  minDate: Date;
  date_modify = new Date();

  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: "YYYY-MM-DD",
    showWeekNumbers: false,
  };

  idUpdate: any;
  isLoading: boolean = false;

  masterSelected: boolean;
  checklist: any;
  checkedList: any;
  candiates = [];
  selectedCandidate = [];
  select_all = false;
  select_one = false;
  htmlContent = "";

  idHr: any;
  emailHr: string;
  passwordHr: string;

  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: "5rem",
    minHeight: "10rem",
    placeholder: "Enter text here...",
    translate: "no",
    defaultParagraphSeparator: "p",
    defaultFontName: '"Roboto", "Siemreap", sans-serif',
    outline: false,
    toolbarHiddenButtons: [
      [
        // "undo",
        // "redo",
        "strikeThrough",
        "subscript",
        "superscript",
        // 'justifyLeft',
        // 'justifyCenter',
        // 'justifyRight',
        // "justifyFull",
        "indent",
        "outdent",
        // 'insertUnorderedList',
        // 'insertOrderedList',
        // 'heading',
        // 'fontName'
      ],
      [
        // 'fontSize',
        // "textColor",
        // "backgroundColor",
        "customClasses",
        // "link",
        "unlink",
        // "insertImage",
        "insertVideo",
        "insertHorizontalRule",
        // "removeFormat",
        "toggleEditorMode",
      ],
    ],
    customClasses: [
      {
        name: "quote",
        class: "quote",
      },
      {
        name: "redText",
        class: "redText",
      },
      {
        name: "titleText",
        class: "titleText",
        tag: "h1",
      },
    ],
  };

  @ViewChild("templates", { static: true }) templates: TemplateRef<any>;

  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();
  companyName: string;

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  refresh: Subject<any> = new Subject();
  events: CalendarEvent[] = [];
  activeDayIsOpen: boolean = true;

  constructor(
    private modalService: BsModalService,
    private formBuilder: FormBuilder,
    translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private fb: FormBuilder,
    private _schedule: SetScheduleService,
    private candidateService: CandidateService,
    private _service: NotificationsService,
    private cookie: NgxEncryptCookieService,
    private hrService: HrService
  ) {

    this.ModifyForm = this.fb.group({
      candidates: ["", Validators.required],
      position: ["", Validators.required],
      date_modify: [this.date_modify, Validators.required],
      time_modify: ["",Validators.required],
      remark: ["", Validators.required],
    });

    this.maxDate.setDate(this.maxDate.getDate() + 7);
    this.bsRangeValue = [this.bsValue, this.maxDate];

    translate.setDefaultLang(this.data);
    translate.use(this.data);
    translate.setDefaultLang("ar");
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate());
    this.idHr = this.cookie.get("id",true,"hrd");
  }

  //TODO: to get all data
  getFirstData(id) {
    this._schedule.getScheduleByCompanyId(id).subscribe((res: any) => {
      for (let item of res.data) {
        let schedule = {
          id: item.id,
          start: new Date(item.meetingDate),
          title: item.remark,
          actions: this.actions,
        };
        this.events.push(schedule);
      }
    });
  }

  ngOnInit(): void {
    this.hrService.getHrDetails(this.idHr).subscribe((res: any)=>{
      this.companyName = res.data.name;
    })
    this.getFirstData(this.idHr);
    this._schedule.refreshNeed.subscribe(() => {
      this.events.length = 0;
      this._schedule.getScheduleByCompanyId(this.idHr).subscribe((res: any) => {
        for (let item of res.data) {
          let schedule = {
            id: item.id,
            start: new Date(item.meetingDate),
            title: item.remark,
            actions: this.actions,
          };
          this.events.push(schedule);
        }
      });
    });
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
  }

  //TODO: to udpate set schedule
  onUpdate() {
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
      remark: this.ModifyForm.get("remark").value
    };

    this._schedule
      .updateSchedule(
        modify,
        "jobroom.kshrd@gmail.com",
        "Jobroomkshrd9",
        this.idUpdate,
        this.companyName
      )
      .subscribe((res: any) => {
        if (res.message == "Schedule has been updated successfully") {
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
      });
  }

  //TODO: has action button 2 are "edit" and "delete"
  actions: CalendarEventAction[] = [
    {
      label: '<i class="fas fa-fw fa-pencil-alt"></i>',
      a11yLabel: "Edit",
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.htmlContent = "";
        this.selectedCandidate.length = 0;
        this._schedule.getScheduleById(event.id).subscribe((res: any) => {
          this.idUpdate = res.data.id;
          this.candidateService
            .getCandidateById(res.data.candidateId)
            .subscribe((data: any) => {
              let obj = {
                id: data.data.id,
                name: data.data.name,
              };
              this.selectedCandidate = [...this.selectedCandidate, obj];
            });
          this.ModifyForm.get("remark").patchValue(res.data.remark);
          this.ModifyForm.get("position").patchValue(res.data.position);
          this.ModifyForm.get("time_modify").patchValue(res.data.meetingTime);
          this.ModifyForm.get("date_modify").patchValue(res.data.meetingDate);
          this.htmlContent = res.data.emailContent;
        });
        this.modalRef = this.modalService.show(this.templates, {
          backdrop: "static",
        });
      },
    },
    {
      label: '<i class="fas fa-fw fa-trash-alt"></i>',
      a11yLabel: "Delete",
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this._schedule.deleteSchedule(event.id).subscribe((res: any)=>{
          if(res.message == "Schedule has been deleted successfully"){
            this._service.success(res.message, "", {
              timeOut: 2000,
              position: ["bottom", "right"],
              clickToClose: true,
              clickIconToClose: true,
              animate: "fromRight",
              maxLength: 50,
            });
          }
        });
        this.events = this.events.filter((iEvent) => iEvent !== event);
        this.activeDayIsOpen = false;
      },
    },
  ];

  get remark() {
    return this.ModifyForm.get("remark");
  }

  //TODO: to close set schedule modal
  closeModalSetSchedule(template: TemplateRef<any>) {
    this.modalRef1 = this.modalService.show(template, { class: "modal-md" });
  }

  //TODO: to click to detail each day
  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({ event, newStart }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
        };
      }
      return iEvent;
    });
    this.handleEvent("Dropped or resized", event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }
}
