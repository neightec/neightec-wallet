import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeightWeddingPagenotfoundComponent } from './neight-wedding-pagenotfound.component';

describe('NeightWeddingPagenotfoundComponent', () => {
  let component: NeightWeddingPagenotfoundComponent;
  let fixture: ComponentFixture<NeightWeddingPagenotfoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NeightWeddingPagenotfoundComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NeightWeddingPagenotfoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
