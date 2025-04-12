import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { Cookie } from 'ng2-cookies';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
  providers: [NgxEncryptCookieService]
})
export class SignInComponent implements OnInit {

  public clientId = 'fIEZTLtFy3YutyKDcFadMeZ3cb5BouLP';
  public redirectUri = 'http://35.197.132.204:31000/home';
  public clientSecret = '9bnm8sV0HwgcMx6pqk9x0WWR9tCzYyGi';
  public provision_key = 'AU3p0FF7yU4mA8thdUGG8xdpOV45Iwjb';
  public authenticated_userid = 'connex';
  username: string = 'string'
  pw: string = 'string'

  form = new FormGroup({
    email: new FormControl('henglayeung@gmail.com', [
      Validators.required,
      Validators.email,
      Validators.pattern('')
    ]),
    password: new FormControl('password', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(128),
    ]),
  });
  constructor(public fb: FormBuilder, private auth: AuthService, private router: Router, private cookie: NgxEncryptCookieService,) { }

  get password() {
    return this.form.get('password')
  }

  get email() {
    return this.form.get('email')
  }

  ngOnInit() {
  }

  //TODO: login
  login() {
    let user = {
      "client_id": this.clientId,
      "client_secret": this.clientSecret,
      "username": this.username,
      "password": this.pw,
      "grant_type": "password",
      "scope": "email",
      "provision_key": this.provision_key,
      "authenticated_userid": "connex"
    }
    //For testing
    window.location.href = 'http://localhost:4200'

    this.auth.retrieveToken(user).subscribe((data: any) => {
      this.checkUser(this.form.get('email').value, this.form.get('password').value)
      this.saveCredential('token', data.access_token),
        (err: any) => alert('Invalid Credentials')
    });
  }

  //TODO: check user credential
  checkUser(email, password) {
    this.auth.checkUser(email, password).subscribe((data: any) => { 
      if (data.data === null) {
        alert(data.message)
      }
      else {
        let userId = data.data.userId.toString()

        this.cookie.set('userId', userId,true,"hrd",365,"/",".kshrd-ite.com")
        this.cookie.set('id',userId,true,"hrd",365,"/",".kshrd-ite.com")
        
        this.saveCredential('role',data.data.role)
        if (data.data.role === 'company') {
          window.location.href = 'https://jobroom.kshrd-ite.com/hr/dashboard'
        } 
        else{
          window.location.href = 'https://connex.kshrd-ite.com/home'
        }
      }
    })
  }

  //TODO: save credential in cookie
  saveCredential(name, token) {
    this.cookie.set(name, token, true, "hrd",365,"/",".kshrd-ite.com")
  }

  //TODO: submit form
  onSubmit() {
    this.login()
  }

  //TODO: redirect to sign up employee
  toSignUpEmployee() {
    this.router.navigate(['/sign-up-employee'])
  }

  //TODO: redirect to sign up hr
  toSignUpHR() {
    this.router.navigate(['/sign-up-hr'])
  }
}
