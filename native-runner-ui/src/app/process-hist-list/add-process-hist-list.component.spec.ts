import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProcessHistListComponent } from './add-process-hist-list.component';

describe('AddProcessHistListComponent', () => {
  let component: AddProcessHistListComponent;
  let fixture: ComponentFixture<AddProcessHistListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProcessHistListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProcessHistListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
