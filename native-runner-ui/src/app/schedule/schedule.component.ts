import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ScheduleTO} from "../domain/Domain";

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.scss']
})
export class ScheduleComponent implements OnInit {

  @Input("schedule") schedule: ScheduleTO;
  @Output("removePressed") removePressed = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  remove(process:string) {
    this.removePressed.emit(process);
  }

}
