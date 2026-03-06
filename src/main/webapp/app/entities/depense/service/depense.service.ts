import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDepense, NewDepense } from '../../../shared/model/principal/depense.model';

export type PartialUpdateDepense = Partial<IDepense> & Pick<IDepense, 'id'>;

type RestOf<T extends IDepense | NewDepense> = Omit<T, 'dateDepense' | 'dateValidation'> & {
  dateDepense?: string | null;
  dateValidation?: string | null;
};

export type RestDepense = RestOf<IDepense>;

export type NewRestDepense = RestOf<NewDepense>;

export type PartialUpdateRestDepense = RestOf<PartialUpdateDepense>;

export type EntityResponseType = HttpResponse<IDepense>;
export type EntityArrayResponseType = HttpResponse<IDepense[]>;

@Injectable({ providedIn: 'root' })
export class DepenseService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/depenses');

  create(depense: NewDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depense);
    return this.http
      .post<RestDepense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(depense: IDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depense);
    return this.http
      .put<RestDepense>(`${this.resourceUrl}/${this.getDepenseIdentifier(depense)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(depense: PartialUpdateDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depense);
    return this.http
      .patch<RestDepense>(`${this.resourceUrl}/${this.getDepenseIdentifier(depense)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDepense>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDepense[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDepenseIdentifier(depense: Pick<IDepense, 'id'>): number {
    return depense.id;
  }

  compareDepense(o1: Pick<IDepense, 'id'> | null, o2: Pick<IDepense, 'id'> | null): boolean {
    return o1 && o2 ? this.getDepenseIdentifier(o1) === this.getDepenseIdentifier(o2) : o1 === o2;
  }

  addDepenseToCollectionIfMissing<Type extends Pick<IDepense, 'id'>>(
    depenseCollection: Type[],
    ...depensesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const depenses: Type[] = depensesToCheck.filter(isPresent);
    if (depenses.length > 0) {
      const depenseCollectionIdentifiers = depenseCollection.map(depenseItem => this.getDepenseIdentifier(depenseItem));
      const depensesToAdd = depenses.filter(depenseItem => {
        const depenseIdentifier = this.getDepenseIdentifier(depenseItem);
        if (depenseCollectionIdentifiers.includes(depenseIdentifier)) {
          return false;
        }
        depenseCollectionIdentifiers.push(depenseIdentifier);
        return true;
      });
      return [...depensesToAdd, ...depenseCollection];
    }
    return depenseCollection;
  }

  protected convertDateFromClient<T extends IDepense | NewDepense | PartialUpdateDepense>(depense: T): RestOf<T> {
    return {
      ...depense,
      dateDepense: depense.dateDepense?.format(DATE_FORMAT) ?? null,
      dateValidation: depense.dateValidation?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDepense: RestDepense): IDepense {
    return {
      ...restDepense,
      dateDepense: restDepense.dateDepense ? dayjs(restDepense.dateDepense) : undefined,
      dateValidation: restDepense.dateValidation ? dayjs(restDepense.dateValidation) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDepense>): HttpResponse<IDepense> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDepense[]>): HttpResponse<IDepense[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
