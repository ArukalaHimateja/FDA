import { Component, Input, OnInit } from '@angular/core';
import { UtilityService } from '../../services/utility.service';

@Component({
  selector: 'app-show-image',
  templateUrl: './show-image.component.html',
  styleUrls: ['./show-image.component.css']
})
export class ShowImageComponent implements OnInit {

  @Input("image") image: any;
  @Input("type") type?: any;
  @Input("classes") classes?: any;
  @Input("width") width: any;
  @Input("height") height: any;

  constructor(
    public _utilityService: UtilityService
  ) { }

  ngOnInit() {
  }

}