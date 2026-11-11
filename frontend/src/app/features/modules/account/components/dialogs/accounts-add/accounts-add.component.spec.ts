import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsAddComponent } from './accounts-add.component';

describe('AccountsAddComponent', () => {
  let component: AccountsAddComponent;
  let fixture: ComponentFixture<AccountsAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountsAddComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountsAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
