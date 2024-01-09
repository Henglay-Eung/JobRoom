 import { HrGuard } from './routeGuards/hr.guard';
import { EmployeeGuard } from './routeGuards/employee.guard';
import { CreateAnnouncementComponent } from './hr/job-announcement/create-announcement/create-announcement.component';
import { ViewAnnouncementComponent } from './hr/job-announcement/view-announcement/view-announcement.component';
import { SettingComponent } from './hr/setting/setting.component';
import { CandidateComponent } from './hr/candidate/candidate.component';
import { SaveDraftListComponent } from './hr/job-announcement/save-draft-list/save-draft-list.component';
import { CloseListComponent } from './hr/job-announcement/close-list/close-list.component';
import { DashboardComponent } from './hr/dashboard/dashboard.component';
import { ActiveListComponent } from './hr/job-announcement/active-list/active-list.component';
import { ScheduleComponent } from './hr/schedule/schedule.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivateChild } from '@angular/router';
import { SideBarComponent } from './hr/side-bar/side-bar.component';
import { HomepageComponent } from './employee/homepage/homepage.component';
import { ViewDetailAnnouncementComponent } from './employee/view-detail-announcement/view-detail-announcement.component';

const routes: Routes = [
  {path: '', component: HomepageComponent,canActivate:[EmployeeGuard]},
  // {path: '', component: HomepageComponent},
  {path: 'announcement/view-detail/:id', component : ViewDetailAnnouncementComponent},
  { path: 'hr', component: SideBarComponent,canActivateChild:[HrGuard],
  // { path: 'hr', component: SideBarComponent,
    children: [
      {path:'dashboard',component:DashboardComponent},
      {path:'active-announcement', component:ActiveListComponent},
      {path:'close-announcement', component:CloseListComponent},
      {path:'job-announcement/view/:id', component: ViewAnnouncementComponent},
      {path:'job-announcement/create', component: CreateAnnouncementComponent},
      {path:'save-draft', component: SaveDraftListComponent},
      {path:'candidate', component: CandidateComponent},
      {path:'schedule', component: ScheduleComponent},
      {path:'setting', component: SettingComponent},
    ]
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
