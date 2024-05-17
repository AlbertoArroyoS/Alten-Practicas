import { Role } from '../Rol/Role';

export interface UserRequest {
  idUsuario: number;
  username: string;
  enabled: number;
  role: Role;
  idCliente: number;
  nombre: string;
  apellidos: string;
  idLibreria: number;
  nombreLibreria: string;
  nombreDueno: string;
  direccion: string;
  email: string;
  ciudad: string;

}
