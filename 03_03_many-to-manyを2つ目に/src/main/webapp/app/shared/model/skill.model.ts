import { IStudent } from 'app/shared/model/student.model';

export interface ISkill {
  id?: number;
  type?: string | null;
  students?: IStudent[] | null;
}

export const defaultValue: Readonly<ISkill> = {};
