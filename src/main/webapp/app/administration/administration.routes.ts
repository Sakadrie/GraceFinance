import { Route } from '@angular/router';
import { Authority } from '../config/authority.constants';
import { UserRouteAccessService } from '../core/auth/user-route-access.service';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
// import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';

export const ADMINISTRATION_ROUTE: Route[] = [
  {
    path: LISTE_ADMINISTRATION_ROUTES.DASHBOARD.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./dashboard/dashboard.routes').then(m => m.DashboardRoutingModule.routes),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./principal/principal.routes').then(m => m.PRINCIPAL_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./referentiel/referentiel.routes').then(m => m.REFERENTIEL_ROUTE),
  },
  {
    path: LISTE_ADMINISTRATION_ROUTES.SECURITY.PATH,
    // data: {
    //   authorities: [Authority.USER],
    // },
    // canActivate: [UserRouteAccessService],
    loadChildren: () => import('./security/security.routes').then(m => m.SECURITY_ROUTES),
  },
];
