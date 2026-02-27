import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DroitResolve from './route/droit-routing-resolve.service';

const droitRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/droit.component').then(m => m.DroitComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/droit-detail.component').then(m => m.DroitDetailComponent),
    resolve: {
      droit: DroitResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/droit-update.component').then(m => m.DroitUpdateComponent),
    resolve: {
      droit: DroitResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/droit-update.component').then(m => m.DroitUpdateComponent),
    resolve: {
      droit: DroitResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default droitRoute;
