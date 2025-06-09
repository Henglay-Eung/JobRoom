import { CreateAnnouncementService } from './../../shared/create-announcement.service';
import { ActivatedRoute } from "@angular/router";
import { Component, OnInit, Input } from "@angular/core";
import { I18nHrserviceService } from "./../../i18n-hrservice.service";
import { Observable, Observer } from "rxjs";
import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";
import { TranslateService } from "@ngx-translate/core";


@Component({
  selector: "app-view-announcement",
  templateUrl: "./view-announcement.component.html",
  styleUrls: ["./view-announcement.component.css"],
})
export class ViewAnnouncementComponent implements OnInit {
  @Input() second: string;
  @Input() first: string;

  language: string = "hello";
  id: any;

  data = JSON.parse(sessionStorage.getItem("changlang"));

  base64TrimmedURL: string;
  base64DefaultURL: string;
  generatedImage: string;
  header: any;
  body: any;
  footer: any;
  myLoading: boolean = false;

  announcement = [];
  announcement_pdf = [];

  constructor(
    translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private _activatedRoute: ActivatedRoute,
    private ja: CreateAnnouncementService
  ) {

    this.id = this._activatedRoute.snapshot.params.id;
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
    translate.setDefaultLang(this.data);
    translate.use(this.data);
    translate.setDefaultLang("en");
  }


  ngOnInit(): void {
    this.myLoading = true;
    this.ja.getJobAnnouncementById(this.id).subscribe((res: any)=>{
      this.announcement.push(res.data);
      this.myLoading = false;
      this.announcement_pdf.push(res.data.form);
      for (let item of this.announcement_pdf) {
        this.getImage(`${item.header.logo}`);
        this.header = item.header;
        this.body = item.body;
        this.footer = item.footer;
      }
    })
    this.i18nService.localeEvent.subscribe((locale) => {
      (this.language = locale),
        sessionStorage.setItem("changlang", JSON.stringify(this.language));
    });
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
      // create an image object
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
        font: "Roboto",
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
