import { API_URL } from '@app/types/Api';
import type { Order } from '@app/types/Order';
import type { CreateOrderDto } from '@app/types/CreateOrderDto';

export default class OrderService {
  public static getOrders(currency: string) {
    const data = fetch(`${API_URL}/orders`, {
      headers: {
        'Accept-Currency': currency,
      },
    }).then((res) => res.json());
    return data;
  }

  public static getOrderById(id: number, currency: string) {
    return fetch(`${API_URL}/orders/${id}`, {
      headers: {
        'Accept-Currency': currency,
      },
    }).then((res) => res.json());
  }

  public static addNewOrder(packageId: number) {
    const postData: CreateOrderDto = {
      packageId,
    };
    const data = fetch(`${API_URL}/orders`, {
      method: 'post',
      body: JSON.stringify(postData),
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    })
      .then((res) => res.json())
      .then((data) => data);
    return data;
  }
}
