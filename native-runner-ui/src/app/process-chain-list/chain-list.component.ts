import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {ProcessGroupTO} from "../domain/Domain";

@Component({
  selector: 'app-chain-list',
  templateUrl: './chain-list.component.html',
  styleUrls: ['./chain-list.component.scss']
})
export class ChainListComponent implements OnInit {

  chains: ProcessGroupTO[];

  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.dataService.getChains()
      .subscribe(data => {
        this.chains = data;
        console.log("Chains", this.chains);
      });
  }

  removedChain($event) {
      console.log("Received delete event with chain:", $event.chain);
      let index = this.chains.indexOf($event.chain);
      this.chains.splice(index);
  }
}

