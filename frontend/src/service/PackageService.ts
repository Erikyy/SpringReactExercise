import { API_URL } from '@app/types/Api';

export default class PackageService {
  public static getPackages(
    category: number,
    language: string,
    currency: string,
  ) {
    return fetch(`${API_URL}/packages?category=${category}`, {
      headers: {
        'Accept-Language': language,
        'Accept-Currency': currency,
      },
    }).then((res) => res.json());
  }

  public static getpackageById(id: number, language: string, currency: string) {
    return fetch(`${API_URL}/packages/${id}`, {
      headers: {
        'Accept-Language': language,
        'Accept-Currency': currency,
      },
    }).then((res) => res.json());
  }
}