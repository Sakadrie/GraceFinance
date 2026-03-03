import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LISTE_ADMINISTRATION_ROUTES } from 'app/shared/constants/liste.constants';

/**
 * Structure d'un élément de menu
 */
export interface MenuItem {
  title: string;
  icon?: string;
  route?: string;
  children?: MenuItem[];
}

@Component({
  selector: 'jhi-sidebar',
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  /**
   * la liste d'items à afficher dans la sidebar. Peut être fournie par le parent.
   * Si non renseignée, on peut utiliser un jeu par défaut ou laisser vide.
   */
  @Input()
  menu: MenuItem[] = [];

  // État de la sidebar (ouverte ou fermée)
  sidebarOpen = true;

  // expose les constantes de routes au template pour construire les routerLink
  readonly LAR = LISTE_ADMINISTRATION_ROUTES;

  /**
   * transforme un titre en identifiant CSS pour collapse (supprime espaces, minuscules)
   */
  idFor(item: MenuItem): string {
    return item.title.toLowerCase().replace(/\s+/g, '');
  }

  /**
   * Bascule l'état de la sidebar (ouvrir/fermer)
   */
  toggleSidebar(): void {
    this.sidebarOpen = !this.sidebarOpen;
  }
}
