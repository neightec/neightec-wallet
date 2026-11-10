import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsCardComponent } from './accounts-card.component';

describe('AccountsCardComponent', () => {
  let component: AccountsCardComponent;
  let fixture: ComponentFixture<AccountsCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountsCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
