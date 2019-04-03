import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
// import {Observable} from "rxjs/Observable";
import {environment} from "../../environments/environment.prod";

export class BaseResourceService {

  API_URL:string =  "http://processlauncher.eg-factory.de/admin/rest/";

  // protected extractData(res: HttpResponse<any>) {
  //   if (res.hasOwnProperty('responseType')) {
  //     if (res["responseType"] == 'EXCEPTION') {
  //       let response = res['response'];
  //       let message = response['message'];
  //       throw new Error(message);
  //     }
  //   }
  //   return res["response"] || {};
  // }
  //
  // protected handleError (error: HttpErrorResponse) {
  //   // console.error(error.message || error);
  //   // return Observable.throw(error.message || error);
  // }
}
