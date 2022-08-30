import { API_URL } from '@app/constants';
import type { CreateOrderDto } from '@app/types/CreateOrderDto';
import { Order } from '@app/types/Order';

export default class OrderService {
  public static getOrders(currency: string): Promise<Order[]> {
    return fetch(`${API_URL}/orders`, {
      headers: {
        'Accept-Currency': currency,
      },
    }).then((res) => res.json());
  }

  //unused
  public static getOrderById(id: number, currency: string): Promise<Order> {
    return fetch(`${API_URL}/orders/${id}`, {
      headers: {
        'Accept-Currency': currency,
      },
    }).then((res) => res.json());
  }

  public static addNewOrder(packageId: number): Promise<Order> {
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
