import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProcessDetailComponent } from './add-process-detail.component';

describe('AddProcessDetailComponent', () => {
  let component: AddProcessDetailComponent;
  let fixture: ComponentFixture<AddProcessDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProcessDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProcessDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
