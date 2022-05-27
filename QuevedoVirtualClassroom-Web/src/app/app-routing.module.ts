import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./pages/login/login.component";
import {ResourcesListComponent} from "./pages/resources/resourcesList.component";
import {UploadComponent} from "./pages/upload/upload.component";
import {UsersLIstComponent} from "./pages/users/usersLIst.component";
import {ClassroomsListComponent} from "./pages/classrooms/classroomsList.component";


const routes: Routes = [
  {path: 'classrooms', component: ClassroomsListComponent},
  {path: 'resources', component: ResourcesListComponent},
  {path: 'upload', component: UploadComponent},
  {path: 'users', component: UsersLIstComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: '**', pathMatch: 'full', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
