import { I18nServiceService } from "./../i18n-service.service";
import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { TranslateService } from "@ngx-translate/core";

@Component({
  selector: 'app-sign-up-hr',
  templateUrl: './sign-up-hr.component.html',
  styleUrls: ['./sign-up-hr.component.css']
})
export class SignUpHrComponent implements OnInit {
  hr_signup_image: string = '/assets/back_image.png';
  hrimage: string = '/assets/hr_image.png';

  title = 'ngx-i18n';
  language:string="";
  data= JSON.parse(sessionStorage.getItem("changlang"));



  form = new FormGroup({
    name: new FormControl('',
      [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
    primaryEmail: new FormControl('',
      [
        Validators.required,
        Validators.email,
      ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(20),
    ]),
    secondaryEmail: new FormControl('',
      [
        Validators.required,
        Validators.email,
      ]),
    telephone: new FormControl('',
      [
        Validators.required,
        Validators.maxLength(10),
      ]),
    street: new FormControl('',
      [
        Validators.required,
      ]),
    commune: new FormControl('',
      [
        Validators.required,
      ]),
    district: new FormControl('',
      [
        Validators.required,
      ]),
    city: new FormControl('',
      [
        Validators.required,
      ])
  });

  constructor(private auth: AuthService, private router: Router,private translate: TranslateService,
    private i18nService: I18nServiceService
) { 
     this.translate.setDefaultLang(this.data);
     this.translate.use(this.data);

  }
  changeLocale(locale: string) {
    this.i18nService.changeLocale(locale); 
    this.language=locale;
    sessionStorage.setItem('changlang',JSON.stringify(this.language));
  }



  ngOnInit() {
     // this.checkLanguage();

  }
  //link to login
  toLogin() {
    this.router.navigate([''])
  }
    //TODO: submit
  onSubmit() {

    this.auth.signUpHR(this.form.value).subscribe((data: any) => {
      this.router.navigate([''])
    })
  }
}