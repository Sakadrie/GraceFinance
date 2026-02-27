import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CompteComptableResolve from './route/compte-comptable-routing-resolve.service';

const compteComptableRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/compte-comptable.component').then(m => m.CompteComptableComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/compte-comptable-detail.component').then(m => m.CompteComptableDetailComponent),
    resolve: {
      compteComptable: CompteComptableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/compte-comptable-update.component').then(m => m.CompteComptableUpdateComponent),
    resolve: {
      compteComptable: CompteComptableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/compte-comptable-update.component').then(m => m.CompteComptableUpdateComponent),
    resolve: {
      compteComptable: CompteComptableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default compteComptableRoute;
