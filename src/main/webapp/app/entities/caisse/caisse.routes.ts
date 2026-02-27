import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CaisseResolve from './route/caisse-routing-resolve.service';

const caisseRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/caisse.component').then(m => m.CaisseComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/caisse-detail.component').then(m => m.CaisseDetailComponent),
    resolve: {
      caisse: CaisseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/caisse-update.component').then(m => m.CaisseUpdateComponent),
    resolve: {
      caisse: CaisseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/caisse-update.component').then(m => m.CaisseUpdateComponent),
    resolve: {
      caisse: CaisseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default caisseRoute;
