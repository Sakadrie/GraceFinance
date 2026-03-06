import { Directive, HostBinding, Input } from '@angular/core';
import { AbstractControl, FormControl, NgModel } from '@angular/forms';

@Directive({
  selector: '[jhiFormFieldState]',
})
export class FormFieldStateDirective {
  @Input('jhiFormFieldState') control!: NgModel | FormControl | AbstractControl | null;

  @HostBinding('class.is-invalid') get isInvalid(): boolean {
    const c = this.ctrl;
    return !!c && c.invalid && (c.dirty || c.touched);
  }

  @HostBinding('class.input-border-invalid') get borderInvalid(): boolean {
    return this.isInvalid;
  }

  @HostBinding('class.is-valid') get isValid(): boolean {
    const c = this.ctrl;
    return !!c && c.valid && (c.dirty || c.touched);
  }

  @HostBinding('class.input-border-valid') get borderValid(): boolean {
    return this.isValid;
  }

  private get ctrl(): AbstractControl | null {
    if (!this.control) return null;
    // ngModel possède une propriété "control"
    return this.control instanceof NgModel ? this.control.control : (this.control as AbstractControl);
  }
}
