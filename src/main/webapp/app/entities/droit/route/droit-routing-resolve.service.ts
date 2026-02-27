import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDroit } from '../droit.model';
import { DroitService } from '../service/droit.service';

const droitResolve = (route: ActivatedRouteSnapshot): Observable<null | IDroit> => {
  const id = route.params.id;
  if (id) {
    return inject(DroitService)
      .find(id)
      .pipe(
        mergeMap((droit: HttpResponse<IDroit>) => {
          if (droit.body) {
            return of(droit.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default droitResolve;
