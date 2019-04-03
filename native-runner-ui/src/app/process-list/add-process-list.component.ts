import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {ProcessTO} from "../domain/Domain";

@Component({
  selector: 'app-add-process-list',
  templateUrl: './add-process-list.component.html',
  styleUrls: ['./add-process-list.component.css']
})
export class AddProcessListComponent implements OnInit {

  processes: ProcessTO[];

  constructor(private dataService: DataService) { }

  ngOnInit() {
    this.getData();
  }

  getData(){
    this.dataService.getProcesses()
      .subscribe(data => {
        this.processes = data;
        console.log("Processes", this.processes);
      });
  }

  removedProcess($event) {
    console.log("Received delete event with process:", $event.process);
    let index = this.processes.indexOf($event.process);
    this.processes.splice(index);
  }
}
