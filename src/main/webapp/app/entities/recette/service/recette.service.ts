import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRecette, NewRecette } from '../../../shared/model/principal/recette.model';

export type PartialUpdateRecette = Partial<IRecette> & Pick<IRecette, 'id'>;

type RestOf<T extends IRecette | NewRecette> = Omit<T, 'dateRecette'> & {
  dateRecette?: string | null;
};

export type RestRecette = RestOf<IRecette>;

export type NewRestRecette = RestOf<NewRecette>;

export type PartialUpdateRestRecette = RestOf<PartialUpdateRecette>;

export type EntityResponseType = HttpResponse<IRecette>;
export type EntityArrayResponseType = HttpResponse<IRecette[]>;

@Injectable({ providedIn: 'root' })
export class RecetteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recettes');

  create(recette: NewRecette): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recette);
    return this.http
      .post<RestRecette>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(recette: IRecette): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recette);
    return this.http
      .put<RestRecette>(`${this.resourceUrl}/${this.getRecetteIdentifier(recette)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(recette: PartialUpdateRecette): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recette);
    return this.http
      .patch<RestRecette>(`${this.resourceUrl}/${this.getRecetteIdentifier(recette)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRecette>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRecette[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRecetteIdentifier(recette: Pick<IRecette, 'id'>): number {
    return recette.id;
  }

  compareRecette(o1: Pick<IRecette, 'id'> | null, o2: Pick<IRecette, 'id'> | null): boolean {
    return o1 && o2 ? this.getRecetteIdentifier(o1) === this.getRecetteIdentifier(o2) : o1 === o2;
  }

  addRecetteToCollectionIfMissing<Type extends Pick<IRecette, 'id'>>(
    recetteCollection: Type[],
    ...recettesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const recettes: Type[] = recettesToCheck.filter(isPresent);
    if (recettes.length > 0) {
      const recetteCollectionIdentifiers = recetteCollection.map(recetteItem => this.getRecetteIdentifier(recetteItem));
      const recettesToAdd = recettes.filter(recetteItem => {
        const recetteIdentifier = this.getRecetteIdentifier(recetteItem);
        if (recetteCollectionIdentifiers.includes(recetteIdentifier)) {
          return false;
        }
        recetteCollectionIdentifiers.push(recetteIdentifier);
        return true;
      });
      return [...recettesToAdd, ...recetteCollection];
    }
    return recetteCollection;
  }

  protected convertDateFromClient<T extends IRecette | NewRecette | PartialUpdateRecette>(recette: T): RestOf<T> {
    return {
      ...recette,
      dateRecette: recette.dateRecette?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRecette: RestRecette): IRecette {
    return {
      ...restRecette,
      dateRecette: restRecette.dateRecette ? dayjs(restRecette.dateRecette) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRecette>): HttpResponse<IRecette> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRecette[]>): HttpResponse<IRecette[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
