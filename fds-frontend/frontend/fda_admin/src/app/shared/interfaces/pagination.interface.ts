export interface Pagination {
    totalPages: number;
    totalCount: number;
    currentPage: number;
    perPage: number;
    data: any;
}
export interface MenuListType {
        id: any,
        createdAt: any,
        updatedAt: any,
        price: any,
        productSize: any,
        restaurantId: any,
        restaurantName: any,
        active: any,
        categoryId: any,
        categoryName: any,
        productName: any,
        stripeProductId: any,
        stripePriceId: any,
        productImage: any,
        description: any,
}

export interface DashboardDataType {
    totalUser: any,
    totalRestaurant: any,
    totalOrder: any,
    totalEmployee: any,
    totalOrderDelivered: any,
    totalPendingOrder: any,
    totalRevenue: any,
}