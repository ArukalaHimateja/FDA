export interface FoodListItemType {
    id: any,
    productImage: any,
    productName: any,
    description: any,
    price: any,
    productSize: any,
    restaurantId: any,
    restaurantName: any,
    restaurantAddress: any,
    categoryId: any,
    categoryName: any,
    rating: any,
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
    orderId: any,
}

export interface OrderProductDetailType {
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
export interface OrderDetailType {
    id: any,
    createdAt: any,
    promoCode: any,
    payPriceWithoutPromoCode: any,
    payPriceWithPromoCode: any,
    payStatus: any,
    status: any,
    deliveryAddress: any,
    subTotalPrice: any,
}