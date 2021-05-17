import {Component, OnInit} from "@angular/core";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RequestService} from "../../../services/request.service";
import {Individual} from "../../../domain/individual";
import {formatDate} from "@angular/common";
import {CommandObject} from "../../../domain/command";

@Component({
  selector: 'app-new-insurer-dialog',
  templateUrl: './new.insurer.component.html',
  styleUrls: ['./new.insurer.component.css']
})
export class NewInsurerComponent implements OnInit {
  insurer: Individual = new Individual();
  insurerForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<NewInsurerComponent>,
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
      birthday: ['', Validators.required]
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
  }

  save() {
    let commandObject = new CommandObject();
    commandObject.command = 'saveIndividualNoDocAction'
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
