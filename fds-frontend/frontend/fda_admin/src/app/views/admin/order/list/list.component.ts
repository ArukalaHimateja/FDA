import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from 'src/app/views/session/auth.service';
import { UserService } from '../../user/user.service';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {
  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  pageNumber: any = 1;
  sessionUser: any;

  constructor(
    public _utilityService: UtilityService,
    private _authService: AuthService,
    private _orderService: OrderService,
  ) {
    this.displayedColumns = this._orderService.displayedColumns;
    this.pagination = _utilityService.pagination;
    this.sessionUser = this._authService.getAuthUser();
    this.getDataList();
  }
  
  /**
  * Get next page data
  * 
  * @param page 
  */
  getNextPageData(page: any) {
    this.pageNumber = page.pageIndex + 1;
    this.pagination.currentPage = page.pageIndex + 1;
    this.getDataList();
  }

  /**
   * Get Data List with pagination
   */
  getDataList() {
    this._orderService.getAllOrderWithPaginationByRestaurantId().then((response: any) => {
      this.dataSource = new MatTableDataSource(response.body.data);
    })
  }

  async getCustomerDetail(customerId: any) {
    const response = await this._orderService.getUserDetailById(customerId);
    return response.body.data;
  }
  
  async generateReceipt(element: any) {
    var image = "/assets/images/dummy/user.jpg";
    let customerDtl: any = await this.getCustomerDetail(element.customerId);    
    let popupWin = window.open('', '_blank', 'top=0, left=0, height=100%, width=auto');
    popupWin?.document.open();
    let htmlString = `
      <html>
        <head>
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
          <style>
            #invoice-POS {
              box-shadow: 0 0 1in -0.25in rgba(0, 0, 0, 0.5);
              padding: 2mm;
              margin: 0 auto;
              width: 100%;
              background: #FFF;
            }
  
            ::selection {
              background: #f31544;
              color: #FFF;
            }
  
            ::moz-selection {
              background: #f31544;
              color: #FFF;
            }
  
            h1 {
              font-size: 20px;
              color: #222;
            }
  
            h2 {
              font-size: 16px;
            }
  
            p {
              font-size: 16px;
              color: #666;
              line-height: 20px;
            }
            td{
              font-size: 14.5px;
            }
  
            #top, #mid, #bot {
              border-bottom: 1px solid #EEE;
            }
  
            #top {
              min-height: 100px;
            }
  
            #mid {
              min-height: 80px;
            }
  
            #bot {
              min-height: 50px;
            }
  
            #top .logo {
              height: 80px;
              width: 80px;
              background: url(${image}) no-repeat;
              background-size: 80px 80px;
            }
  
            .clientlogo {
              float: left;
              height: 60px;
              width: 60px;
              background: url(http://michaeltruong.ca/images/client.jpg) no-repeat;
              background-size: 60px 60px;
              border-radius: 50px;
            }
  
            .info {
              display: block;
              margin-left: 0;
            }
  
            .title {
              float: right;
            }
  
            .title p {
              text-align: right;
            }
  
            table {
              width: 100%;
              border-collapse: collapse;
              margin-bottom: 20px;
            }
  
            td {
              /* Your styles here */
            }
  
            .tabletitle {
              /* Your styles here */
            }
  
            .service {
              border-bottom: 1px solid #EEE;
            }
  
            #legalcopy {
              margin-top: 5mm;
            }
          </style>
        </head>
        <body onload="window.print();window.close()">
          <div id="invoice-POS">
            <center id="top">
              <div class="logo"></div>
              <div class="info">
                <h1>${element.restaurantName}</h1>
              </div>
            </center>
            <div id="mid">
              <div class="info">
                <h2>Contact Info</h2>
                <table>
                  <tr>
                    <td>Name:</td>
                    <td>${customerDtl?.fullName}</td>
                  </tr>
                  <tr>
                    <td>Address:</td>
                    <td>${customerDtl?.deliveryAddress}</td>
                  </tr>
                  <tr>
                    <td>Email:</td>
                    <td>${customerDtl?.email}</td>
                  </tr>
                  <tr>
                    <td>Phone:</td>
                    <td>${customerDtl?.mobileNumber}</td>
                  </tr>
                </table>
              </div>
            </div>
            <div id="bot">
              <div id="table">
                <table>
                  <tr class="tabletitle">
                    <td class="item"><h2>Product Name</h2></td>
                    <td class="Hours"><h2>Qty</h2></td>
                    <td class="Rate"><h2>Sub Total</h2></td>
                  </tr>
                  <tr class="service">
                    <td class="tableitem">${element.productName}</td>
                    <td class="tableitem">${element.productQuantity}</td>
                    <td class="tableitem">${element.perQuantityPrice}</td>
                  </tr>
                </table>
                <div>
                  <table>
                    <tr>
                      <td class="Rate"><h2>tax</h2></td>
                      <td class="payment"><h2>$0</h2></td>
                    </tr>
                    <tr>
                      <td class="Rate"><h2>Total</h2></td>
                      <td class="payment"><h2>₹${element.totalPayPrice}</h2></td>
                    </tr>
                  </table>
                </div>
              </div>
              <div id="legalcopy">
                <p class="legal">
                  <strong>Thank you for your business!</strong> Payment is expected within ${element.createdAt} days; please process this invoice within that time. There will be a 5% interest charge per month on late invoices.
                </p>
              </div>
            </div>
          </div>
        </body>
      </html>`;
    popupWin?.document.write(htmlString);
    popupWin?.document.close();
  }
  




}
