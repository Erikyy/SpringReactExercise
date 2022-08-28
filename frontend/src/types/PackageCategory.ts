import type { Description } from './Description';

export type PackageCategory = {
  id: number;
  name: string;
  descriptions: Description[];
};
