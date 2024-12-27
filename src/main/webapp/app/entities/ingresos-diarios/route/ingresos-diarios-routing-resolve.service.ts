import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIngresosDiarios } from '../ingresos-diarios.model';
import { IngresosDiariosService } from '../service/ingresos-diarios.service';

const ingresosDiariosResolve = (route: ActivatedRouteSnapshot): Observable<null | IIngresosDiarios> => {
  const id = route.params.id;
  if (id) {
    return inject(IngresosDiariosService)
      .find(id)
      .pipe(
        mergeMap((ingresosDiarios: HttpResponse<IIngresosDiarios>) => {
          if (ingresosDiarios.body) {
            return of(ingresosDiarios.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default ingresosDiariosResolve;
