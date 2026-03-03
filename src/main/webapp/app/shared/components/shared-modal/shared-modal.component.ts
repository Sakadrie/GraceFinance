import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, ViewChild, ViewContainerRef } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { TranslateDirective } from 'app/shared/language';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'jhi-shared-modal',
  standalone: true,
  imports: [CommonModule, TranslateDirective, TranslatePipe],
  templateUrl: './shared-modal.component.html',
})
export class SharedModalComponent {
  @Input() title = '';
  @Input() titleTransValues: any = {};
  @Input() size: 'sm' | 'lg' | 'xl' | '' = '';
  @Input() actions: { label: string; color?: string; value: string; icon?: string }[] = [];
  @Input() data$!: BehaviorSubject<any>;
  @Output() closed = new EventEmitter<{ confirmed: boolean; action: string }>();

  @ViewChild('contentContainer', { read: ViewContainerRef, static: true })
  contentContainer!: ViewContainerRef;

  contentInstance: any;

  onAction(action: string) {
    if (this.contentInstance?.onAction) {
      this.contentInstance.onAction(action);
    }

    if (action === 'close' || action === 'cancel') {
      this.closed.emit({ confirmed: false, action: action });
    }
    // else if (action === 'save' || action === 'validate') {
    //   this.closed.emit({confirmed: false, action: action});
    // }
  }
}
