import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { EcritureComptableComponent } from './ecriture-comptable.component';

export const ECRITURE_COMPTABLE_ROUTE: Route[] = [
  {
    path: '',
    component: EcritureComptableComponent,
    title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.TITRE,
    // resolve: {
    //   allGroupeProduit: allGroupeProduitResolve,
    // },
  },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.DETAIL.PATH}:id`,
  //   // component: DetailsClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.DETAIL.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   // },
  // },
  // {
  //   path: `${LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.EDIT.PATH}:id`,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.EDIT.TITRE,
  //   // resolve: {
  //   //   classeProduit: classeProduitResolve,
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
  // {
  //   path: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.ADD.PATH,
  //   // component: CreateUpdateClasseProduitComponent,
  //   title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.ECRITURE_COMPTABLE.ADD.TITRE,
  //   // resolve: {
  //   //   allGroupeProduit: allGroupeProduitResolve,
  //   // },
  // },
];
