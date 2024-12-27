import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEgresosDiarios } from '../egresos-diarios.model';
import { EgresosDiariosService } from '../service/egresos-diarios.service';

const egresosDiariosResolve = (route: ActivatedRouteSnapshot): Observable<null | IEgresosDiarios> => {
  const id = route.params.id;
  if (id) {
    return inject(EgresosDiariosService)
      .find(id)
      .pipe(
        mergeMap((egresosDiarios: HttpResponse<IEgresosDiarios>) => {
          if (egresosDiarios.body) {
            return of(egresosDiarios.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default egresosDiariosResolve;
