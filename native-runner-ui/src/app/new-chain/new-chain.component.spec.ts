import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChainComponent } from './new-chain.component';

describe('NewChainComponent', () => {
  let component: NewChainComponent;
  let fixture: ComponentFixture<NewChainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewChainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewChainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
