import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChainListComponent } from './chain-list.component';

describe('ChainListComponent', () => {
  let component: ChainListComponent;
  let fixture: ComponentFixture<ChainListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChainListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChainListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
