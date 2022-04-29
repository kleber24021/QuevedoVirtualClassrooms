import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { VideosComponent } from './pages/videos/videos.component';
import { UploadComponent } from './pages/upload/upload.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'video/:id', component: VideosComponent},
  {path: 'upload', component: UploadComponent},
  {path: '**', pathMatch: 'full', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
