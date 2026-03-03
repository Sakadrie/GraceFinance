import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { DepenseComponent } from './depense.component';

export const DEPENSE_ROUTE: Route[] = [
  {
    path: '',
    component: DepenseComponent,
    title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.TITRE,
    // resolve: {
    //   allGroupeProduit: allGroupeProduitResolve,
    // },
  },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.DETAIL.PATH}:id`,
  //   // component: DetailsClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.DETAIL.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   // },
  // },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.EDIT.PATH}:id`,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.EDIT.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
  // {
  //   path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.ADD.PATH,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.DEPENSE.ADD.TITRE,
  //   // resolve: {
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
];
