import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpEmployeeComponent } from './sign-up-employee/sign-up-employee.component';
import { SignUpHrComponent } from './sign-up-hr/sign-up-hr.component';


const routes: Routes = [
  { path: '', component: SignInComponent },
  { path: 'sign-up-employee', component:SignUpEmployeeComponent },
  { path: 'sign-up-hr', component:SignUpHrComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
