import {Component, OnInit} from "@angular/core";
import {DataService} from "../service/data.service";

@Component({
  selector: 'app-schedule-list',
  templateUrl: './schedule-list.component.html',
  styleUrls: ['./schedule-list.component.scss']
})
export class ScheduleListComponent implements OnInit {
  scheduledTasks;

  constructor(private dataService: DataService) { }

  ngOnInit() {
    this.getSchedule();
  }

  getSchedule() {
    this.dataService.getSchedule()
      .subscribe(data => {
        console.log("Schedule: ", data);
        this.scheduledTasks = data;
      });
  }

  removeFromSchedule(process) {
    this.dataService.unschedule(process).subscribe();
    this.getSchedule();
  }
}
