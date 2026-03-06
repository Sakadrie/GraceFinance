/* eslint-disable @typescript-eslint/no-redundant-type-constituents */
export interface IRequestHeader {
  propriete?: string;
  expression?:
    | 'equals'
    | 'contains'
    | 'specified'
    | 'in'
    | 'notIn'
    | 'greaterThan'
    | 'lessThan'
    | 'greaterThanOrEqual'
    | 'lessThanOrEqual'
    | 'notEquals';
  value?: any | any[];
}

export class RequestHeader implements IRequestHeader {
  constructor(
    public propriete: string,
    public expression:
      | 'equals'
      | 'contains'
      | 'specified'
      | 'in'
      | 'notIn'
      | 'greaterThan'
      | 'lessThan'
      | 'greaterThanOrEqual'
      | 'lessThanOrEqual'
      | 'notEquals',
    public value: any,
  ) {}
}

export interface IQueryParams {
  propriete?: string;
  value?: any;
}

export class QueryParams implements IQueryParams {
  constructor(
    public propriete: string,
    public value: any,
  ) {}
}
