import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Individual} from "../../../domain/individual";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../../services/request.service";
import {CommandObject} from "../../../domain/command";
import {NewInsurerComponent} from "../new/new.insurer.component";

@Component({
  selector: 'app-choose-insurer-dialog',
  templateUrl: './choose.insurer.component.html',
  styleUrls: ['./choose.insurer.component.css']
})
export class ChooseInsurerComponent implements OnInit {
  insurerForm: FormGroup;
  insurer: Individual = new Individual();
  insurers: Individual[] = [];
  selected: Individual;

  constructor(
    public dialogRef: MatDialogRef<ChooseInsurerComponent>,
    private formBuilder: FormBuilder,
    private requestService: RequestService,
    private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.createInsurerForm();
  }

  private createInsurerForm() {
    this.insurerForm = this.formBuilder.group({
      lastName: ['', Validators.required],
      firstName: ['', Validators.required],
      secondName: [''],
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
  }

  onCloseClick() {
    this.dialogRef.close();
  }

  find() {
    const commandObject = new CommandObject();
    commandObject.command = 'findIndividualsByNameAction';
    commandObject.payload = this.insurer;
    this.requestService.request(commandObject).subscribe(value => {
      if (value && value.ok) {
        this.insurers = value.payload;
      }
    });
  }

  select(insurer: any) {
    this.selected = insurer;
  }

  highlight(insurer: any) {
    return this.selected?.id === insurer.id
  }

  createNewInsurer() {
    this.dialogRef.close()
    this.dialog.open(NewInsurerComponent, {
      width: '750px',
    });
  }

  closeAndSelect() {
    this.dialogRef.close()
    let contract = this.requestService.contractValue;
    contract.insurer = this.selected;
    this.requestService.contractSet(contract)
  }
}
