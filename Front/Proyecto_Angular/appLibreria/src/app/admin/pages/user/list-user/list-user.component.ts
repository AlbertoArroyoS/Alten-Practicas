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

  formularioUsuario: FormGroup;

  @ViewChild('content', { static: true }) modalContent!: ElementRef;

  constructor(
    public fb: FormBuilder,
    private modalService: NgbModal,
    private userService: UserService,
    private loginService: LoginService
  ) {
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;

    this.formularioUsuario = this.fb.group({
      idUsuario: [''],
      username: new FormControl('', Validators.required),
      nombre: new FormControl('', Validators.required),
      apellidos: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      ciudad: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
    this.recargarTablaUsuarios();
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
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar la lista de usuarios.';
          console.error('Error fetching users', error);
        }
      })
    );
  }

  buscarUsuarios(keyword: string) {
    this.subscription.add(
      this.userService.searchUsersByKeyword(keyword).subscribe({
        next: (data) => {
          this.usuarios = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        },
        error: (error) => {
          this.errorMessage = 'Error al buscar usuarios.';
          console.error('Error searching users', error);
        }
      })
    );
  }



  guardarUsuario(): void {
    if (this.formularioUsuario.valid) {
      this.subscription.add(
        this.userService.addUser(this.formularioUsuario.value).subscribe({
          next: (resp) => {
            this.showSuccessAlert('Usuario guardado correctamente');
            this.botonNuevoUsuarioVisible = false;
            this.recargarTablaUsuarios();
          },
          error: (error) => {
            this.showWarningAlert('Conflicto al guardar el usuario.');
          },
        })
      );
    } else {
      this.formularioUsuario.markAllAsTouched();
      this.showWarningAlert('El formulario no está completo');
    }
  }

  botonNuevoUsuario(): void {
    this.formularioUsuario.reset();
    this.botonNuevoUsuarioVisible = !this.botonNuevoUsuarioVisible;
  }

  editarUsuario(user: any): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.botonNuevoUsuarioVisible = true;
    this.modificarUsuario = true;
    this.mostrarBotonGuardar = false;
    this.formularioUsuario.patchValue({
      idUsuario: user.idUsuario,
      username: user.username,
      nombre: user.nombre,
      apellidos: user.apellidos,
      email: user.email,
      ciudad: user.ciudad,
    });
  }

  actualizarUsuario(): void {
    const idControl = this.formularioUsuario.get('idUsuario');
    if (idControl) {
      const userId = idControl.value;
      this.subscription.add(
        this.userService.updateUser(userId, this.formularioUsuario.value).subscribe({
          next: (resp) => {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            this.guardadoExitoso = true;
            this.botonNuevoUsuarioVisible = false;
            this.modificarUsuario = false;
            this.formularioUsuario.reset();
            const index = this.usuarios.findIndex((u) => u.idUsuario === userId);
            if (index !== -1) this.usuarios[index] = resp;
            this.showSuccessAlert('Usuario actualizado correctamente');
            this.recargarTablaUsuarios();
          },
          error: (error) => {
            this.showWarningAlert('Conflicto al actualizar el usuario.');
          }
        })
      );
    }
  }

  eliminarUsuario(user: any): void {
    this.subscription.add(
      this.userService.deleteUserById(user.idUsuario).subscribe({
        next: (resp) => {
          window.scrollTo({ top: 0, behavior: 'smooth' });
          this.recargarTablaUsuarios();
          this.showSuccessAlert('Usuario eliminado correctamente');
        },
        error: (error) => {
          this.showWarningAlert('Conflicto al eliminar el usuario.');
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
