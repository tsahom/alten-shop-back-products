import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from './product.class';

@Injectable({
    providedIn: 'root'
})
export class ProductsService {

    private static productslist: Product[] = null;
    private products$: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);
    private productUrl: string;

    constructor(private http: HttpClient) {
        this.productUrl = "http://localhost:8080/products"
    }

    

    getProducts(): Observable<Product[]> {
        if( ! ProductsService.productslist )
        {
            this.http.get<Product[]>(this.productUrl).subscribe(data => {
                ProductsService.productslist = data;
                
                this.products$.next(ProductsService.productslist);
            });
        }
        else
        {
            this.products$.next(ProductsService.productslist);
            this.http.get<Product>(this.productUrl).subscribe();
        }

        return this.products$;
    }

    create(prod: Product): Observable<Product[]> {
        ProductsService.productslist.push(prod);
        this.products$.next(ProductsService.productslist);
        this.http.post<Product>(this.productUrl,prod).subscribe();
        return this.products$;
    }

    update(prod: Product): Observable<Product[]> {
        ProductsService.productslist.forEach(element => {
            if (element.id == prod.id) {
                element.name = prod.name;
                element.category = prod.category;
                element.code = prod.code;
                element.description = prod.description;
                element.image = prod.image;
                element.inventoryStatus = prod.inventoryStatus;
                element.price = prod.price;
                element.quantity = prod.quantity;
                element.rating = prod.rating;
            }
        });
        this.http.patch<Product>(this.productUrl+"/"+prod.id, prod).subscribe();
        this.products$.next(ProductsService.productslist);
        return this.products$;
    }

    delete(id: number): Observable<Product[]> {
        ProductsService.productslist = ProductsService.productslist.filter(value => { return value.id !== id });
        this.products$.next(ProductsService.productslist);
        this.http.delete<Product>(this.productUrl+"/"+id).subscribe();
        return this.products$;
    }
}