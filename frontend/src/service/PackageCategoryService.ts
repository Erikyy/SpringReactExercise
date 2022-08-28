import { API_URL } from '@app/types/Api';

export default class PackageCategoryService {
  static getAllCategoriesByLanguage(lang: string) {
    return fetch(`${API_URL}/categories`, {
      headers: {
        'Accept-Language': lang,
      },
    }).then((res) => res.json());
  }
}
