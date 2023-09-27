import dayjs from 'dayjs';

export interface IAuthor {
  id?: number;
  colA?: string;
  colB?: number;
  colC?: number;
  colD?: string | null;
  colE?: boolean | null;
  colF?: string | null;
}

export const defaultValue: Readonly<IAuthor> = {
  colE: false,
};
