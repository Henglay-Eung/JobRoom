import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _http: HttpClient) { }

  retrieveToken(user: any) {
    return this._http.post('https://gateway.kshrd-ite.com/root/post/oauth2/token', user);
  }

  checkUser(email:any, password:any) {
    return this._http.post(`https://gateway.kshrd-ite.com/auth/login`, {
      email: email,
      password: password
    });
  }

  signUpEmployee(employee:any){
    return this._http.post('https://gateway.kshrd-ite.com/auth/register/employee',employee);
  }

  signUpHR(hr:any){
    return this._http.post('http://localhost:8083/register/hr',hr);
  }
}
