import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AffectationUtilisateurResolve from './route/affectation-utilisateur-routing-resolve.service';

const affectationUtilisateurRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/affectation-utilisateur.component').then(m => m.AffectationUtilisateurComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/affectation-utilisateur-detail.component').then(m => m.AffectationUtilisateurDetailComponent),
    resolve: {
      affectationUtilisateur: AffectationUtilisateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/affectation-utilisateur-update.component').then(m => m.AffectationUtilisateurUpdateComponent),
    resolve: {
      affectationUtilisateur: AffectationUtilisateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/affectation-utilisateur-update.component').then(m => m.AffectationUtilisateurUpdateComponent),
    resolve: {
      affectationUtilisateur: AffectationUtilisateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default affectationUtilisateurRoute;
