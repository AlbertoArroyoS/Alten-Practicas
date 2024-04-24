import { Person } from './interfaces/person';
import { Component } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'appLibreria';

  public getPerson(): Person {
    let person: Person = {
      "name" : 'Alten - Alberto Arroyo',
    };
    return person;
  }

}


