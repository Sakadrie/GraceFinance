import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICaisse, NewCaisse } from '../../../shared/model/principal/caisse.model';

export type PartialUpdateCaisse = Partial<ICaisse> & Pick<ICaisse, 'id'>;

export type EntityResponseType = HttpResponse<ICaisse>;
export type EntityArrayResponseType = HttpResponse<ICaisse[]>;

@Injectable({ providedIn: 'root' })
export class CaisseService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/caisses');

  create(caisse: NewCaisse): Observable<EntityResponseType> {
    return this.http.post<ICaisse>(this.resourceUrl, caisse, { observe: 'response' });
  }

  update(caisse: ICaisse): Observable<EntityResponseType> {
    return this.http.put<ICaisse>(`${this.resourceUrl}/${this.getCaisseIdentifier(caisse)}`, caisse, { observe: 'response' });
  }

  partialUpdate(caisse: PartialUpdateCaisse): Observable<EntityResponseType> {
    return this.http.patch<ICaisse>(`${this.resourceUrl}/${this.getCaisseIdentifier(caisse)}`, caisse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICaisse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICaisse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCaisseIdentifier(caisse: Pick<ICaisse, 'id'>): number {
    return caisse.id;
  }

  compareCaisse(o1: Pick<ICaisse, 'id'> | null, o2: Pick<ICaisse, 'id'> | null): boolean {
    return o1 && o2 ? this.getCaisseIdentifier(o1) === this.getCaisseIdentifier(o2) : o1 === o2;
  }

  addCaisseToCollectionIfMissing<Type extends Pick<ICaisse, 'id'>>(
    caisseCollection: Type[],
    ...caissesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const caisses: Type[] = caissesToCheck.filter(isPresent);
    if (caisses.length > 0) {
      const caisseCollectionIdentifiers = caisseCollection.map(caisseItem => this.getCaisseIdentifier(caisseItem));
      const caissesToAdd = caisses.filter(caisseItem => {
        const caisseIdentifier = this.getCaisseIdentifier(caisseItem);
        if (caisseCollectionIdentifiers.includes(caisseIdentifier)) {
          return false;
        }
        caisseCollectionIdentifiers.push(caisseIdentifier);
        return true;
      });
      return [...caissesToAdd, ...caisseCollection];
    }
    return caisseCollection;
  }
}
