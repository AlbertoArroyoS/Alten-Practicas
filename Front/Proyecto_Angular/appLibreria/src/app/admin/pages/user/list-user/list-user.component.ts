import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription, Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/services/users/user.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';
import { LoginService } from 'src/app/services/auth/login.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit, OnDestroy {
  protected subscription: Subscription = new Subscription();
  public title: string = 'Lista de usuarios';
  usuarios: any[] = [];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  botonNuevoUsuarioVisible: boolean = false;
  modificarUsuario: boolean = false;
  mostrarBotonGuardar: boolean = true;
  alertaConflicto: boolean = false;
  successMessage: string = 'Usuario guardado correctamente';
  warningMessage: string = '';
  eliminadoExitoso: boolean = false;
  guardadoExitoso: boolean = false;
  userLoginOn$: Observable<boolean>;
  user$: Observable<AuthResponse | null>;
  userData?: UserRequest;
  errorMessage?: string;

  @ViewChild('content', { static: true }) modalContent!: ElementRef;

  constructor(
    public fb: FormBuilder,
    private modalService: NgbModal,
    private userService: UserService,
    private loginService: LoginService
  ) {
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;
  }

  ngOnInit(): void {
    this.loadUsuarios();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  recargarTablaUsuarios(): void {
    this.loadUsuarios();
  }

  private loadUsuarios(): void {
    this.subscription.add(
      this.userService.getAllUsers(this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.usuarios = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
          //console.log('Usuarios', data);
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar la lista de usuarios.';
          console.error('Error fetching users', error);
        }
      })
    );
  }

  private loadAdmins(): void {
    this.subscription.add(
      this.userService.getAllAdmins(this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.usuarios = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar la lista de usuarios.';
          console.error('Error fetching users', error);
        }
      })
    );
  }

  showSuccessAlert(message: string): void {
    this.guardadoExitoso = true;
    this.successMessage = message;
    setTimeout(() => {
      this.guardadoExitoso = false;
    }, 4000);
  }

  showWarningAlert(message: string): void {
    this.warningMessage = message;
    this.alertaConflicto = true;
    setTimeout(() => {
      this.alertaConflicto = false;
    }, 4000);
  }

  changePage(pageNumber: number): void {
    this.currentPage = pageNumber;
    this.loadUsuarios();
  }

  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement;
    const size = Number(element.value);
    this.pageSize = size;
    this.changePage(0);
  }
}
