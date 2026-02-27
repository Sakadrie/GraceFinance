import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntiteFinanciere } from '../entite-financiere.model';
import { EntiteFinanciereService } from '../service/entite-financiere.service';

const entiteFinanciereResolve = (route: ActivatedRouteSnapshot): Observable<null | IEntiteFinanciere> => {
  const id = route.params.id;
  if (id) {
    return inject(EntiteFinanciereService)
      .find(id)
      .pipe(
        mergeMap((entiteFinanciere: HttpResponse<IEntiteFinanciere>) => {
          if (entiteFinanciere.body) {
            return of(entiteFinanciere.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default entiteFinanciereResolve;
