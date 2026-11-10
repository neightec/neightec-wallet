import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsCardComponent } from './accounts-card.component';

describe('AccountsCardComponent', () => {
  let fixture: ComponentFixture<AccountsCardComponent>;

  const render = (balance: string, currency: string | null) => {
    fixture.componentRef.setInput('account', { name: 'Girokonto', balance, currency });
    fixture.detectChanges();
    return fixture.nativeElement as HTMLElement;
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountsCardComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(AccountsCardComponent);
  });

  it('should show name and formatted balance', () => {
    const el = render('1830.1', 'EUR');
    expect(el.querySelector('.account-name')?.textContent).toContain('Girokonto');
    expect(el.querySelector('.account-balance')?.textContent).toContain('1,830.10 EUR');
  });

  it('should default currency to EUR and mark negative balance', () => {
    const el = render('-320.40', null);
    const balance = el.querySelector('.account-balance')!;
    expect(balance.textContent).toContain('EUR');
    expect(balance.classList).toContain('negative');
  });
});
