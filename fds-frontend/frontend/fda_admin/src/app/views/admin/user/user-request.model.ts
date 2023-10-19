export class UserRequest {
    name: string;
    email: string;
    mobileNumber: string;
    businessName: any;
    businessAddress: any;
    businessEmail: any;
    businessPhone: any;

    constructor(element?: any) {
        element = element || {};
        this.name = element.name || null;
        this.email = element.email || null;
        this.mobileNumber = element.mobileNumber || null;
        this.businessName = element.businessName || null;
        this.businessAddress = element.businessAddress || null;
        this.businessEmail = element.businessEmail || null;
        this.businessPhone = element.businessPhone || null;
    }
}