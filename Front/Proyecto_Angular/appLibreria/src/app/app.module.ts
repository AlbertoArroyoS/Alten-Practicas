import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookCatalogModule } from './book-catalog/book-catalog.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BookCatalogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
