import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'prettyJson',
})
export default class PrettyJsonPipe implements PipeTransform {
  /**
   * Transforme une chaîne JSON (ou undefined) en une chaîne joliment indentée.
   * Si la valeur n'est pas un JSON valide, retourne la valeur d'origine.
   *
   * @param value - chaîne JSON ou undefined
   * @param space - nombre d'espaces pour l'indentation (par défaut 2)
   */
  transform(value: string | undefined | null, space = 2): string | undefined | null {
    if (value === null || value === undefined) {
      return value;
    }

    if (typeof value !== 'string') {
      try {
        return JSON.stringify(value, null, space);
      } catch (e) {
        return String(value);
      }
    }

    // Trim pour éviter les caractères non visibles qui empêcheraient le parsing
    const trimmed = value.trim();
    if (trimmed.length === 0) {
      return value;
    }

    try {
      const parsed = JSON.parse(trimmed);
      return JSON.stringify(parsed, null, space);
    } catch (e) {
      // Si parse échoue, on essaye de remplacer les single quotes par double quotes basiquement
      // pour gérer des objets JS-like: { 'a': 1 } -> { "a": 1 }
      try {
        const replaced = trimmed.replace(/(['"])\s*:\s*([^,}\]]+)/g, '"$1": $2');
        const parsed2 = JSON.parse(replaced);
        return JSON.stringify(parsed2, null, space);
      } catch (e2) {
        // Rien à faire, retourner la valeur d'origine
        return value;
      }
    }
  }
}
