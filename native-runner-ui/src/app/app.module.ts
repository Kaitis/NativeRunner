import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AddProcessComponent} from './process/add-process.component';
import {AddProcessHistComponent} from './process-hist/add-process-hist.component';
import {AddProcessListComponent} from './process-list/add-process-list.component';
import {AddProcessHistListComponent} from './process-hist-list/add-process-hist-list.component';
import {AppRoutingModule} from './/app-routing.module';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AddProcessDetailComponent} from './process-detail/add-process-detail.component';
import {NewProcessComponent} from './new-process/new-process.component';
import {FormsModule} from "@angular/forms";
import {EnumToArrayPipe} from "./pipes/enum-to-array.pipe";
import {HttpClientModule} from "@angular/common/http";
import {NewChainComponent} from './new-chain/new-chain.component';
import {ChainComponent} from './process-chain/chain.component';
import {ChainListComponent} from './process-chain-list/chain-list.component';
import { ScheduleListComponent } from './schedule-list/schedule-list.component';
import { ScheduleComponent } from './schedule/schedule.component';
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {DateFormatPipe, DateTimeFormatPipe} from "./pipes/date-time-format";
import {EscapeHtmlPipe} from "./pipes/keep-html.pipe";


@NgModule({
  declarations: [
    AppComponent,
    AddProcessComponent,
    AddProcessHistComponent,
    AddProcessListComponent,
    AddProcessHistListComponent,
    AddProcessDetailComponent,
    NewProcessComponent,
    EnumToArrayPipe,
    NewChainComponent,
    ChainComponent,
    ChainListComponent,
    ScheduleListComponent,
    ScheduleComponent,
    DateTimeFormatPipe, EscapeHtmlPipe, DateFormatPipe

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    InfiniteScrollModule
  ],
  providers: [EnumToArrayPipe],
  bootstrap: [AppComponent]
})
export class AppModule {

}
platformBrowserDynamic().bootstrapModule(AppModule);
