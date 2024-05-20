import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/auth/login.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent {

  //inyectamos dependencias
  userLoginOn:boolean=false;
  

  private router: Router = inject(Router);

  constructor(private loginService:LoginService) { }


  public goToUser(): void {
    this.router.navigate(['dashboard/']);
    
  }

  public goToBook(): void {
    this.router.navigate(['book-catalog/book']);
    
  }

  public goToBookShop(): void {
    this.router.navigate(['book-catalog/book-shop']);
  }

  public goToAuthor(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToAdminBook(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToAdminBookShop(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToAdminAuthor(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToPurchases(): void {
    this.router.navigate(['book-catalog/purchases']);
  }

  ngOnInit(): void {
    this.loginService.currentUserLoginOn.subscribe(
      {
        next:(userLoginOn) => {
          this.userLoginOn=userLoginOn;
        }
      }
    )
  }

  logout()
  {
    this.loginService.logout();
    this.router.navigate(['/sign-in'])
  }



}
