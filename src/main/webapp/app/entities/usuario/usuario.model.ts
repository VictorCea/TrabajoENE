export interface IUsuario {
  id: number;
  nombre?: string | null;
  rol?: string | null;
  rut?: string | null;
  admin?: boolean | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
