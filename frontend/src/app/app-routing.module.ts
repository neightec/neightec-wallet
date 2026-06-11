import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NeightecHomeComponent } from './component/neightec-home/neightec-home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

const routes: Routes = 
[
  { path: 'home',           component: NeightecHomeComponent  },
  { path: 'dashboard',      component: DashboardComponent     },
  // { path: 'login',    component: NeightTechLoginComponent },
  // { path: '**',       component: NeightWeddingPagenotfoundComponent },    
  { path: '',         redirectTo: '/dashboard', pathMatch: 'full' }, //need to be declared before PageNotFound
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],

  exports: [RouterModule]
})
export class AppRoutingModule { }
