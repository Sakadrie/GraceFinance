import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { CompteUtilisateurComponent } from './compte-utilisateur.component';

export const COMPTE_UTILISATEUR_ROUTE: Route[] = [
  {
    path: '',
    component: CompteUtilisateurComponent,
    title: LISTE_ADMINISTRATION_ROUTES.SECURITY.USER.TITRE,
    // resolve: {
    //   allGroupeProduit: allGroupeProduitResolve,
    // },
  },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.DETAIL.PATH}:id`,
  //   // component: DetailsClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.DETAIL.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   // },
  // },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.EDIT.PATH}:id`,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.EDIT.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
  // {
  //   path: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.ADD.PATH,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.TRANSFERT.ADD.TITRE,
  //   // resolve: {
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
];
