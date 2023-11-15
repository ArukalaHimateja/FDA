import { Component, Inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MenuService } from '../menu.service';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from 'src/app/views/session/auth.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent {
  addMenuForm: FormGroup;
  image: any;
  isSubmitButtonDisable: boolean = false;
  progressBarForFile: number = 0;
  categoryList: any[] = [];
  sessionUser: any;

  constructor(
    private _menuService: MenuService,
    public _fileUploadService: FileUploadService,
    private _snackBar: MatSnackBar,
    private _authService: AuthService,
    private _router: Router,
    public matDialogRef: MatDialogRef<AddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _utilityService: UtilityService,
  ) {
    this.sessionUser = this._authService.getAuthUser();
    if (data.type === 'add') {
      this.addMenuForm = this._menuService.createAddMenuForm();
      this._menuService.getCategoryListByRestaurantId(this.data.id).then((response: any) => {
        this.categoryList = response.body.data;
      })
    } else {
      this.addMenuForm = this._menuService.createUpdateMenuForm(this.data.element);
      this.image = this.data.element.productImage;
    }
  }

  onSubmit() {
    // this._menuService.addProduct(json);
    // this._router.navigate(["'/menu'"])

    if (this.image && this.image !== null) {
      if (this.data.type === 'add') {
        let json = {
          categoryId: this.addMenuForm.controls['categoryId'].value,
          description: this.addMenuForm.controls['description'].value,
          price: this.addMenuForm.controls['price'].value,
          productImage: this.image,
          productName: this.addMenuForm.controls['productName'].value,
          productSize: this.addMenuForm.controls['productSize'].value,
          restaurantId: this.addMenuForm.controls['restaurantId'].value,
        }
        this._menuService.addProduct(json).then((response: any) => {
          if (response.statusText === 'OK') {
            this.addMenuForm.reset();
            this.image = null;
            this.matDialogRef.close();
            this._utilityService.successMessage(response.body.message, response.body.status);
          } else {
            this._utilityService.errorMessage(response.body.message, response.body.status);
          }
        });
      } else if (this.data.type === 'update') {
        let json = {
          description: this.addMenuForm.controls['description'].value,
          price: this.addMenuForm.controls['price'].value,
          productImage: this.image,
          productName: this.addMenuForm.controls['productName'].value,
          productSize: this.addMenuForm.controls['productSize'].value,
          restaurantId: this.addMenuForm.controls['restaurantId'].value,
        }
        this._menuService.updateProduct(json, this.data.id).then((response: any) => {
          if (response.statusText === 'OK') {
            this.addMenuForm.reset();
            this.image = null;
            this.matDialogRef.close();
            this._utilityService.successMessage(response.body.message, response.body.status);
          } else {
            this._utilityService.errorMessage(response.body.message, response.body.status);
          }
        });
      }
    } else {
      // Display a MatSnackBar when the image is null
      this._snackBar.open('Please, select an image', 'OK', { duration: 5000, verticalPosition: 'top' });
    }

  }

  /**
  * On select file
  * 
  * @param event 
  */
  onSelectFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.uploadFile(event.target.files[0]);
    }
  }
  /**
    * Remove file
    */
  removeLogoImage() {
    this._fileUploadService.delete(this.image).then((response: any) => {
      if (response && response.status === 'OK') {
        this.image = "";
      } else {
      }
    })
  }

  /**
     * Upload File
     * 
     * @param multiPartFile 
     */
  uploadFile(multiPartFile: any) {
    this.isSubmitButtonDisable = true;
    let formData = new FormData();
    formData.append('file', multiPartFile);
    formData.append('fileName', `${new Date().getTime()}_${multiPartFile.name}`);
    formData.append('type', `${this.sessionUser.name}`);
    this._fileUploadService.uploadImage(formData).subscribe((event: HttpEvent<any>) => {
      switch (event.type) {
        case HttpEventType.Sent:
          console.log('Request has been made!');
          break;
        case HttpEventType.ResponseHeader:
          console.log('Response header has been received!');
          break;
        case HttpEventType.UploadProgress:
          this.progressBarForFile = Math.round(event.loaded / (event && event.total ? event.total * 100 : 0));
          console.log(`Uploaded! ${this.progressBarForFile}%`);
          break;
        case HttpEventType.Response:
          console.log('User successfully created!', event.body);
          this.image = event.body.data;
          setTimeout(() => {
            this.progressBarForFile = 0;
            this.isSubmitButtonDisable = false;
          }, 1500);
      }
    });
  }

}
