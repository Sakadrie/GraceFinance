import { Component, Input } from '@angular/core';

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
  imports: [],
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

  /**
   * transforme un titre en identifiant CSS pour collapse (supprime espaces, minuscules)
   */
  idFor(item: MenuItem): string {
    return item.title.toLowerCase().replace(/\s+/g, '');
  }
}
