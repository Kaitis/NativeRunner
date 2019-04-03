import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AddProcessListComponent} from "./process-list/add-process-list.component";
import {AddProcessHistListComponent} from "./process-hist-list/add-process-hist-list.component";
import {AddProcessComponent} from "./process/add-process.component";
import {AddProcessDetailComponent} from "./process-detail/add-process-detail.component";
import {NewProcessComponent} from "./new-process/new-process.component";
import {NewChainComponent} from "./new-chain/new-chain.component";
import {ChainListComponent} from "./process-chain-list/chain-list.component";
import {ScheduleListComponent} from "./schedule-list/schedule-list.component";


const routes: Routes = [
  { path: 'processes', component: AddProcessListComponent },
  { path: 'history', component: AddProcessHistListComponent },
  { path: 'schedule', component: ScheduleListComponent},
  { path: 'detail/:id/:isChain', component: AddProcessDetailComponent },
  { path: 'edit/:id', component: NewProcessComponent },
  { path: 'new', component: NewProcessComponent },
  { path: 'chains', component: ChainListComponent },
  { path: 'chain/:id', component: NewChainComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
