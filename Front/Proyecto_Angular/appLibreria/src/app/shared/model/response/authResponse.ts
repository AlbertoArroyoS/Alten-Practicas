export interface AuthResponse {
  idUsuario: number;
  username: string;
  token: string;
  role: string;
  idCliente?: number; // Propiedad opcional
  idLibreria?: number; // Propiedad opcional
}