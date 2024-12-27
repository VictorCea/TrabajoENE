import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMesa } from '../mesa.model';
import { MesaService } from '../service/mesa.service';

const mesaResolve = (route: ActivatedRouteSnapshot): Observable<null | IMesa> => {
  const id = route.params.id;
  if (id) {
    return inject(MesaService)
      .find(id)
      .pipe(
        mergeMap((mesa: HttpResponse<IMesa>) => {
          if (mesa.body) {
            return of(mesa.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default mesaResolve;
