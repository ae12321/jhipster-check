import { IAuthorInfo } from 'app/shared/model/author-info.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IAuthor {
  id?: number;
  name?: string | null;
  gender?: Gender | null;
  authorInfo?: IAuthorInfo | null;
}

export const defaultValue: Readonly<IAuthor> = {};
