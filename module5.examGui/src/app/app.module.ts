import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {ContractsComponent} from './contracts/contracts.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './helper/auth.guard';
import {ContractComponent} from "./contract/contract.component";
import {ChooseInsurerComponent} from "./contract/insurer/choose/choose.insurer.component";
import {MatDialogModule} from "@angular/material/dialog";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {NewInsurerComponent} from "./contract/insurer/new/new.insurer.component";
import {ChangeInsurerComponent} from "./contract/insurer/change/change.insurer.component";
import {NgxMaskModule} from "ngx-mask";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ContractsComponent,
    ContractComponent,
    ChooseInsurerComponent,
    NewInsurerComponent,
    ChangeInsurerComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: '', component: ContractsComponent, canActivate: [AuthGuard]},
      {path: 'contract', component: ContractComponent, canActivate: [AuthGuard]},
      {path: 'login', component: LoginComponent},
      {path: '**', redirectTo: ''}
    ]),
    MatDialogModule,
    BrowserAnimationsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    NgxMaskModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
