import {Component, OnInit} from '@angular/core';
import {EProcessPriority, ProcessTO} from "../domain/Domain";
import {isUndefined} from "util";
import {DataService} from "../service/data.service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-new-process',
  templateUrl: './new-process.component.html',
  styleUrls: ['./new-process.component.scss']
}, )
export class NewProcessComponent implements OnInit {

  process: ProcessTO;
  serviceUrl;
  hasFixedUrl: boolean = false;


  constructor(private dataService:DataService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getProcessDetail();
  }

  getProcessDetail(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.serviceUrl = +this.route.snapshot.paramMap.get('serviceUrl');

    console.log("serviceUrl:" + this.serviceUrl);
    if(id === undefined || id === 0)
      this.process = new ProcessTO(null, null, null, null, null, null, new Date(), EProcessPriority.NORMAL, null, null);
    else
      this.dataService.getProcess(id)
        .subscribe(p => {
          this.process = p;
          if (!isUndefined(p.fixedURL))
            this.hasFixedUrl = true;
        });


  }

  onFormSubmit($event: Event) {
    // if (this.process.id <= 0)
    //   this.process.id = null;
    console.log("Saving process : ",this.process);
    this.dataService.saveProcess(this.process)
      .subscribe(data => this.process = data);
    this.router.navigate(['./processes'])
  }

  toggleFixedURL() {
    this.hasFixedUrl = !this.hasFixedUrl;
  }

}
