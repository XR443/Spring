import {Document} from "./document";

export class Individual {
  id: number;
  lastName: string;
  firstName: string;
  secondName: string;
  birthday: Date = null;
  citizenship: string;
  document: Document;
}
