export class PromoCodeRequest {
    id: Number;
    promoCode: String;
    startDate: any;
    endDate: any;
    value: Number;
    description: String;

    constructor(element?: any) {
        element = element || {};
        this.id = element.id || null;
        this.promoCode = element.promoCode || null;
        this.startDate = element.startDate || null;
        this.endDate = element.endDate || null;
        this.value = element.value || null;
        this.description = element.description || null;
    }
}