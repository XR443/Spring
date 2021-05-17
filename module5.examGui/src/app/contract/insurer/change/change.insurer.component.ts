import {Component, Inject, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {formatDate} from "@angular/common";
import {CommandObject} from "../../../domain/command";
import {Individual} from "../../../domain/individual";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {RequestService} from "../../../services/request.service";
import {Document} from "../../../domain/document";

@Component({
  selector: 'app-change-insurer-dialog',
  templateUrl: './change.insurer.component.html',
  styleUrls: ['./change.insurer.component.css']
})
export class ChangeInsurerComponent implements OnInit {
  insurerForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<ChangeInsurerComponent>,
    private formBuilder: FormBuilder,
    private requestService: RequestService,
    @Inject(MAT_DIALOG_DATA) public insurer: Individual) {
  }

  ngOnInit(): void {
    if (!this.insurer.document)
      this.insurer.document = new Document();
    this.createInsurerForm();
    this.setInsurerForm();
  }

  private createInsurerForm() {
    this.insurerForm = this.formBuilder.group({
      lastName: ['', Validators.required],
      firstName: ['', Validators.required],
      secondName: [''],
      birthday: ['', Validators.required],
      series: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4)]],
      number: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
    });
    this.insurerForm.controls.lastName.valueChanges.subscribe(value => {
      this.insurer.lastName = value;
    })
    this.insurerForm.controls.firstName.valueChanges.subscribe(value => {
      this.insurer.firstName = value;
    })
    this.insurerForm.controls.secondName.valueChanges.subscribe(value => {
      this.insurer.secondName = value;
    })
    this.insurerForm.controls.birthday.valueChanges.subscribe(value => {
      this.insurer.birthday = value ? new Date(formatDate(value, 'yyyy-MM-dd', 'en')) : null
    })
    this.insurerForm.controls.series.valueChanges.subscribe(value => {
      this.insurer.document.series = value
    })
    this.insurerForm.controls.number.valueChanges.subscribe(value => {
      this.insurer.document.number = value
    })
  }

  private setInsurerForm() {
    this.insurerForm.controls.lastName.setValue(this.insurer?.lastName);
    this.insurerForm.controls.firstName.setValue(this.insurer?.firstName);
    this.insurerForm.controls.secondName.setValue(this.insurer?.secondName);
    let birthday = this.insurer?.birthday ? formatDate(this.insurer?.birthday, 'yyyy-MM-dd', 'en') : null;
    this.insurerForm.controls.birthday.setValue(birthday);
    this.insurerForm.controls.series.setValue(this.insurer?.document?.series);
    this.insurerForm.controls.number.setValue(this.insurer?.document?.number);
  }

  save() {
    let commandObject = new CommandObject();
    commandObject.command = 'saveIndividualAction'
    commandObject.payload = this.insurer
    this.requestService.request(commandObject).subscribe(value => {
      if (value.ok) {
        let contract = this.requestService.contractValue;
        contract.insurer = value.payload;
        this.requestService.contractSet(contract)
        this.dialogRef.close()
      }
    })
  }

  close() {
    this.dialogRef.close()
  }
}
