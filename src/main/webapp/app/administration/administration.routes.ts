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
  //     path: LISTE_ADMINISTRATION_ROUTES.ACTEURS.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./acteurs/acteurs.routes').then(m => m.ACTEURS_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.AGENTS.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./agents/agents.routes').then(m => m.AGENTS_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.BESC.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./besc/besc.routes').then(m => m.BESC_ROUTE),
  //   },

  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.MON_COMPTE.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./mon-compte/mon-compte.routes').then(m => m.MON_COMPTE_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.NEWS.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./news/news.routes').then(m => m.NEWS_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.PAYMENT.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./payment/payment.routes').then(m => m.PAYMENT_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./referentiel/referentiel.routes').then(m => m.REFERENTIEL_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.SECURITY.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./security/security.routes').then(m => m.SECURITY_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.SUPER_ADMIN.PATH,
  //     // data: {
  //     //   authorities: [Authority.ADMIN],
  //     // },
  //     // canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./super-admin/super-admin.routes').then(m => m.SUPER_ADMIN_ROUTE),
  //   },
  //   {
  //     path: LISTE_ADMINISTRATION_ROUTES.SUPPORTS.PATH,
  //     data: {
  //       authorities: [Authority.USER],
  //     },
  //     canActivate: [UserRouteAccessService],
  //     loadChildren: () => import('./supports/supports.routes').then(m => m.SUPPORTS_ROUTE),
  //   },
];
