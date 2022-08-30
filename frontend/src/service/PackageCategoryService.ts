import { API_URL } from '@app/constants';
import { PackageCategory } from '@app/types/PackageCategory';

export default class PackageCategoryService {
  static getAllCategoriesByLanguage(lang: string): Promise<PackageCategory[]> {
    return fetch(`${API_URL}/categories`, {
      headers: {
        'Accept-Language': lang,
      },
    }).then((res) => res.json());
  }
}
