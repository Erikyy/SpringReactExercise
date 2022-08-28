import type { OrderStatus } from "./OrderStatus";
import type { Package } from "./Package";

export type Order = {
    id: number;
    status: OrderStatus,
    packageEntity: Package
}