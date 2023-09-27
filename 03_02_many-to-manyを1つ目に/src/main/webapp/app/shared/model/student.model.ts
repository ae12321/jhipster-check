import { ISkill } from 'app/shared/model/skill.model';

export interface IStudent {
  id?: number;
  name?: string | null;
  skills?: ISkill[] | null;
}

export const defaultValue: Readonly<IStudent> = {};
