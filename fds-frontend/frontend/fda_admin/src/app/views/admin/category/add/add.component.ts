import { Component, Inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CategoryService } from '../category.service';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/views/session/auth.service';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent {
  addCategoryForm: FormGroup;
  image: any;
  isSubmitButtonDisable: boolean = false;
  progressBarForFile: number = 0;;
  sessionUser: any

  constructor(
    private _categoryService: CategoryService,
    public _fileUploadService: FileUploadService,
    private _router: Router,
    public matDialogRef: MatDialogRef<AddComponent>,
    private _snackBar: MatSnackBar,
    private _authService: AuthService,
    private _utilityService: UtilityService,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this.addCategoryForm = this._categoryService.createAddCategoryForm(this.data.element ? this.data.element : null);
    this.sessionUser = this._authService.getAuthUser();
    if(data.type !== 'add'){
      this.image = this.data.element.image;
    }
  }

  onSubmit() {
    if (this.image && this.image !== null) {
      let json = {
        name: this.addCategoryForm.controls['name'].value,
        image: this.image,
        description: this.addCategoryForm.controls['description'].value,
      }
      if (this.data.type === 'add') {
        this._categoryService.addCategory(json).then((response: any) => {
          if (response.statusText === 'OK') {
            this.addCategoryForm.reset();
            this.image = null;
            this.matDialogRef.close();
            this._utilityService.successMessage(response.body.message, response.body.status);
          } else {
            this._utilityService.errorMessage(response.body.message, response.body.status);
          }
        });
      } else if (this.data.type === 'update') {

        this._categoryService.updateCategory(json, this.data.id).then((response: any) => {
          if (response.statusText === 'OK') {
            this.addCategoryForm.reset();
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
