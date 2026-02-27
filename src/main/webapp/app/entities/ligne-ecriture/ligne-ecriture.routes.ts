import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import LigneEcritureResolve from './route/ligne-ecriture-routing-resolve.service';

const ligneEcritureRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ligne-ecriture.component').then(m => m.LigneEcritureComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ligne-ecriture-detail.component').then(m => m.LigneEcritureDetailComponent),
    resolve: {
      ligneEcriture: LigneEcritureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ligne-ecriture-update.component').then(m => m.LigneEcritureUpdateComponent),
    resolve: {
      ligneEcriture: LigneEcritureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ligne-ecriture-update.component').then(m => m.LigneEcritureUpdateComponent),
    resolve: {
      ligneEcriture: LigneEcritureResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ligneEcritureRoute;
