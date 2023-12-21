export interface ResultsResponse {
    id: number;
    nombre: string;
    precio: number;
    stock: number;
    categorias: CategoriaResponse[];
    descripcion: string;
    codigo: string;
    imagen: string;
    ratings: RatingResponse[];
  }
  
  export interface CategoriaResponse {
    id: number;
    nombre: string;
    descripcion: string;
    imagen: string;
  }
  
  export interface RatingResponse {
    id: number;
    rating: number;
    comment: string;
    date: string;
    userNombre: string;
    userFirstName: string;
  }
  