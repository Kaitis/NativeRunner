import {BaseResourceService} from "./BaseResourceService";
import {ProcessGroupTO, ProcessTO} from "../domain/Domain";
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/index";

@Injectable({
  providedIn: 'root'
})
export class DataService extends BaseResourceService{

  private BASE_URL = this.API_URL;

  private HEADER = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {
    super();

  }

  // getNodes(): Observable<any> {
  //   let url = this.BASE_URL + 'live-instances';
  //   console.log(url);
  //   return this.http.get(url, {headers: this.HEADER});
  // }

  getProcesses(): Observable<any> {
    let url = this.BASE_URL + 'processes';
    console.log(url);
    return this.http.get(url, {headers: this.HEADER});
  }

  getProcess(id): Observable<any> {
    let url = this.BASE_URL + 'process/'+id;
    console.log(url);
    return this.http.get(url, {headers: this.HEADER});
  }

  saveProcess(process: ProcessTO):Observable<any>{
    let url = this.BASE_URL + 'process';
    if(process.id == null || process.id <= 0) {
      return this.http.post(url, process,{headers: this.HEADER});
    }else {
      return this.http.patch(url, process,{headers: this.HEADER});
    }
  }

  getChains():Observable<any>{
    let url = this.BASE_URL + 'chains';
    console.log(url);
    return this.http.get(url, {headers: this.HEADER});
  }

  getChain(id: number): Observable<any> {
    let url = this.BASE_URL + 'chain/'+id;
    console.log(url);
    return this.http.get(url, {headers: this.HEADER});
  }

  getChainDetail(id: number): Observable<any> {
    let url = this.BASE_URL + 'chain/'+id+'/detail';
    console.log(url);
    return this.http.get(url, {headers: this.HEADER});
  }

  saveChain(chain:ProcessGroupTO):Observable<any>{
    let url = this.BASE_URL + 'chain/register';
    console.log(url);
    return this.http.post(url, chain,{headers: this.HEADER});
  }

  getHistory(page, size): Observable<any> {
    let url = this.BASE_URL + 'history';
    // console.log("Page: " + page + " size: " + size);
    return this.http.get(url, {headers: this.HEADER, params:{page: page, size:size}});
  }

  getSchedule(): Observable<any> {
    let url = this.BASE_URL + 'schedule';
    console.log(url);
    return this.http.get(url, {headers: this.HEADER});
  }

  startProcess(id: number):Observable<any>{
    let url = this.BASE_URL + 'process/'+id + '/start';
    console.log(url);
    return this.http.post(url, null,{headers: this.HEADER});
  }

  stopProcess(id: number):Observable<any>{
    let url = this.BASE_URL + 'process/'+id + '/stop';
    console.log(url);
    return this.http.post(url, null,{headers: this.HEADER});
  }

  deleteProcess(id: number):Observable<any>{
    let url = this.BASE_URL + 'process/'+id;
    console.log(url);
    return this.http.delete(url, {headers: this.HEADER});
  }

  startProcessGroup(id: number):Observable<any>{
    let url = this.BASE_URL + 'chain/'+id + '/start';
    console.log(url);
    return this.http.post(url, null,{headers: this.HEADER});
  }

  stopProcessGroup(id: number):Observable<any>{
    let url = this.BASE_URL + 'chain/'+id + '/stop';
    console.log(url);
    return this.http.post(url, null,{headers: this.HEADER});
  }

  deleteProcessGroup(id: number):Observable<any>{
    let url = this.BASE_URL + 'chain/'+id;
    console.log(url);
    return this.http.delete(url, {headers: this.HEADER});
  }

  unschedule(process: string): Observable<any>{
    let url = this.BASE_URL + 'process/unschedule/' + process;
    console.log(url);
    return this.http.post(url, null,{headers: this.HEADER});
  }
}
