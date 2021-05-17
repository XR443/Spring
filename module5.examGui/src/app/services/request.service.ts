import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {CommandObject} from '../domain/command';
import {Principal} from '../domain/principal';
import {ResponseObject} from '../domain/response-object';
import {PropertyInsuranceContract} from "../domain/property.insurance.contract";

@Injectable({providedIn: 'root'})
export class RequestService {
  private principalSubject: BehaviorSubject<Principal>;
  private contractSubject: BehaviorSubject<PropertyInsuranceContract>;
  public principal: Observable<Principal>;
  public contract: Observable<PropertyInsuranceContract>;

  constructor(
    private router: Router,
    private http: HttpClient,
  ) {
    this.principalSubject = new BehaviorSubject<Principal>(JSON.parse(localStorage.getItem('principal')));
    this.contractSubject = new BehaviorSubject<PropertyInsuranceContract>(null);
    this.principal = this.principalSubject.asObservable();
    this.contract = this.contractSubject.asObservable();
  }

  public get principalValue(): Principal {
    return this.principalSubject.value;
  }

  public get contractValue(): PropertyInsuranceContract {
    return this.contractSubject.value;
  }

  public contractSet(contract: PropertyInsuranceContract): void {
    this.contractSubject.next(contract);
  }

  login(username: string, password: string): Observable<any> {
    const httpHeaders = new HttpHeaders({
      authorization: 'Basic ' + btoa(username + ':' + password)
    });
    const commandObject = new CommandObject();
    commandObject.command = 'loginAction';
    commandObject.payload = null;
    const options = {headers: httpHeaders};
    return this.http.post<any>('property.insurance', commandObject, options)
      .pipe(map(data => {
        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        if (data.ok) {
          const principal = new Principal();
          principal.authdata = window.btoa(username + ':' + password);
          localStorage.setItem('principal', JSON.stringify(principal));
          this.principalSubject.next(principal);
          const responseObject = new ResponseObject();
          responseObject.ok = data.ok;
          responseObject.payload = data.payload;
          return responseObject;
        } else {
          alert('Что-то пошло не так! Вызывайте разработчика!!!');
        }
      }));
  }

  request(commandObject: CommandObject): Observable<any> {
    const httpHeaders = new HttpHeaders({
      authorization: 'Basic ' + this.principalValue.authdata
    });
    return this.http.post<any>('property.insurance', commandObject, {headers: httpHeaders})
      .pipe(map(data => {
        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        if (data.ok) {
          const responseObject = new ResponseObject();
          responseObject.ok = data.ok;
          responseObject.payload = data.payload;
          return responseObject;
        } else {
          alert('Что-то пошло не так! Вызывайте разработчика!!!');
        }
      }));
  }

  logout(): void {
    // remove user from local storage to log user out
    localStorage.removeItem('principal');
    this.principalSubject.next(null);
    this.router.navigate(['/login']);
  }

  openContract(contract: PropertyInsuranceContract) {
    this.contractSubject.next(contract);
    this.router.navigate(['/contract']);
  }
}
