import { cvdata } from "./../cv";
import { CvApplyService } from "./../shared/cv-apply.service";
import { CreateAnnouncementService } from './../../hr/shared/create-announcement.service';
import { ActivatedRoute } from "@angular/router";
import { Component, OnInit, TemplateRef } from "@angular/core";
import { BsModalService, ModalOptions } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import { NotificationsService } from 'angular2-notifications';
// import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { I18nServiceService } from "../i18n-service.service";
import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";
import { Observable, Observer } from "rxjs";
import { data } from "jquery";
import { NgxEncryptCookieService } from "ngx-encrypt-cookie";

@Component({
  selector: "app-view-detail-announcement",
  templateUrl: "./view-detail-announcement.component.html",
  styleUrls: ["./view-detail-announcement.component.css"],
})
export class ViewDetailAnnouncementComponent implements OnInit {
  title = "ngx-i18n";
  language: string = "";
  data = JSON.parse(sessionStorage.getItem("changlang"));
  id: any;
  //selectted cvid from ng-select
  selectedCVId:number;
  //apply job on announcedata
  appdataTick={
    //dynamic variable
    announcementId: 2,
    //dynamic variable
    companyId: 5,
    //dynamic variable
    cvLink: "ratanacv.com",
    //static variable
    employeeId: 5,
    //static variable
    name: "New Choose browse Candidate"
  }
  localdataTick={
    //dynamic variable
    announcementId: 2,
    //dynamic variable
    companyId: 5,
    //dynamic variable
    cvLink: "",
    //static variable
    employeeId: 5,
    //static variable
    name: "New Local candidate"
  }

    //image base data string
    ImageBaseData:string | ArrayBuffer=null;
    //title = "View Detail";
    url: string;
    //varaible array recieve from getcv by userid
    listcv= [];
    //cvid
    CVid;
    h//userId must get from login
  userId=5;
  file;
  applyForm: any;
  localForm: any;
  isLocalTick = false;
  isAppTick = true;

  base64TrimmedURL: string;
  base64DefaultURL: string;
  generatedImage: string;
  header: any;
  body: any;
  footer: any;
  idHr: any;
  myLoading: boolean = false;
  idCompany: any;
  fileData: any;
  announcement = [];
  myfile:string;
  announcement_pdf = [];

  modalRef: BsModalRef;
  config: ModalOptions = { class: "modal-md" };
  datas = JSON.parse(sessionStorage.getItem("changlang"));

  constructor(
    private cookie: NgxEncryptCookieService,
    private notificationservice: NotificationsService,
    private CvApplyService:CvApplyService,
    private _activatedRoute: ActivatedRoute,
    private modalService: BsModalService,
    private translate: TranslateService,
    private i18nService: I18nServiceService,
    private ja: CreateAnnouncementService,
  ) {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
    this.id = this._activatedRoute.snapshot.params.id;
    this.applyForm = new FormGroup({
      resumes: new FormControl("", [Validators.required]),
    });

    this.localForm = new FormGroup({
      localResumes: new FormControl("", [Validators.required]),
    });
    this.idHr = this.cookie.get("id",true,"hrd");
    translate.setDefaultLang('ar');
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
  }

  ngOnInit(): void {
    this.myLoading = true;
     this.i18nService.localeEvent.subscribe((locale) => {
      this.language = locale,
        sessionStorage.setItem('changlang', JSON.stringify(this.language))
    });

    this.ja.getJobAnnouncementById(this.id).subscribe((res: any)=>{
      this.announcement.push(res.data);
      this.idCompany = res.data.companyId;
      this.myLoading = false;
      this.announcement_pdf.push(res.data.form);
      for (let item of this.announcement_pdf) {
        this.getImage(`${item.header.logo}`);
        this.header = item.header;
        this.body = item.body;
        this.footer = item.footer;
      }
    })
  }

  //TODO: to open modal
  openModal(template: TemplateRef<any>,announcementId,companyId) {
    this.modalRef = this.modalService.show(template, this.config);
    //get all cv by user id to show on list select
      this.CvApplyService.getcvbyid(this.idHr)
      .subscribe(data=>{
        this.listcv=data.data;
      });
    //get emplyee name by id
         this.CvApplyService.getemployeebyid(this.idHr).subscribe((data:any)=>{
          //TODO: pass employee name to employee name
          this.appdataTick.name=data.data.fullName;
          this.localdataTick.name=data.data.fullName;
        });
      this.appdataTick.employeeId=this.idHr;
      this.localdataTick.employeeId=this.idHr;
      this.appdataTick.announcementId=announcementId;
      this.appdataTick.companyId=companyId
      this.localdataTick.announcementId=announcementId;
      this.localdataTick.companyId=companyId;
  }

  //TODO: to get local file
  onLocalFileChange(e){
    if(e.target.files){
      var reader = new  FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload=(event:any)=>{
        //upload file pdf to store file
        this.CvApplyService.uploadcvfile(e.target.files[0]).subscribe((data:any)=>{
            this.localdataTick.cvLink=data.file1;
        });
      }
    }
  }
  
  //TODO: to select change cv
  onSelectResumeChange(id){
    if(this.isAppTick==true){
       this.appdataTick.cvLink=id;
    }
  }
   
  //TODO: to apply cv
  apply() {
    if (this.isAppTick==true){

      this.CvApplyService.postcv(this.appdataTick).then((data:any)=>{
        this.notificationservice.success("Applied Successfully!",'',{
          timeOut: 2000,
          position: [ "top","right"],
          clickToClose: true,
          clickIconToClose: true,
          animate: "fromRight",
          maxLength: 100
        })
        
      });
    }

    else {
      this.CvApplyService.postcv(this.localdataTick).then((data:any)=>{
        this.notificationservice.success(" Applied Successfully!",'',{
          timeOut: 2000,
          position: [ "top","right"],
          clickToClose: true,
          clickIconToClose: true,
          animate: "fromRight",
          maxLength: 100
        })
      });
    }
  }

  onLocalResumeTypeTick(e) {
    this.isAppTick = false;
    this.isLocalTick = true;
  }

  onAppResumeTypeTick(e) {
    this.isAppTick = true;
    this.isLocalTick = false;
  }


  get resumes() {
    return this.applyForm.get("resumes");
  }

  get localResumes() {
    return this.localForm.get("localResumes");
  }

  //TODO: to chat with new tab
  chat(){
    console.log("ADS")
    this.cookie.set("receiverId",this.idCompany,true,"hrd",365,"/",".kshrd-ite.com")
    window.open("https://chat.kshrd-ite.com", "_blank");
  }

  //TODO: to change local string
  changeLocale(locale: string) {
    this.i18nService.changeLocale(locale);
    this.language = locale;
    sessionStorage.setItem("changlang", JSON.stringify(this.language));
  }

  //TODO: download pdf job annnouncement
  /* Method called from the UI */
  getImage = (imageUrl: string) => {
    this.getBase64ImageFromURL(imageUrl).subscribe((base64Data: string) => {
      this.base64TrimmedURL = "data:image/png;base64," + base64Data;
    });
  };

  /* Method to fetch image from Url */
  getBase64ImageFromURL(url: string): Observable<string> {
    return Observable.create((observer: Observer<string>) => {
      let img = new Image();
      img.crossOrigin = "Anonymous";
      img.src = url;
      if (!img.complete) {
        // This will call another method that will create image from url
        img.onload = () => {
          observer.next(this.getBase64Image(img));
          observer.complete();
        };
        img.onerror = (err) => {
          observer.error(err);
        };
      } else {
        observer.next(this.getBase64Image(img));
        observer.complete();
      }
    });
  }

  /* Method to create base64Data Url from fetched image */
  getBase64Image(img: HTMLImageElement): string {
    // We create a HTML canvas object that will create a 2d image
    var canvas: HTMLCanvasElement = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    let ctx: CanvasRenderingContext2D = canvas.getContext("2d");
    // This will draw image
    ctx.drawImage(img, 0, 0);
    // Convert the drawn image to Data URL
    let dataURL: string = canvas.toDataURL("image/png");
    this.base64DefaultURL = dataURL;
    return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
  }

  //TODO: download pdf announcement
  convetToPDF() {
    const documentDefinition = this.getDocumentDefinition();
    pdfMake.createPdf(documentDefinition).download();
  }

  //TODO: get content of pdf
  getDocumentDefinition() {
    return {
      content: [
        {
          image: this.base64TrimmedURL,
          width: 100,
          alignment: "right",
          margin: [0, 0, 0, 20],
        },
        {
          text: this.header.title,
          bold: true,
          fontSize: 15,
          alignment: "center",
          margin: [0, 0, 0, 5],
        },
        this.getHeader(this.header),
        this.getBackground(this.body.background),
        this.getJobRquirement(this.body.job_requirement),
        this.getJobDescription(this.body.job_description),
        this.getHowToApply(this.body.how_to_apply),
        this.getFooter(this.footer.contact),
      ],
      defaultStyle: {
        font: 'Roboto'
      },
      info: {
        title: "Job_announcement",
        subject: "job_announcement",
      },
      styles: {
        header: {
          fontSize: 13,
          bold: true,
          margin: [0, 0, 0, 10],
        },
        body: {
          fontSize: 13,
          bold: true,
          margin: [0, 10, 0, 0],
        },
        ul: {
          margin: [20, 10, 0, 0],
        },
        line: {
          margin: [0, 0, 0, 10],
          bold: true,
        },
      },
    };
  }

   //TODO: get pdf header of announcement
   getHeader(header: any[]) {
    if (header) {
      return [
        {
          text:
            "_______________________________________________________________________________________________",
          style: "line",
        },
        ...Object.entries(header).map(([key, value]) => {
          if (`${key}` !== "logo" && `${key}` !== "title") {
            if(`${value}` !== ""){
              return {
                columns: [
                  {
                    margin: [0, 0, 10, 10],
                    text: `${
                      key.charAt(0).toUpperCase() +
                      key.slice(1).replace(/_/g, " ")
                    }`,
                    bold: true,
                    width: 150,
                  },
                  {
                    margin: [0, 0, 10, 10],
                    text: ": " + `${value}`,
                    width: "auto",
                  },
                ],
              };
            }
          }
        }),
        {
          text:
            "_______________________________________________________________________________________________",
          style: "line",
        },
      ];
    }
    return null;
  }

  //TODO: get pdf background of announcement
  getBackground(background: any) {
    if (background) {
      return [
        {
          text: "BACKGROUND",
          style: "header",
        },
        {
          columns: [
            {
              text: background,
              width: "auto",
            },
          ],
        },
      ];
    }
    return null;
  }

  //TODO: get pdf job requirement of announcement
  getJobRquirement(requirement: any[]) {
    if (requirement.length !== 0) {
      return [
        {
          text: "JOB REQUIREMENT",
          style: "body",
        },
        {
          columns: [
            {
              ul: [...requirement.map((s) => s.list)],
              style: "ul",
            },
          ],
        },
      ];
    }
    return null;
  }

  //TODO: get pdf job description of announcement
  getJobDescription(description: any[]) {
    if (description.length !== 0) {
      return [
        {
          text: "JOB DESCRIPTION",
          style: "body",
        },
        {
          columns: [
            {
              ul: [...description.map((s) => s.list)],
              style: "ul",
            },
          ],
        },
      ];
    }
    return null;
  }

  //TODO: get pdf how to apply of announcement
  getHowToApply(apply: any) {
    if (apply) {
      return [
        {
          text: "HOW TO APPLY",
          style: "body",
        },
        {
          columns: [
            {
              text: apply,
              width: "auto",
              margin: [0, 10, 0, 0],
            },
          ],
        },
      ];
    }
    return null;
  }
 
  //TODO: get pdf footer of announcement
  getFooter(footer) {
    if (footer.address !== "" || footer.website !== "") {
      return [
        {
          text: "CONTACT",
          style: "body",
        },
        ...Object.entries(footer).map(([key, value]) => {
          if (`${value}` !== "") {
            return {
              columns: [
                {
                  margin: [20, 10, 10, 10],
                  text: `${
                    key.charAt(0).toUpperCase() +
                    key.slice(1).replace(/_/g, " ")
                  }`,
                  bold: true,
                  width: 150,
                },
                {
                  margin: [20, 10, 10, 10],
                  text: ": " + `${value}`,
                  width: "auto",
                },
              ],
            };
          }
        }),
      ];
    }
    return null;
  }
}