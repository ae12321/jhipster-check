export interface IAuthorInfo {
  id?: number;
  age?: number | null;
  address?: string | null;
}

export const defaultValue: Readonly<IAuthorInfo> = {};
