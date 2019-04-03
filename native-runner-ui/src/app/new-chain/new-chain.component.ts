import {Component, OnInit} from '@angular/core';
import {ChainProcessTO, ProcessGroupTO, ProcessTO} from "../domain/Domain";
import {DataService} from "../service/data.service";
import {ActivatedRoute, Router} from "@angular/router";
import {isUndefined} from "util";

@Component({
  selector: 'app-new-chain',
  templateUrl: './new-chain.component.html',
  styleUrls: ['./new-chain.component.scss']
})
export class NewChainComponent implements OnInit {

  processes: ProcessTO[] = [];
  chain: ProcessGroupTO;
  steps: String[] = [];
  hasFixedUrl: boolean = false;

  constructor(private dataService: DataService,private router: Router, private route:ActivatedRoute) { }

  ngOnInit() {
    this.getChainDetail();
    this.getProcesses();
  }


  getProcesses(){
    this.dataService.getProcesses()
      .subscribe(data => this.processes = data);

  }

  getChainDetail(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    // console.log("id:",id);
    if(id < 1){
      this.chain = new ProcessGroupTO(null, null, [ new ChainProcessTO(new ProcessTO(null,null,null,null,null,null,null,null,null, null), 0) ], null, null, null, null, null, null);
      this.steps.push("");
    }
    else
      this.dataService.getChain(id)
        .subscribe(c => {
          console.log("chain:",c);
          this.chain = c;
          if(!isUndefined(this.chain.fixedURL))
            this.hasFixedUrl = true;
          for (let process of this.chain.processes){
            this.steps.push("");
          }
        });

    // console.log("steps:", this.steps.length);

  }

  newStep(){
    this.steps.push("");
    this.chain.processes.push(new ChainProcessTO(new ProcessTO(null,null,null,null,null,null,null,null,null, null), this.steps.length));
    // console.log("steps:", this.steps.length);
    // console.log("processes:", this.processes);
  }

  deleteStep(step){
    this.steps.splice(step, 1);
    this.chain.processes.splice(step, 1);
  }

  onOptionChange(step,event){
    this.chain.processes[step].processTO =this.processes[event.target.value];
    console.log("Step:" + step + " process:" +this.processes[event.target.value].name);
    // this.onSaveClicked();
  }

  save(){
    this.dataService.saveChain(this.chain)
      .subscribe(d => this.chain = d);
    this.router.navigate(['./chains'])
  }


  toggleFixedURL() {
    this.hasFixedUrl = !this.hasFixedUrl;
  }
}
