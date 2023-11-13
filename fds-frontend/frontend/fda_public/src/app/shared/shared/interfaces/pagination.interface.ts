export interface FoodListItemType {
    id: string,
    productImage: string,
    productName: string,
    description: string,
    price: string,
    productSize: string,
    restaurantId: string,
    restaurantName: string,
    restaurantAddress: string,
    categoryId: string,
    categoryName: string,
}

export interface RestroResponseType {
    id: number,
    createdAt: string,
    updatedAt: string,
    name: string,
    description: string,
    status: string,
    restaurantId: number,
}

export interface Pagination {
    currentPage: number,
    data: any,
    perPage: number,
    totalCount: number,
    totalPages: number
}
export interface FoodCategoryType {
    id: number,
    name: string;
    image: string;
    description: string,
}

export interface OrderHistoryListType {
    id: number,
    createdAt: string,
    updatedAt: any,
    customerId: number,
    productId: number,
    productQuantity: number,
    payStatus: number,
    status: number,
    restaurantId: number,
    totalPayPrice: number,
    perQuantityPrice: number,
    deliveryAddress: string,
    productName: string,
}

export interface OrderDetailType{
    id: any,
    createdAt: any,
    updatedAt: any,
    customerId: any,
    productId: any,
    productName: any,
    productQuantity: any,
    totalPayPrice: any,
    perQuantityPrice: any,
    payStatus: any,
    status: any,
    restaurantId: any,
    deliveryAddress: any,
    restaurantName: any,
}