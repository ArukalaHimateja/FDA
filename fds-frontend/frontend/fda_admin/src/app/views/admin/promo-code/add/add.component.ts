import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UtilityService } from '../../../../shared/services/utility.service';
import { ConstantService } from '../../../../shared/services/constant.service';
import { PromoCodeRequest } from '../promo-code-request.model';
import { PromoCodeService } from '../promo-code.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  pageType: string = 'add';
  icon: string = 'add_circle';
  formGroup!: FormGroup;
  promoCodeRequest: PromoCodeRequest = new PromoCodeRequest();
  authUser: any;
  json: any = {};
  initialValue: any = {};
  minDate: Date = new Date();
  propertyList: any[] = [];

  constructor(
    public _utilityService: UtilityService,
    public _promoCodeService: PromoCodeService,
    public _constantService: ConstantService
  ) {
    this.formGroup = this._promoCodeService.createForm(this.promoCodeRequest);
  }

  ngOnInit() {
    if (this._promoCodeService.data) {
      this.promoCodeRequest = this._promoCodeService.data;
      this.promoCodeRequest.startDate = new Date(this.promoCodeRequest.startDate);
      this.promoCodeRequest.endDate = new Date(this.promoCodeRequest.endDate);
      this.initialValue = Object.assign({}, this.promoCodeRequest);
      this.pageType = 'edit';
      this.icon = 'edit';
    } else {
      this.pageType = 'add';
      this.icon = 'add_circle';
    }
    this.formGroup = this._promoCodeService.createForm(this.promoCodeRequest);
  }

  /**
   * Add or Save Data
   */
  submit() {
    const data = this.formGroup.getRawValue();
    data.startDate = new Date(data.startDate).getTime();
    data.endDate = new Date(data.endDate).getTime();
    this._promoCodeService.addOrUpdateData(data, this._promoCodeService.data ? this._promoCodeService.data.id : null);
  }
}
