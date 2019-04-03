import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProcessTO} from "../domain/Domain";
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-process',
  templateUrl: './add-process.component.html',
  styleUrls: ['./add-process.component.css']
})
export class AddProcessComponent implements OnInit {

  @Input("process") process: ProcessTO;
  @Output("deleteEvent") deleteEvent = new EventEmitter();

  shouldHideBtn = false;

  constructor(private dataService: DataService, private router:Router) { }

  ngOnInit() {
    this.shouldHideBtn = this.process.cron === null
    console.log("hide btns?: " + this.shouldHideBtn);
  }

  startProcess(id){
    this.dataService.startProcess(id).subscribe();
  }

  stopProcess(id){
    this.dataService.stopProcess(id).subscribe();
  }

  delete(id){
    console.log("Deleting process:", this.process);
    if(window.confirm('Are sure you want to delete this item ?')) {

      this.dataService.deleteProcess(id).subscribe();
      this.deleteEvent.emit({process:this.process});
    }
  }
}
