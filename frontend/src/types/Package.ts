import type { PackageCategory } from './PackageCategory';
import type { Description } from './Description';
import type { PackageType } from './PackageType';

export type Package = {
  id: number;
  packageType: PackageType;
  descriptions: Description[];
  price: number;
  category: PackageCategory;
};
