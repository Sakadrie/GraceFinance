import { Routes } from '@angular/router';

import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { errorRoute } from './layouts/error/error.route';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';

const routes: Routes = [
  // {
  //   path: '',
  //   loadComponent: () => import('./home/home.component'),
  //   title: 'home.title',
  // },
  // {
  //   path: '',
  //   loadComponent: () => import('./layouts/navbar/navbar.component'),
  //   outlet: 'navbar',
  // },
  // {
  //   path: 'admin',
  //   data: {
  //     authorities: [Authority.ADMIN],
  //   },
  //   canActivate: [UserRouteAccessService],
  //   loadChildren: () => import('./admin/admin.routes'),
  // },
  // {
  //   path: 'account',
  //   loadChildren: () => import('./account/account.route'),
  // },
  // {
  //   path: 'login',
  //   loadComponent: () => import('./login/login.component'),
  //   title: 'login.title',
  // },
  // {
  //   path: '',
  //   loadChildren: () => import(`./entities/entity.routes`),
  // },
  // ...errorRoute,
  {
    path: '',
    component: AdminLayoutComponent,
    // canActivate: [AuthGuard],
    resolve: {
      // account: AccountDataResolve,
    },
    children: [
      // { path: '', redirectTo: '/auth/signin', pathMatch: 'full' },
      {
        path: 'admin',
        loadChildren: () => import('./administration/administration.routes').then(m => m.ADMINISTRATION_ROUTE),
      },
    ],
  },
  // {
  //   path: 'auth',
  //   resolve: {
  //     account: AccountDataResolve,
  //   },
  //   loadChildren: () => import('./authentication/authentication.routes').then(m => m.AUTHENTICATION_ROUTE),
  // },
  // { path: '**', component: Error404Component },
];

export default routes;
