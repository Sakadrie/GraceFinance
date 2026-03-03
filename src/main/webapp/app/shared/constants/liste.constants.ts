export const LISTE_ADMINISTRATION_ROUTES = {
  PATH: 'admin',

  DASHBOARD: {
    PATH: 'dashboard',
    TITRE: 'main.admin.dashboard.title',
  },

  PRINCIPAL: {
    PATH: 'principal',

    COMPTE_COMPTABLE: {
      PATH: 'compte-comptable',
      TITRE: 'main.admin.compte-comptable.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.compte-comptable.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.compte-comptable.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.compte-comptable.edit.title' },
    },
    CAISSE: {
      PATH: 'caisse',
      TITRE: 'main.admin.caisse.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.caisse.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.caisse.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.caisse.edit.title' },
    },
    DEPENSE: {
      PATH: 'depense',
      TITRE: 'main.admin.depense.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.depense.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.depense.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.depense.edit.title' },
    },
    ECRITURE_COMPTABLE: {
      PATH: 'ecriture-comptable',
      TITRE: 'main.admin.ecriture-comptable.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.ecriture-comptable.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.ecriture-comptable.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.ecriture-comptable.edit.title' },
    },
    ENTITE_FINACIERE: {
      PATH: 'entite-financiere',
      TITRE: 'main.admin.entite-financiere.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.entite-financiere.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.entite-financiere.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.entite-financiere.edit.title' },
    },
    LIGNE_ECRITURE: {
      PATH: 'ligne-ecriture',
      TITRE: 'main.admin.ligne-ecriture.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.ligne-ecriture.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.ligne-ecriture.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.ligne-ecriture.edit.title' },
    },
    RECETTE: {
      PATH: 'recette',
      TITRE: 'main.admin.recette.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.recette.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.recette.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.recette.edit.title' },
    },
  },

  REFERENTIEL: {
    PATH: 'referentiel',

    CATEGORIE: {
      PATH: 'categorie',
      TITRE: 'main.admin.categorie.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.categorie.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.categorie.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.categorie.edit.title' },
    },

    TRANSFERT: {
      PATH: 'transfert',
      TITRE: 'main.admin.transfert.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.transfert.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.transfert.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.transfert.edit.title' },
    },
  },

  SECURITY: {
    PATH: 'security',

    PROFIL: {
      PATH: 'profil',
      TITRE: 'main.admin.profil.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.profil.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.profil.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.profil.edit.title' },
    },

    DROITS: {
      PATH: 'droits',
      TITRE: 'main.admin.droits.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.droits.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.droits.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.droits.edit.title' },
    },
    AFFECTATION_UTILISATEUR: {
      PATH: 'affectation-utilisateur',
      TITRE: 'main.admin.affectation-utilisateur.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.affectation-utilisateur.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.affectation-utilisateur.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.affectation-utilisateur.edit.title' },
    },
    USER: {
      PATH: 'compte-utilisateur',
      TITRE: 'main.admin.compte-utilisateur.title',
      DETAIL: { PATH: 'view', TITRE: 'main.admin.compte-utilisateur.detail.title' },
      ADD: { PATH: 'new', TITRE: 'main.admin.compte-utilisateur.add.title' },
      EDIT: { PATH: 'edit', TITRE: 'main.admin.compte-utilisateur.edit.title' },
    },
  },
};
