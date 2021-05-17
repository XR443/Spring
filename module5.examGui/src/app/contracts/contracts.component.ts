import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RequestService} from "../services/request.service";
import {FormBuilder} from "@angular/forms";
import {CommandObject} from "../domain/command";
import {PropertyInsuranceContract} from "../domain/property.insurance.contract";

@Component({
  templateUrl: './contracts.component.html',
  styleUrls: ['./contracts.component.css']
})
export class ContractsComponent implements OnInit {
  contracts: PropertyInsuranceContract[];
  selected: PropertyInsuranceContract;
  loading = false;

  constructor(
    private router: Router,
    private requestService: RequestService,
    // private formBuilder: FormBuilder,
    private route: ActivatedRoute,
  ) {
  }

  openByDblclick(contract: PropertyInsuranceContract): void {
    this.requestService.openContract(contract);
    this.router.navigate(['/contract']);
  }

  ngOnInit(): void {
    this.loading = true;
    const commandObject = new CommandObject();
    commandObject.command = 'getAllContractsAction';
    this.requestService.request(commandObject).subscribe(value => {
      this.contracts = value.payload
      this.loading = false;
    });
  }

  select(contract: PropertyInsuranceContract) {
    this.selected = contract;
    this.requestService.contractSet(contract);
  }

  highlight(contract: PropertyInsuranceContract): boolean {
    return this.selected?.id === contract.id
  }
}
