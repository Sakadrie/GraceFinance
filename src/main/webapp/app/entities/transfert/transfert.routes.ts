import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import TransfertResolve from './route/transfert-routing-resolve.service';

const transfertRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/transfert.component').then(m => m.TransfertComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/transfert-detail.component').then(m => m.TransfertDetailComponent),
    resolve: {
      transfert: TransfertResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/transfert-update.component').then(m => m.TransfertUpdateComponent),
    resolve: {
      transfert: TransfertResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/transfert-update.component').then(m => m.TransfertUpdateComponent),
    resolve: {
      transfert: TransfertResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default transfertRoute;
