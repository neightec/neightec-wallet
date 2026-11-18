import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { addAccount } from '../../../store/account.actions';
import { AccountsAddComponent } from './accounts-add.component';

describe('AccountsAddComponent', () => {
  let component: AccountsAddComponent;
  let fixture: ComponentFixture<AccountsAddComponent>;
  let dispatch: jasmine.Spy;
  let dialogRef: jasmine.SpyObj<MatDialogRef<AccountsAddComponent>>;

  beforeEach(async () => {
    dialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      imports: [AccountsAddComponent],
      providers: [
        provideMockStore(),
        { provide: MatDialogRef, useValue: dialogRef },
      ],
    }).compileComponents();

    dispatch = spyOn(TestBed.inject(MockStore), 'dispatch');
    fixture = TestBed.createComponent(AccountsAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create with an invalid empty form', () => {
    expect(component).toBeTruthy();
    expect(component.formGroupAccountAdd.invalid).toBeTrue();
  });

  it('should not dispatch and mark fields touched when the form is invalid', () => {
    component.onSubmit();

    expect(dispatch).not.toHaveBeenCalled();
    expect(dialogRef.close).not.toHaveBeenCalled();
    expect(component.formGroupAccountAdd.controls.accountName.touched).toBeTrue();
  });

  it('should reject a whitespace-only name', () => {
    component.formGroupAccountAdd.setValue({ accountName: '   ', accountBalance: 10 });
    expect(component.formGroupAccountAdd.controls.accountName.invalid).toBeTrue();
  });

  it('should reject a name longer than 50 characters', () => {
    component.formGroupAccountAdd.setValue({ accountName: 'a'.repeat(51), accountBalance: 10 });
    expect(component.formGroupAccountAdd.controls.accountName.invalid).toBeTrue();
  });

  it('should dispatch addAccount with trimmed name and close the dialog', () => {
    component.formGroupAccountAdd.setValue({ accountName: '  Giro  ', accountBalance: 12.5 });
    component.onSubmit();

    expect(dispatch).toHaveBeenCalledWith(
      addAccount({ data: { name: 'Giro', balance: '12.5', currency: '€' } })
    );
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should close the dialog without dispatching on closeDialog', () => {
    component.closeDialog();

    expect(dialogRef.close).toHaveBeenCalled();
    expect(dispatch).not.toHaveBeenCalled();
  });
});
