import {Component, Input, Output, OnInit, EventEmitter} from '@angular/core';
import {ProcessGroupTO} from "../domain/Domain";
import {DataService} from "../service/data.service";

@Component({
  selector: 'app-chain',
  templateUrl: './chain.component.html',
  styleUrls: ['./chain.component.scss']
})
export class ChainComponent implements OnInit {

  @Input("chain")chain:ProcessGroupTO;
  @Output("deleteEvent") deleteEvent = new EventEmitter();

  constructor(private dataService:DataService) { }

  ngOnInit() {
  }

  startProcessGroup(id){
    this.dataService.startProcessGroup(id).subscribe();
  }

  stopProcessGroup(id){
    this.dataService.stopProcessGroup(id).subscribe();
  }

  deleteGroup(id){
    console.log("Deleting chain:", this.chain);
    if(window.confirm('Are sure you want to delete this item ?')) {

      this.dataService.deleteProcessGroup(id).subscribe();
      this.deleteEvent.emit({chain:this.chain});
    }
  }
}
