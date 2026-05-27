import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NeightecHomeComponent } from './component/neightec-home/neightec-home.component';

const routes: Routes = [
  { path: 'home',     component: NeightecHomeComponent },
  // { path: 'login',    component: NeightTechLoginComponent },
  // { path: '**',       component: NeightWeddingPagenotfoundComponent },    
  { path: '',         redirectTo: '/home', pathMatch: 'full' }, //need to be declared before PageNotFound
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],

  exports: [RouterModule]
})
export class AppRoutingModule { }
