import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EcritureComptableResolve from './route/ecriture-comptable-routing-resolve.service';

const ecritureComptableRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ecriture-comptable.component').then(m => m.EcritureComptableComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ecriture-comptable-detail.component').then(m => m.EcritureComptableDetailComponent),
    resolve: {
      ecritureComptable: EcritureComptableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ecriture-comptable-update.component').then(m => m.EcritureComptableUpdateComponent),
    resolve: {
      ecritureComptable: EcritureComptableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ecriture-comptable-update.component').then(m => m.EcritureComptableUpdateComponent),
    resolve: {
      ecritureComptable: EcritureComptableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ecritureComptableRoute;
