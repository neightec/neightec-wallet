import { AfterViewInit, Component, EventEmitter, Inject, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormControlName, FormGroup, UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { BaseModal, ModalService } from 'carbon-components-angular';
import { GuestWeddingListService } from 'src/app/services/guest-wedding-list.service';
import * as dashboardActions from '../../store/dashboard.actions';
import { GuestFullnameValidator } from 'src/app/validators/guest-fullname.validator';

@Component({
  selector: 'neight-tech-register-guest-dialog',
  templateUrl: './register-guest-dialog.component.html',
  styleUrls: ['./register-guest-dialog.component.scss'],
  standalone: false
})
export class RegisterGuestDialogComponent extends BaseModal implements OnInit {

  registerGuestEmpty: boolean = true;
  registerUserFormGroup: FormGroup;
  // registerGuestChanged = new EventEmitter<boolean>();
  data: boolean = false;
  enteredGuests: string[] = [];

  constructor(
    @Inject('newInput') public newInput: boolean,
    protected modalService: ModalService,
    private store: Store,
    private guestFullNameValidator: GuestFullnameValidator,
    private service: GuestWeddingListService //TODO in actions + effects
  ) {
    super();
  }

  ngOnInit(): void {
    if (!this.data && this.newInput) {
      this.registerUserFormGroup = new FormGroup(
        {
          registerUserFormControl: new FormControl(
            {
              value: null,
              disabled: false,
            },
            {
              asyncValidators: this.guestFullNameValidator.validate.bind(
                this.guestFullNameValidator
              ),
            }
        ),
        },
      );
      this.data = this.newInput; // prevent first from "Expression has changed after it was checked" Error
    }
  }

  // this.store.select -- detect any guest objects entered in modal

  registerGuests() {
    if (this.enteredGuests) {
      // TODO update table data immediately and add new guest
      this.store.dispatch(
        dashboardActions.updateDataDashboard({
          guests: this.enteredGuests
        })
      );
    }
    this.closeModal();
  }

  enterGuest() {
    if (this.registerUserFormGroup.get('registerUserFormControl').value && !this.registerUserFormGroup.controls['registerUserFormControl'].errors) {
      const guest: string = this.registerUserFormGroup.get('registerUserFormControl').value;
      this.enteredGuests.push(guest);
      this.registerUserFormGroup.get('registerUserFormControl').setValue(null);
    }
  }

  showEnteredGuest(text: string) {
    return text
  }

  deleteEnteredGuest(index: any) {
    if (this.enteredGuests) {
      const idx = this.enteredGuests.indexOf(index);
      if (idx != -1) {
        this.enteredGuests.splice(idx, 1);
      }
    }
  }
}
