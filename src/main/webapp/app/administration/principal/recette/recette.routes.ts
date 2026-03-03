import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { RecetteComponent } from './recette.component';

export const RCETTE_ROUTE: Route[] = [
  {
    path: '',
    component: RecetteComponent,
    title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.TITRE,
    // resolve: {
    //   allGroupeProduit: allGroupeProduitResolve,
    // },
  },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.DETAIL.PATH}:id`,
  //   // component: DetailsClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.DETAIL.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   // },
  // },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.EDIT.PATH}:id`,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.EDIT.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
  // {
  //   path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.ADD.PATH,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.RECETTE.ADD.TITRE,
  //   // resolve: {
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
];
