import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
// import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';

export const SECURITY_ROUTES: Route[] = [
  {
    path: LISTE_ADMINISTRATION_ROUTES.SECURITY.PROFIL.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./profil/profil.routes').then(m => m.PROFIL_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.SECURITY.DROITS.PATH,
    //   data: {
    //     authorities: [Authority.USER],
    //    },
    //    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./droit/droit.routes').then(m => m.DROIT_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.SECURITY.AFFECTATION_UTILISATEUR.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./affectation-utilisateur/affectation-utilisateur.routes').then(m => m.AFFECTATION_UTILISATEUR_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.SECURITY.USER.PATH,
    //   data: {
    //     authorities: [Authority.USER],
    //    },
    //    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./compte-utilisateur/compte-utilisateur.routes').then(m => m.COMPTE_UTILISATEUR_ROUTE),
  },
];
