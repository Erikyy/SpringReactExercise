import { API_URL } from '@app/types/Api';

/**
 * Utils service for fetching supported languages and currencies
 */
export default class UtilsService {
  public static getCurrencies(): Promise<string[]> {
    return fetch(`${API_URL}/currencies`)
      .then((res) => res.json())
      .then((data) => data);
  }

  public static getLanguages(): Promise<string[]> {
    return fetch(`${API_URL}/languages`)
      .then((res) => res.json())
      .then((data) => data);
  }
}
