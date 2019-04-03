import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProcessHistComponent } from './add-process-hist.component';

describe('AddProcessHistComponent', () => {
  let component: AddProcessHistComponent;
  let fixture: ComponentFixture<AddProcessHistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProcessHistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProcessHistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
