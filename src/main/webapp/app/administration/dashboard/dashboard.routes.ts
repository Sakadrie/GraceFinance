import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { DashboardComponent } from './dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    title: LISTE_ADMINISTRATION_ROUTES.DASHBOARD.TITRE,
    // children: [
    //   {
    //     path: '',
    //     title: LISTE_ADMINISTRATION_ROUTES.DASHBOARD.TITRE,
    //     canActivate: [UserRouteAccessService],
    //     loadComponent: () => import('../dashboard/dashboard.component').then(m => m.DashboardComponent),
    //   },
    // ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {
  static routes = routes;
}
