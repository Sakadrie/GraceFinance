import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EntiteFinanciereResolve from './route/entite-financiere-routing-resolve.service';

const entiteFinanciereRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/entite-financiere.component').then(m => m.EntiteFinanciereComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/entite-financiere-detail.component').then(m => m.EntiteFinanciereDetailComponent),
    resolve: {
      entiteFinanciere: EntiteFinanciereResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/entite-financiere-update.component').then(m => m.EntiteFinanciereUpdateComponent),
    resolve: {
      entiteFinanciere: EntiteFinanciereResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/entite-financiere-update.component').then(m => m.EntiteFinanciereUpdateComponent),
    resolve: {
      entiteFinanciere: EntiteFinanciereResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default entiteFinanciereRoute;
