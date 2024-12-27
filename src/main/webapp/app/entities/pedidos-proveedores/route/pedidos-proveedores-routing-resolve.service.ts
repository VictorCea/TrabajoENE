import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPedidosProveedores } from '../pedidos-proveedores.model';
import { PedidosProveedoresService } from '../service/pedidos-proveedores.service';

const pedidosProveedoresResolve = (route: ActivatedRouteSnapshot): Observable<null | IPedidosProveedores> => {
  const id = route.params.id;
  if (id) {
    return inject(PedidosProveedoresService)
      .find(id)
      .pipe(
        mergeMap((pedidosProveedores: HttpResponse<IPedidosProveedores>) => {
          if (pedidosProveedores.body) {
            return of(pedidosProveedores.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pedidosProveedoresResolve;
