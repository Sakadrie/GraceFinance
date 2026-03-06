import { Directive, HostBinding, Input } from '@angular/core';
import { NgModel } from '@angular/forms';

@Directive({
  selector: '[jhiFormControlState]',
})
export class FormControlStateDirective {
  @Input('jhiFormControlState') ctrl!: NgModel;

  @HostBinding('class.is-invalid') get isInvalid(): boolean {
    return this.ctrl?.invalid! && (this.ctrl?.dirty! || this.ctrl?.touched!);
  }

  @HostBinding('class.input-border-invalid') get borderInvalid(): boolean {
    return this.isInvalid;
  }

  @HostBinding('class.is-valid') get isValid(): boolean {
    return this.ctrl?.valid! && (this.ctrl?.dirty! || this.ctrl?.touched!);
  }

  @HostBinding('class.input-border-valid') get borderValid(): boolean {
    return this.isValid;
  }
}
