// Generated by https://quicktype.io

export interface ShoppingCartResponse 
  {
    cartId: Number,
    totalPrice: Number,
    products: Products[]
}

export interface ShoppingCartIdResponse 
  {
    cartId: Number,
    totalPrice: Number,
    products: Products[]
}

export interface Products {
  id: number,
  nombre: String,
  cantidad: number,
  precio: number,
  codigo: String,
  imagen: String,
  stock: number,
  descripcion: String,
  categorias: Categoria[]
}

export interface Categoria {
  id: number;
  nombre: string;
  descripcion: string;
  imagen: string
}

