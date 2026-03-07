import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { CategorieComponent } from './categorie.component';

export const CATEGORIE_ROUTE: Route[] = [
  {
    path: '',
    component: CategorieComponent,
    title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.TITRE,
    // resolve: {
    //   allEntiteFinanciere: allGroupeProduitResolve,
    // },
  },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.DETAIL.PATH}:id`,
  //   // component: DetailsClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.DETAIL.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   // },
  // },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.EDIT.PATH}:id`,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.EDIT.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
  // {
  //   path: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.ADD.PATH,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.CATEGORIE.ADD.TITRE,
  //   // resolve: {
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
];
