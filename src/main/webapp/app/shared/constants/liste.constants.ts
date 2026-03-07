export const FILE_SIZE_MAX_SIGNATURE = 153600; // 150Kb en octet
export const SNACKBAR_DURATION = 5000; // In milliseconds
export const ID_PARAM_LOCK_DURATION = 7;
export const HEADER_PREFIX = 'X-bescapp-';
export const DEFAULT_LANGUAGE = 'fr';
export const PREFERRED_LANGUAGES = ['fr', 'en'];
export const PREFERRED_COUNTRIES = ['bf', 'ml', 'ne'];

export const SKIP_INTERCEPTOR = { 'Skip-Interceptor': 'true' };
export const IS_QUERY_PARAM = { isQuery: 'true' };

export const MESSAGE_TITLE_SUCCESS = 'main.constants.messageTitleSuccess';
export const MESSAGE_TITLE_ERROR = 'main.constants.messageTitleError';
export const MESSAGE_TITLE_WARN = 'main.constants.messageTitleWarn';
export const MESSAGE_TITLE_INFO = 'main.constants.messageTitleInfo';
export const MESSAGE_SERVER_ERROR_CONTACT_ADMIN = "Une erreur serveur est survenue, merci de contacter l'administrateur";
export const SWAL_CONFIRMATION_TITLE = 'main.components.AlertConfirmation.title';
export const SWAL_MENTION_IRREVERSIBLE = 'main.components.AlertConfirmation.irreversibleMention';
export const SWAL_TEXT_BTN_CONFIRME = 'main.components.AlertConfirmation.confirmButtonLabel';
export const SWAL_TEXT_BTN_CANCEL = 'main.components.AlertConfirmation.cancelButtonLabel';
export const SWAL_TEXT_CONFIRMATION_ENREGISTREMENT = 'Voulez-vous vraiment enregistrer les données saisie ?';

export const IS_TRANSLATION_ENABLED_ON_TOAST = true;

export enum EnumIconAlert {
  SUCCESS = 'success',
  ERROR = 'error',
  WARNING = 'warning',
  INFO = 'info',
  QUESTION = 'question',
}

export enum EnumInputType {
  TEXT = 'text',
  NUMBER = 'number',
  TEXTAREA = 'textarea',
  SELECT = 'select',
  DATE = 'date',
  RADIO = 'radio',
  CHECKBOX = 'checkbox',
}

export enum EnumListActions {
  ADD = 'ADD',
  EDIT = 'EDIT',
  VIEW = 'VIEW',
  PROCESS = 'PROCESS',
  DELETE = 'DELETE',
  CLONE = 'CLONE',
}

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
