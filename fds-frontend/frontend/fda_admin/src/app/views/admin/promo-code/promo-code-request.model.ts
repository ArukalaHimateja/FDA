export class PromoCodeRequest {
    propertyId: Number;
    promoCode: String;
    startDate: any;
    endDate: any;
    discount: Number;
    description: String;

    constructor(element?: any) {
        element = element || {};
        this.propertyId = element.propertyId || null;
        this.promoCode = element.promoCode || null;
        this.startDate = element.startDate || null;
        this.endDate = element.endDate || null;
        this.discount = element.discount || null;
        this.description = element.description || null;
    }
}