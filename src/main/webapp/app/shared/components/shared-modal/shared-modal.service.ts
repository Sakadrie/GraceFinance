import { ApplicationRef, ComponentRef, createComponent, EnvironmentInjector, Injectable, Type } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { SharedModalComponent } from './shared-modal.component';

@Injectable({ providedIn: 'root' })
export class SharedModalService {
  private modalRef?: ComponentRef<SharedModalComponent>;

  constructor(
    private appRef: ApplicationRef,
    private envInjector: EnvironmentInjector,
  ) {}

  open<T>(
    component: Type<T>,
    config: {
      title?: string;
      titleTransValues?: any;
      data?: any;
      size?: 'sm' | 'lg' | 'xl' | '';
      actions?: { label: string; color?: string; value: string; icon?: string; isDisable?: boolean }[];
    } = {},
  ): {
    afterClosed: () => import('rxjs').Observable<{ confirmed: boolean; data?: any }>;
    data: BehaviorSubject<any>;
    contentInstance: any;
  } {
    const container = document.createElement('div');
    document.body.appendChild(container);

    const afterClosed$ = new Subject<{ confirmed: boolean; data?: any }>();
    const data$ = new BehaviorSubject(config.data ?? null);

    this.modalRef = createComponent(SharedModalComponent, {
      environmentInjector: this.envInjector,
    });

    const modalInstance = this.modalRef.instance;
    modalInstance.title = config.title ?? '';
    modalInstance.titleTransValues = config.titleTransValues ?? '';
    modalInstance.size = config.size ?? '';
    modalInstance.actions = config.actions ?? [
      { label: 'main.buttons.close', value: 'close', color: 'btn-secondary', icon: 'fa fa-ban' },
      { label: 'main.buttons.save', value: 'save', color: 'btn-primary', icon: 'fa fa-save' },
    ];

    // on passe le BehaviorSubject dans le modal
    modalInstance.data$ = data$;

    // Crée le contenu dynamique
    const contentRef = modalInstance.contentContainer.createComponent(component, {
      environmentInjector: this.envInjector,
    });

    // on injecte aussi le BehaviorSubject au contenu
    (contentRef.instance as any).data$ = data$;

    (contentRef.instance as any).closeModal = (result: { confirmed: boolean; data?: any }) => {
      afterClosed$.next(result);
      afterClosed$.complete();
      this._destroyModal();
    };

    modalInstance.contentInstance = contentRef.instance;

    // Attache à l'application
    this.appRef.attachView(this.modalRef.hostView);
    container.appendChild(this.modalRef.location.nativeElement);

    // fermeture via footer actions
    modalInstance.closed.subscribe(result => {
      afterClosed$.next({ confirmed: result.confirmed, data: data$.value });
      afterClosed$.complete();
      this._destroyModal();
    });

    return {
      afterClosed: () => afterClosed$.asObservable(),
      data: data$,
      contentInstance: contentRef.instance,
    };
  }

  private _destroyModal(): void {
    if (!this.modalRef) {
      return;
    }
    this.appRef.detachView(this.modalRef.hostView);
    this.modalRef.destroy();
    this.modalRef = undefined;
  }
}
