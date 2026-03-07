import { Injectable } from '@angular/core';

export interface ParseLenientOptions {
  /** Si true, renvoie la chaîne réparée au lieu de l’objet parsé. */
  returnString?: boolean;
  /** Si true, journalise les passes et renvoie des infos de debug en cas d’erreur. */
  debug?: boolean;
}

@Injectable({ providedIn: 'root' })
export class JsonRepairService {
  // eslint-disable-next-line @typescript-eslint/no-redundant-type-constituents
  parseJsonArrayFix(jsonArrayStr: string): unknown | string {
    // supprime les apostrophes d’enveloppe et ré‑échappe les guillemets internes
    const repaired = jsonArrayStr.replace(/"'([^']*)'"/g, (_, value) => {
      // JSON.stringify échappe correctement tous les caractères spéciaux ;
      // on retire simplement les guillemets ajoutés au début/fin.
      const safe = JSON.stringify(value).slice(1, -1);
      return `"${safe}"`;
    });

    // deuxième tentative de parse
    return JSON.parse(repaired);
  }
}
