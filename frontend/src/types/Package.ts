import type { PackageCategory } from "./PackageCategory"
import type { PackageDescription } from "./PackageDescription"
import type { PackageType } from "./PackageType"


export type Package = {
    id: number,
    packageType: PackageType,
    descriptions: PackageDescription[],
    price: number,
    category: PackageCategory,
}