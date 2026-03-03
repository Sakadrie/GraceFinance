import { Route } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';
import { CompteComptableComponent } from './compte-comptable.component';

export const COMPTE_COMPTABLE_ROUTE: Route[] = [
  {
    path: '',
    component: CompteComptableComponent,
    title: LISTE_ADMINISTRATION_ROUTES.PRINCIPAL.CAISSE.TITRE,
    // resolve: {
    //   allGroupeProduit: allGroupeProduitResolve,
    // },
  },
];
