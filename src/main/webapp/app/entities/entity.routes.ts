import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'graceFinanceApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'entite-financiere',
    data: { pageTitle: 'graceFinanceApp.entiteFinanciere.home.title' },
    loadChildren: () => import('./entite-financiere/entite-financiere.routes'),
  },
  {
    path: 'caisse',
    data: { pageTitle: 'graceFinanceApp.caisse.home.title' },
    loadChildren: () => import('./caisse/caisse.routes'),
  },
  {
    path: 'categorie',
    data: { pageTitle: 'graceFinanceApp.categorie.home.title' },
    loadChildren: () => import('./categorie/categorie.routes'),
  },
  {
    path: 'recette',
    data: { pageTitle: 'graceFinanceApp.recette.home.title' },
    loadChildren: () => import('./recette/recette.routes'),
  },
  {
    path: 'depense',
    data: { pageTitle: 'graceFinanceApp.depense.home.title' },
    loadChildren: () => import('./depense/depense.routes'),
  },
  {
    path: 'transfert',
    data: { pageTitle: 'graceFinanceApp.transfert.home.title' },
    loadChildren: () => import('./transfert/transfert.routes'),
  },
  {
    path: 'droit',
    data: { pageTitle: 'graceFinanceApp.droit.home.title' },
    loadChildren: () => import('./droit/droit.routes'),
  },
  {
    path: 'profil',
    data: { pageTitle: 'graceFinanceApp.profil.home.title' },
    loadChildren: () => import('./profil/profil.routes'),
  },
  {
    path: 'affectation-utilisateur',
    data: { pageTitle: 'graceFinanceApp.affectationUtilisateur.home.title' },
    loadChildren: () => import('./affectation-utilisateur/affectation-utilisateur.routes'),
  },
  {
    path: 'compte-comptable',
    data: { pageTitle: 'graceFinanceApp.compteComptable.home.title' },
    loadChildren: () => import('./compte-comptable/compte-comptable.routes'),
  },
  {
    path: 'ecriture-comptable',
    data: { pageTitle: 'graceFinanceApp.ecritureComptable.home.title' },
    loadChildren: () => import('./ecriture-comptable/ecriture-comptable.routes'),
  },
  {
    path: 'ligne-ecriture',
    data: { pageTitle: 'graceFinanceApp.ligneEcriture.home.title' },
    loadChildren: () => import('./ligne-ecriture/ligne-ecriture.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
