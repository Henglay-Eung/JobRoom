import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { TranslateService } from "@ngx-translate/core";
import { I18nServiceService } from "./../i18n-service.service";

@Component({
  selector: 'app-sign-up-employee',
  templateUrl: './sign-up-employee.component.html',
  styleUrls: ['./sign-up-employee.component.css']
})
export class SignUpEmployeeComponent implements OnInit {

  constructor(private router: Router, private auth: AuthService,private translate: TranslateService,
    private i18nService: I18nServiceService) {
      this.translate.setDefaultLang(this.data);
      this.translate.use(this.data);
    }
  title = 'ngx-i18n';
  language:string="";
  data= JSON.parse(sessionStorage.getItem("changlang"));

  ngOnInit() {
  }
  changeLocale(locale: string) {
    this.i18nService.changeLocale(locale); 
    this.language=locale;
    sessionStorage.setItem('changlang',JSON.stringify(this.language));
  }
  
  loginForm = new FormGroup({
    fullName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  get f() {
    return this.loginForm.controls
  }

  toLogin() {
    this.router.navigate([''])
  }

  //TODO: submit
  onSubmit() {
    this.auth.signUpEmployee(this.loginForm.value).subscribe((data: any) => {
      console.log(data);
      this.router.navigate([''])
    })
  }

}
