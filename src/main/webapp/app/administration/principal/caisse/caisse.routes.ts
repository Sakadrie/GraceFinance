import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { CaisseComponent } from './caisse.component';

export const CAISSE_ROUTE: Route[] = [
  {
    path: '',
    component: CaisseComponent,
    title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.TITRE,
    // resolve: {
    //   allGroupeProduit: allGroupeProduitResolve,
    // },
  },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.DETAIL.PATH}:id`,
  //   // component: DetailsClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.DETAIL.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   // },
  // },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.EDIT.PATH}:id`,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.EDIT.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
  // {
  //   path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.ADD.PATH,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.ADD.TITRE,
  //   // resolve: {
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
];
