import { IAuthor } from 'app/shared/model/author.model';

export interface IAuthorInfo {
  id?: number;
  age?: number | null;
  address?: string | null;
  author?: IAuthor | null;
}

export const defaultValue: Readonly<IAuthorInfo> = {};
