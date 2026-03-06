/* eslint-disable @typescript-eslint/no-deprecated */
/* eslint-disable @typescript-eslint/no-unnecessary-condition */
import { Directive, HostListener, Input, ElementRef, Optional, Host, OnInit, OnChanges } from '@angular/core';
import { GlobalService } from 'app/core/util/global.service';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';

@Directive({
  selector: '[jhiClickToCopy]',
})
export class ClickToCopyDirective implements OnInit, OnChanges {
  @Input() isEnabled?: boolean;
  @Input() tooltipKey?: string = 'main.clickToCopy.clickToCopy';

  constructor(
    private el: ElementRef,
    private globalService: GlobalService,
    private translate: TranslateService,
    @Host() @Optional() private ngbTooltip?: NgbTooltip,
  ) {}

  ngOnInit(): void {
    this.updateCursor();
  }

  ngOnChanges(): void {
    this.updateCursor();
  }

  @HostListener('click')
  onClick(): void {
    if (this.isEnabled) {
      const text = this.el.nativeElement.innerText ?? this.el.nativeElement.value ?? '';
      if (text) {
        if (navigator.clipboard) {
          navigator.clipboard.writeText(text);
        } else {
          // Fallback pour les anciens navigateurs
          const textarea = document.createElement('textarea');
          textarea.value = text;
          document.body.appendChild(textarea);
          textarea.select();
          document.execCommand('copy');
          document.body.removeChild(textarea);
        }
        this.globalService.showToastInfo({ label: 'main.clickToCopy.copySuccess' });
      }
    }
  }

  @HostListener('mouseover')
  onMouseOver(): void {
    if (this.isEnabled && this.ngbTooltip && this.tooltipKey && this.translate) {
      const translated = this.translate.instant(this.tooltipKey);
      this.ngbTooltip.ngbTooltip = translated;
      this.ngbTooltip.open();
    }
  }

  @HostListener('mouseout')
  onMouseOut(): void {
    if (this.ngbTooltip) {
      this.ngbTooltip.close();
    }
  }

  private updateCursor(): void {
    this.el.nativeElement.style.cursor = this.isEnabled ? 'pointer' : '';
  }
}
