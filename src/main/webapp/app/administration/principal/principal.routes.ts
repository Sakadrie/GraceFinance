import { Route } from '@angular/router';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
// import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';

export const PRINCIPAL_ROUTE: Route[] = [
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./caisse/caisse.routes').then(m => m.CAISSE_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.COMPTE_COMPTABLE.PATH,
    // data: {
    //     authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./compte-comptable/compte-comptable.routes').then(m => m.COMPTE_COMPTABLE_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.PATH,
    // data: {
    //     authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./depense/depense.routes').then(m => m.DEPENSE_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.PATH,
    // data: {
    //     authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./ecriture-comptable/ecriture-comptable.routes').then(m => m.ECRITURE_COMPTABLE_ROUTE),
  },

  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ENTITE_FINACIERE.PATH,
    // data: {
    //     authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./entite-financiere/entite-financiere.routes').then(m => m.ECRITURE_FINANCIERE_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.LIGNE_ECRITURE.PATH,
    // data: {
    //     authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./ligne-ecriture/ligne-ecriture.routes').then(m => m.LIGNE_ECRITURE_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.PATH,
    // data: {
    //     authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./recette/recette.routes').then(m => m.RCETTE_ROUTE),
  },
];
