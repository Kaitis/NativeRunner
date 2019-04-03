import {Component, OnInit} from '@angular/core';
import {ProcessDetailTO} from "../domain/Domain";
import {ActivatedRoute} from "@angular/router";
import {DataService} from "../service/data.service";

@Component({
  selector: 'app-add-process-detail',
  templateUrl: './add-process-detail.component.html',
  styleUrls: ['./add-process-detail.component.scss']
})
export class AddProcessDetailComponent implements OnInit {

  process;
  serviceUrl;
  isChain;

  constructor(
    private route: ActivatedRoute,
    private dataService: DataService,
    // private location: Location
  ) {}

  ngOnInit(): void {
    this.getProcessDetail();
  }

  getProcessDetail(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.isChain = +this.route.snapshot.paramMap.get('isChain');

    if(this.isChain === 0)
      this.dataService.getProcess(id)
        .subscribe(p => {
          console.log("Fetched Process Details:",p);
          this.process = p;
        });
    else
      this.dataService.getChainDetail(id)
        .subscribe(p => {
          console.log("Fetched Process Details:",p);
          this.process = p;
        });
  }


  startProcess(id){
    if(this.isChain === 0)
      this.dataService.startProcess(id).subscribe();
    else
      this.dataService.startProcessGroup(id).subscribe();
  }

  stopProcess(id){
    if(this.isChain === 0)
      this.dataService.stopProcess(id).subscribe();
    else
      this.dataService.stopProcessGroup(id).subscribe();
  }

  deleteProcess(id){
    if(window.confirm('Are sure you want to delete this item ?')) {
      if(this.isChain  === 0)
        this.dataService.deleteProcess(id).subscribe();
      else
        this.dataService.deleteProcessGroup(id).subscribe();
    }
  }
}
