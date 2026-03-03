import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
// import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';

export const REFERENTIEL_ROUTE: Route[] = [
  {
    path: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./categorie/categorie.routes').then(m => m.CATEGORIE_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.PATH,
    //   data: {
    //     authorities: [Authority.USER],
    //    },
    //    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./transfert/transfert.routes').then(m => m.TRANSFERT_ROUTE),
  },
];
