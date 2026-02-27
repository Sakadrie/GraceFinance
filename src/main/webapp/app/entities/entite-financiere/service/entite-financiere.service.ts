import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntiteFinanciere, NewEntiteFinanciere } from '../entite-financiere.model';

export type PartialUpdateEntiteFinanciere = Partial<IEntiteFinanciere> & Pick<IEntiteFinanciere, 'id'>;

export type EntityResponseType = HttpResponse<IEntiteFinanciere>;
export type EntityArrayResponseType = HttpResponse<IEntiteFinanciere[]>;

@Injectable({ providedIn: 'root' })
export class EntiteFinanciereService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entite-financieres');

  create(entiteFinanciere: NewEntiteFinanciere): Observable<EntityResponseType> {
    return this.http.post<IEntiteFinanciere>(this.resourceUrl, entiteFinanciere, { observe: 'response' });
  }

  update(entiteFinanciere: IEntiteFinanciere): Observable<EntityResponseType> {
    return this.http.put<IEntiteFinanciere>(
      `${this.resourceUrl}/${this.getEntiteFinanciereIdentifier(entiteFinanciere)}`,
      entiteFinanciere,
      { observe: 'response' },
    );
  }

  partialUpdate(entiteFinanciere: PartialUpdateEntiteFinanciere): Observable<EntityResponseType> {
    return this.http.patch<IEntiteFinanciere>(
      `${this.resourceUrl}/${this.getEntiteFinanciereIdentifier(entiteFinanciere)}`,
      entiteFinanciere,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntiteFinanciere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntiteFinanciere[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEntiteFinanciereIdentifier(entiteFinanciere: Pick<IEntiteFinanciere, 'id'>): number {
    return entiteFinanciere.id;
  }

  compareEntiteFinanciere(o1: Pick<IEntiteFinanciere, 'id'> | null, o2: Pick<IEntiteFinanciere, 'id'> | null): boolean {
    return o1 && o2 ? this.getEntiteFinanciereIdentifier(o1) === this.getEntiteFinanciereIdentifier(o2) : o1 === o2;
  }

  addEntiteFinanciereToCollectionIfMissing<Type extends Pick<IEntiteFinanciere, 'id'>>(
    entiteFinanciereCollection: Type[],
    ...entiteFinancieresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const entiteFinancieres: Type[] = entiteFinancieresToCheck.filter(isPresent);
    if (entiteFinancieres.length > 0) {
      const entiteFinanciereCollectionIdentifiers = entiteFinanciereCollection.map(entiteFinanciereItem =>
        this.getEntiteFinanciereIdentifier(entiteFinanciereItem),
      );
      const entiteFinancieresToAdd = entiteFinancieres.filter(entiteFinanciereItem => {
        const entiteFinanciereIdentifier = this.getEntiteFinanciereIdentifier(entiteFinanciereItem);
        if (entiteFinanciereCollectionIdentifiers.includes(entiteFinanciereIdentifier)) {
          return false;
        }
        entiteFinanciereCollectionIdentifiers.push(entiteFinanciereIdentifier);
        return true;
      });
      return [...entiteFinancieresToAdd, ...entiteFinanciereCollection];
    }
    return entiteFinanciereCollection;
  }
}
