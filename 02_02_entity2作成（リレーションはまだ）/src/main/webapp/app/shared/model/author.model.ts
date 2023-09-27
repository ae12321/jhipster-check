import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IAuthor {
  id?: number;
  name?: string | null;
  gender?: Gender | null;
}

export const defaultValue: Readonly<IAuthor> = {};
