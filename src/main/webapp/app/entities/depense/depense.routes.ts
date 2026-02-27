import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DepenseResolve from './route/depense-routing-resolve.service';

const depenseRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/depense.component').then(m => m.DepenseComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/depense-detail.component').then(m => m.DepenseDetailComponent),
    resolve: {
      depense: DepenseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/depense-update.component').then(m => m.DepenseUpdateComponent),
    resolve: {
      depense: DepenseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/depense-update.component').then(m => m.DepenseUpdateComponent),
    resolve: {
      depense: DepenseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default depenseRoute;
