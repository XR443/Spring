import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {RequestService} from "../services/request.service";
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {PropertyInsuranceContract} from "../domain/property.insurance.contract";
import {PropertyInsuranceObject} from "../domain/property.insurance.object";
import {formatDate} from "@angular/common";
import {CommandObject} from "../domain/command";
import {Individual} from "../domain/individual";
import {Address} from "../domain/address";
import {ChooseInsurerComponent} from "./insurer/choose/choose.insurer.component";
import {MatDialog} from "@angular/material/dialog";
import {ChangeInsurerComponent} from "./insurer/change/change.insurer.component";
import * as moment from 'moment';

@Component({
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css']
})
export class ContractComponent implements OnInit {
  availableYears = [];

  contract: PropertyInsuranceContract;

  calcForm: FormGroup;
  afterCalcForm: FormGroup;
  insurerForm: FormGroup;
  propertyForm: FormGroup;
  commentForm: FormGroup;
  contractInfo: FormGroup;

  allForms: FormGroup[];

  constructor(
    private router: Router,
    private requestService: RequestService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    for (let i = 1900; i <= new Date().getFullYear(); i++) {
      this.availableYears.push(i);
    }

    this.createCalcForm();
    this.createAfterCalcForm();
    this.createContractInfoForm();
    this.createInsurerForm();
    this.createPropertyForm();
    this.createCommentForm();

    this.requestService.contract.subscribe(value => {
      this.contract = value
      this.formSetValue();
    });

    this.allForms = [
      this.calcForm,
      this.afterCalcForm,
      this.insurerForm,
      this.propertyForm,
      this.contractInfo,
    ]
  }

  calculate() {
    if (!this.calcForm.valid) {
      alert('Заполните все поля необходимые для расчета')
    } else {
      const commandObject = new CommandObject();
      commandObject.command = 'calculatePropertyInsurancePremiumAction';
      commandObject.payload = this.contract;
      this.requestService.request(commandObject).subscribe(value => {
        if (value && value.ok) {
          this.contract = value.payload;
          this.formSetValue();
        }
      });
    }
  }

  private formSetValue() {
    if (!this.contract) {
      this.contract = new PropertyInsuranceContract();
      this.contract.insuranceObject = new PropertyInsuranceObject();
      this.contract.insuranceObject.address = new Address();
      this.contract.insurer = new Individual();
      this.contract.conclusionDate = new Date(formatDate(new Date(), 'yyyy-MM-dd', 'en'))
      this.requestService.contractSet(this.contract)
    }

    this.calcForm.controls.insuranceSum.setValue(this.contract?.insuranceObject?.insuranceSum?.toFixed(2));
    let insurancePeriodFrom = this.contract?.insurancePeriodFrom ? formatDate(this.contract?.insurancePeriodFrom, 'yyyy-MM-dd', 'en') : null;
    this.calcForm.controls.insurancePeriodFrom.setValue(insurancePeriodFrom);
    let insurancePeriodTo = this.contract?.insurancePeriodTo ? formatDate(this.contract?.insurancePeriodTo, 'yyyy-MM-dd', 'en') : null;
    this.calcForm.controls.insurancePeriodTo.setValue(insurancePeriodTo);
    this.calcForm.controls.propertyType.setValue(this.contract?.insuranceObject?.propertyType);
    this.calcForm.controls.constructionYear.setValue(this.contract?.insuranceObject?.constructionYear);
    this.calcForm.controls.area.setValue(this.contract?.insuranceObject?.area?.toFixed(2));

    this.contractInfo.controls.contractNumber.setValue(this.contract?.number);
    let conclusionDate = this.contract?.conclusionDate ? formatDate(this.contract?.conclusionDate, 'yyyy-MM-dd', 'en') : null;
    this.contractInfo.controls.conclusionDate.setValue(conclusionDate);

    let calculateDate = this.contract?.calculateDate ? formatDate(this.contract?.calculateDate, 'yyyy-MM-dd', 'en') : null;
    this.afterCalcForm.controls.calculateDate.setValue(calculateDate);
    this.afterCalcForm.controls.premium.setValue(this.contract?.insuranceObject?.insurancePremium?.toFixed(2));

    this.setInsurerForm();

    this.propertyForm.controls.country.setValue(this.contract?.insuranceObject?.address?.country);
    this.propertyForm.controls.region.setValue(this.contract?.insuranceObject?.address?.region);
    this.propertyForm.controls.city.setValue(this.contract?.insuranceObject?.address?.city);
    this.propertyForm.controls.street.setValue(this.contract?.insuranceObject?.address?.street);
    this.propertyForm.controls.house.setValue(this.contract?.insuranceObject?.address?.house);
    this.propertyForm.controls.apartment.setValue(this.contract?.insuranceObject?.address?.apartment);
    this.propertyForm.controls.index.setValue(this.contract?.insuranceObject?.address?.index);
    this.propertyForm.controls.building.setValue(this.contract?.insuranceObject?.address?.building);
    this.propertyForm.controls.housing.setValue(this.contract?.insuranceObject?.address?.housing);
    this.propertyForm.controls.district.setValue(this.contract?.insuranceObject?.address?.district);

    this.commentForm.controls.comment.setValue(this.contract?.comment);
  }

  private setInsurerForm() {
    let insurer = this.contract?.insurer;
    if (insurer) {
      let firstName = insurer.firstName ? insurer.firstName : '';
      let lastName = insurer.lastName ? insurer.lastName : '';
      let secondName = insurer.secondName ? insurer.secondName : '';
      let fullName = firstName + ' ' + lastName + ' ' + secondName;
      let value = fullName.trim();
      this.insurerForm.controls.fio.setValue(value && value.length > 0 ? value : null);
      let birthday = insurer?.birthday ? formatDate(insurer?.birthday, 'yyyy-MM-dd', 'en') : null;
      this.insurerForm.controls.birthday.setValue(birthday);
      this.insurerForm.controls.series.setValue(insurer?.document?.series);
      this.insurerForm.controls.number.setValue(insurer?.document?.number);
    }
  }

  insurancePeriodFromValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      if (control.value) {
        let insurancePeriodFromLessThenToday = new Date(formatDate(control.value, 'yyyy-MM-dd', 'en')) < new Date(formatDate(new Date(), 'yyyy-MM-dd', 'en'));
        if (insurancePeriodFromLessThenToday)
          return {'insurancePeriodFromLessThenTo': insurancePeriodFromLessThenToday};
      }
      return null;
    };
  }

  insurancePeriodToValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      let contract = this.requestService.contractValue;
      if (control.value && contract.insurancePeriodFrom) {

        let date = new Date(formatDate(control.value, 'yyyy-MM-dd', 'en'));
        let insurancePeriodToBiggerThenFrom = date > contract.insurancePeriodFrom;
        if (!insurancePeriodToBiggerThenFrom)
          return {'insurancePeriodToBiggerThenFrom': insurancePeriodToBiggerThenFrom};
        let years = moment(date).diff(moment(contract.insurancePeriodFrom), "years");
        let insurancePeriodToBiggerOneYearThenFrom = years >= 1;
        if (insurancePeriodToBiggerOneYearThenFrom)
          return {'insurancePeriodToBiggerOneYearThenFrom': insurancePeriodToBiggerOneYearThenFrom};
      }
      return null;
    };
  }

  private createCalcForm() {
    this.calcForm = this.formBuilder.group({
      insuranceSum: ['', Validators.required],
      insurancePeriodFrom: ['', [Validators.required, this.insurancePeriodFromValidator()]],
      insurancePeriodTo: ['', [Validators.required, this.insurancePeriodToValidator()]],
      propertyType: ['', Validators.required],
      constructionYear: ['', Validators.required],
      area: ['', Validators.required],
    });

    this.calcForm.controls.insuranceSum.valueChanges.subscribe(value => {
      if (!this.contract.insuranceObject) {
        this.contract.insuranceObject = new PropertyInsuranceObject();
      }
      if (value) {
        this.contract.insuranceObject.insuranceSum = parseFloat(parseFloat(value).toFixed(2));
      }
    });

    this.calcForm.controls.insurancePeriodFrom.valueChanges.subscribe(value => {
      this.contract.insurancePeriodFrom = value ? new Date(formatDate(value, 'yyyy-MM-dd', 'en')) : null;
    });

    this.calcForm.controls.insurancePeriodTo.valueChanges.subscribe(value => {
      this.contract.insurancePeriodTo = value ? new Date(formatDate(value, 'yyyy-MM-dd', 'en')) : null;
    });

    this.calcForm.controls.propertyType.valueChanges.subscribe(value => {
      if (!this.contract.insuranceObject) {
        this.contract.insuranceObject = new PropertyInsuranceObject();
      }
      this.contract.insuranceObject.propertyType = value;
    });

    this.calcForm.controls.constructionYear.valueChanges.subscribe(value => {
      if (!this.contract.insuranceObject) {
        this.contract.insuranceObject = new PropertyInsuranceObject();
      }
      this.contract.insuranceObject.constructionYear = value;
    });

    this.calcForm.controls.area.valueChanges.subscribe(value => {
      if (!this.contract.insuranceObject) {
        this.contract.insuranceObject = new PropertyInsuranceObject();
      }
      if (value) {
        this.contract.insuranceObject.area = parseFloat(parseFloat(value).toFixed(2));
      }
    });

  }

  private createAfterCalcForm() {
    this.afterCalcForm = this.formBuilder.group({
      calculateDate: ['', Validators.required],
      premium: ['', Validators.required],
    });

    this.afterCalcForm.controls.premium.valueChanges.subscribe(value => {
      if (!this.contract.insuranceObject) {
        this.contract.insuranceObject = new PropertyInsuranceObject();
      }
      if (value)
        this.contract.insuranceObject.insurancePremium = parseFloat(parseFloat(value).toFixed(2));
    });

    this.afterCalcForm.controls.calculateDate.valueChanges.subscribe(value => {
      this.contract.calculateDate = value ? new Date(formatDate(value, 'yyyy-MM-dd', 'en')) : null;
    });
  }

  private createContractInfoForm() {
    this.contractInfo = this.formBuilder.group({
      contractNumber: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
      conclusionDate: ['', Validators.required],
    });

    this.contractInfo.controls.contractNumber.valueChanges.subscribe(value => {
      this.contract.number = value;
    })
    this.contractInfo.controls.conclusionDate.valueChanges.subscribe(value => {
      this.contract.conclusionDate = value ? new Date(formatDate(value, 'yyyy-MM-dd', 'en')) : null;
    })
  }

  private createInsurerForm() {
    this.insurerForm = this.formBuilder.group({
      fio: ['', Validators.required],
      birthday: ['', Validators.required],
      series: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4)]],
      number: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
    });
  }

  private createPropertyForm() {
    this.propertyForm = this.formBuilder.group({
      country: ['', Validators.required],
      region: ['', Validators.required],
      city: ['', Validators.required],
      street: ['', Validators.required],
      house: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      apartment: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      index: [''],
      building: [''],
      housing: [''],
      district: [''],
    });

    this.propertyForm.controls.country.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.country = value;
    })
    this.propertyForm.controls.region.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.region = value;
    })
    this.propertyForm.controls.city.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.city = value;
    })
    this.propertyForm.controls.street.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.street = value;
    })
    this.propertyForm.controls.house.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.house = value;
    })
    this.propertyForm.controls.apartment.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.apartment = value;
    })
    this.propertyForm.controls.index.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.index = value;
    })
    this.propertyForm.controls.building.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.building = value;
    })
    this.propertyForm.controls.housing.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.housing = value;
    })
    this.propertyForm.controls.district.valueChanges.subscribe(value => {
      this.contract.insuranceObject.address.district = value;
    })
  }

  private createCommentForm() {
    this.commentForm = this.formBuilder.group({
      comment: [''],
    });

    this.commentForm.controls.comment.valueChanges.subscribe(value => {
      this.contract.comment = value;
    })
  }

  openChooseInsurerWindow() {
    this.requestService.contractSet(this.contract);
    this.dialog.open(ChooseInsurerComponent, {
      width: '750px',
    });
  }

  updateInsurer() {
    this.requestService.contractSet(this.contract);
    this.dialog.open(ChangeInsurerComponent, {
      width: '750px',
      data: this.contract.insurer
    });
  }

  save() {
    this.requestService.contractSet(this.contract);
    let commandObject = new CommandObject();
    commandObject.command = 'saveContractAction'
    commandObject.payload = this.contract
    this.requestService.request(commandObject).subscribe(value => {
      if (value.ok) {
        this.requestService.contractSet(null)
        this.router.navigate(['/']);
      }
    })
  }

  toContracts() {
    this.requestService.contractSet(null);
    this.router.navigate(['/']);
  }

  allFormsValid(): boolean {
    let result = false;
    for (const form of this.allForms) {
      result = result || form.valid
    }
    return result
  }
}
