import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {isEmpty} from "rxjs/internal/operators";
import {isUndefined} from "util";
import {AbstractProcessHistoryTO} from "../domain/Domain";

@Component({
  selector: 'app-add-process-hist-list',
  templateUrl: './add-process-hist-list.component.html',
  styleUrls: ['./add-process-hist-list.component.css']
})
export class AddProcessHistListComponent implements OnInit {

  page = 0;
  size = 15;
  history;

  constructor(private dataService: DataService) { }

  ngOnInit() {
    this.getHistory(this.page,this.size);
  }

  onScroll() {
    this.getHistory(++this.page,this.size);
  }

  private getHistory(page: number, size: number) {

    this.dataService.getHistory(page,size)
      .subscribe(data => {
        // console.log("Data: ", data);
        if(this.history === undefined) {
          this.history = data;
          this.history.sort((a, b) => {
            return <any>new Date(b.startTime) - <any>new Date(a.startTime);
          });
        }else
          if(data.length > 0){
            data.forEach(d => this.history.push(d));
            this.history.sort((a, b) => {
              return <any>new Date(b.startTime) - <any>new Date(a.startTime);
            });
          }

        // console.log(this.history)
      });
  }
}
