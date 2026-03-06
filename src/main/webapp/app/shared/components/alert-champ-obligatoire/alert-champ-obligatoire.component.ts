import { Component } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { TranslateDirective } from 'app/shared/language';

@Component({
  selector: 'jhi-alert-champ-obligatoire',
  standalone: true,
  imports: [MatIcon, TranslateDirective],
  templateUrl: './alert-champ-obligatoire.component.html',
  styleUrl: './alert-champ-obligatoire.component.scss',
})
export class AlertChampObligatoireComponent {}
