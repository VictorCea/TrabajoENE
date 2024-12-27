import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrdenesCocina } from '../ordenes-cocina.model';
import { OrdenesCocinaService } from '../service/ordenes-cocina.service';

const ordenesCocinaResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrdenesCocina> => {
  const id = route.params.id;
  if (id) {
    return inject(OrdenesCocinaService)
      .find(id)
      .pipe(
        mergeMap((ordenesCocina: HttpResponse<IOrdenesCocina>) => {
          if (ordenesCocina.body) {
            return of(ordenesCocina.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default ordenesCocinaResolve;
