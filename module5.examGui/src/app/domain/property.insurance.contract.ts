import {Individual} from "./individual";
import {PropertyInsuranceObject} from "./property.insurance.object";

export class PropertyInsuranceContract {
  id: number;
  number: string;
  insurancePeriodFrom: Date = null;
  insurancePeriodTo: Date = null;
  calculateDate: Date = null;
  conclusionDate: Date = null;
  insurer: Individual;
  insuranceObject: PropertyInsuranceObject;
  comment: string;
}
