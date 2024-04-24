import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { Person } from 'src/app/interfaces/person';
@Component({
  selector: 'app-inf-person',
  templateUrl: './inf-person.component.html',
  styleUrls: ['./inf-person.component.scss']
})
export class InfPersonComponent {


  //Para enviar datos del padre al hijo
  @Input() person?: Person;

  constructor() { }

}
