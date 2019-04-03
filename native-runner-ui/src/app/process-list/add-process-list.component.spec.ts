import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProcessListComponent } from './add-process-list.component';

describe('AddProcessListComponent', () => {
  let component: AddProcessListComponent;
  let fixture: ComponentFixture<AddProcessListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProcessListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProcessListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
