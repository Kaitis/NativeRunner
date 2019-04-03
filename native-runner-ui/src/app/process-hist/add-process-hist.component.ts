import {Component, Input, OnInit} from '@angular/core';
import {ProcessHistoryTO} from "../domain/Domain";

@Component({
  selector: 'app-add-process-hist',
  templateUrl: './add-process-hist.component.html',
  styleUrls: ['./add-process-hist.component.css']
})
export class AddProcessHistComponent implements OnInit {

  @Input("processHist") processHistory;
  isGroup: boolean;

  constructor() { }

  ngOnInit() {
    if(this.processHistory.hasOwnProperty("process"))
      this.isGroup = false;
    else
      this.isGroup = true;

    // console.log("Process:%s isGroup:%s", this.processHistory, this.isGroup);
  }


}
